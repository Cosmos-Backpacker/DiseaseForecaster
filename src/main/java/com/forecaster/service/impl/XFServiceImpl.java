package com.forecaster.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.forecaster.bean.Ocr.JsonParseOcr;
import com.forecaster.bean.Ocr.Text.JsonParseText;
import com.forecaster.bean.Ocr.Text.Lines;
import com.forecaster.bean.Ocr.Text.Pages;
import com.forecaster.bean.Ocr.Text.Words;
import com.forecaster.bean.WebSocket.NettyGroup;
import com.forecaster.bean.WebSocket.ResultBean;
import com.forecaster.bean.WebSocket.RoleContent;
import com.forecaster.config.XFConfig;
import com.forecaster.listener.XFWebClient;
import com.forecaster.listener.XFWebSocketListener;
import com.forecaster.service.XFService;
import com.forecaster.utils.OcrUtil;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;
import okhttp3.WebSocket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import static com.forecaster.listener.XFWebClient.getAuthUrl_OCR;

/**
 * @Author: ChengLiang
 * @CreateTime: 2023-10-17  15:58
 * @Description: TODO
 * @Version: 1.0
 */
@Slf4j
@Service
public class XFServiceImpl implements XFService {

    @Autowired
    private XFConfig xfConfig;

    @Autowired
    private XFWebClient xfWebClient;

    @Autowired
    private OcrUtil ocrUtil;

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




    //发起OCR的请求
    public ResultBean OcrRequest(MultipartFile file) throws Exception {

        //发送请求获取响应体
        URL realUrl = new URL(getAuthUrl_OCR(xfConfig.getHostUrlOcr(), xfConfig.getApiKey(), xfConfig.getApiSecret()));
        URLConnection connection = realUrl.openConnection();
        HttpURLConnection httpURLConnection = (HttpURLConnection) connection;
        httpURLConnection.setDoInput(true);
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setRequestProperty("Content-type", "application/json");
        OutputStream out = httpURLConnection.getOutputStream();
        String params = xfWebClient.createRequestParams_OCR(file);
        //System.out.println("params=>" + params);
        out.write(params.getBytes());
        out.flush();
        InputStream is = null;
        try {
            is = httpURLConnection.getInputStream();
        } catch (Exception e) {
            is = httpURLConnection.getErrorStream();
            throw new Exception("make request error:" + "code is " + httpURLConnection.getResponseMessage() + ocrUtil.readAllBytes(is));
        }

        //对响应体进行解析
        String resp=ocrUtil.readAllBytes(is);
        JsonParseOcr myJsonParseOcr= JSON.parseObject(resp,JsonParseOcr.class);
        //获取响应提里Base64编码前的text文本
        String textBase64Decode=new String(Base64.getDecoder().decode(myJsonParseOcr.getPayload().getResult().getText()), "UTF-8");
        //进行解码获取text内容
        JSONObject text = JSON.parseObject(textBase64Decode);

        JsonParseText jsonText= JSON.parseObject(String.valueOf(text),JsonParseText.class);

        StringBuilder finalContent= new StringBuilder();
        //将结果内容拼接起来
         List<Pages> pages=jsonText.getPages();
         for (Pages page:pages)//找到pages页面
         {
             List<Lines> lines=page.getLines(); //找到lines集合
             for(Lines line:lines)
             {
                 List<Words> words=line.getWords();  //找到words列表
                for (Words word:words)
                {
                    finalContent.append(word.getContent()).append("\n");
                }
             }
         }

        log.info("最终返回的答案是{}",finalContent);
        //封装成ResultBean对象
        return ResultBean.success(finalContent.toString());
    }







}
