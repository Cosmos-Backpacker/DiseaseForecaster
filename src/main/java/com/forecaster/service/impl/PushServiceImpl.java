package com.forecaster.service.impl;

import com.alibaba.fastjson.JSON;
import com.forecaster.bean.NettyGroup;
import com.forecaster.bean.ResultBean;
import com.forecaster.bean.RoleContent;
import com.forecaster.config.XFConfig;
import com.forecaster.listener.XFWebClient;
import com.forecaster.listener.XFWebSocketListener;
import com.forecaster.service.PushService;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;
import okhttp3.WebSocket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: ChengLiang
 * @CreateTime: 2023-10-17  15:58
 * @Description: TODO
 * @Version: 1.0
 */
@Slf4j
@Service
public class PushServiceImpl implements PushService {

    @Autowired
    private XFConfig xfConfig;

    @Autowired
    private XFWebClient xfWebClient;

    @Override
    public void pushToOne(String uid, String text) {
        if (StringUtils.isEmpty(uid) || StringUtils.isEmpty(text)) {
            log.error("uid或text均不能为空");
            throw new RuntimeException("uid或text均不能为空");
        }
        ConcurrentHashMap<String, Channel> userChannelMap = NettyGroup.getUserChannelMap();
        for (String channelId : userChannelMap.keySet()) {
            if (channelId.equals(uid)) {
                Channel channel = userChannelMap.get(channelId);
                if (channel != null) {
                    ResultBean success = ResultBean.success(text);
                    channel.writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(success)));
                    log.info("信息发送成功：{}", JSON.toJSONString(success));
                } else {
                    log.error("该id对于channelId不存在！");
                }
                return;
            }
        }
        log.error("该用户不存在！");
    }

    @Override
    public void pushToAll(String text) {
        String trim = text.trim();
        ResultBean success = ResultBean.success(trim);
        NettyGroup.getChannelGroup().writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(success)));
        log.info("信息推送成功：{}", JSON.toJSONString(success));
    }

    //测试账号只有2个并发，此处只使用一个，若是生产环境允许多个并发，可以采用分布式锁

    /**
     * 原作者这里的text代表我们询问的问题，没有上下文回答，如果把
     * GPT的回答记录下来并且在下方将其封装进question就可以实现上下文对答
     */


    @Override
    public synchronized ResultBean pushMessageToXFServer(String uid, String text) {
        //log.info("我倒要看看你调用几次这个方法");
        //记录问题
        RoleContent.recordQuestion(text);
        //创建问题对象
        ArrayList<RoleContent> questions = new ArrayList<>();

        //封装问题
        for (int i = 0; i < RoleContent.getAnswer().size(); i++) {
            // log.info("i为{}", i);
            //log.info("已经保存的问题个数为{}", RoleContent.getQuestion().size());

            //先封装RoleContent再封装question
            if (RoleContent.getQuestion().size() != 0) {
                RoleContent userRoleContent = RoleContent.createUserRoleContent(RoleContent.getQuestion().get(i));
                questions.add(userRoleContent);         //添加用户历史问题
            }
            // log.info("已经保存的答案数{}", RoleContent.getAnswer().size());
            if (RoleContent.getAnswer().size() != 0) {
                if (RoleContent.getAnswer().get(i) != null) {
                    RoleContent answerRoleContent = RoleContent.createAssistantRoleContent(RoleContent.getAnswer().get(i));
                    questions.add(answerRoleContent);
                }

            }

        }
        //最后再增加用户新问的问题
        RoleContent userRoleContent = RoleContent.createUserRoleContent(text);

        questions.add(userRoleContent);

        XFWebSocketListener xfWebSocketListener = new XFWebSocketListener();
        WebSocket webSocket = xfWebClient.sendMsg(uid, questions, xfWebSocketListener);
        if (webSocket == null) {
            log.error("webSocket连接异常");
            ResultBean.fail("请求异常，请联系管理员");
        }
        try {
            int count = 0;
            int maxCount = xfConfig.getMaxResponseTime() * 5;
            while (count <= maxCount) {
                Thread.sleep(200);
                if (xfWebSocketListener.isWsCloseFlag()) {
                    break;
                }
                count++;
            }
            if (count > maxCount) {
                return ResultBean.fail("响应超时，请联系相关人员");
            }
            //先记录再返回
            RoleContent.recordAnswer(xfWebSocketListener.getAnswer());
            //封装成ResultBean对象并返回
            return ResultBean.success(xfWebSocketListener.getAnswer());
        } catch (Exception e) {
            log.error("请求异常：{}", e);
        } finally {
            webSocket.close(1000, "");
        }
        return ResultBean.success("");
    }
}
