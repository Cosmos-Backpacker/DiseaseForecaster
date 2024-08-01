package com.forecaster.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.forecaster.bean.ImageGeneration.JsonParseImageG;
import com.forecaster.bean.ImageGeneration.Text;
import com.forecaster.bean.ImageUnderstand.RoleContentImage;
import com.forecaster.bean.Ocr.JsonParseOcr;
import com.forecaster.bean.Ocr.Text.JsonParseText;
import com.forecaster.bean.Ocr.Text.Lines;
import com.forecaster.bean.Ocr.Text.Pages;
import com.forecaster.bean.Ocr.Text.Words;
import com.forecaster.bean.WebSocketBigModel.NettyGroup;
import com.forecaster.bean.WebSocketBigModel.ResultBean;
import com.forecaster.bean.WebSocketBigModel.RoleContent;
import com.forecaster.config.XFConfig;
import com.forecaster.listener.XFWebClient;
import com.forecaster.listener.XFWebSocketBigModelListener;
import com.forecaster.listener.XFWebSocketImageUnderstandListener;
import com.forecaster.service.XFService;
import com.forecaster.utils.OcrUtil;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import okhttp3.WebSocket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Map;


import static com.forecaster.listener.XFWebClient.getAuthUrl_OCR;

/**
 * @Author: ChengLiang
 * @CreateTime: 2023-10-17  15:58
 * @Description: TODO
 * @Version: 1.0
 */
@Slf4j
@Data
@Service
public class XFServiceImpl implements XFService {

    @Autowired
    private XFConfig xfConfig;

    @Autowired
    private XFWebClient xfWebClient;

    @Autowired
    private OcrUtil ocrUtil;


    //图片理解请求中设置历史对话合集
    //可以试着用这个方法改一下之前的大模型
    public static List<RoleContentImage> historyListImage = new ArrayList<>(); // 对话历史存储集合



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

//=====================发起大模型请求==================
    @Override
    public synchronized ResultBean pushMessageToXFServer(String uid, String text) {

        //===========================构建questions======================
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

        //================================问题封装好之后发出请求==============================
        //同时用Listener来接受返回结果
        XFWebSocketBigModelListener xfWebSocketBigModelListener = new XFWebSocketBigModelListener();
        WebSocket webSocket = xfWebClient.sendMsg(uid, questions, xfWebSocketBigModelListener);
        if (webSocket == null) {
            log.error("webSocket连接异常");
            ResultBean.fail("请求异常，请联系管理员");
        }
        try {
            int count = 0;
            int maxCount = xfConfig.getMaxResponseTime() * 5;
            while (count <= maxCount) {
                Thread.sleep(200);
                if (xfWebSocketBigModelListener.isWsCloseFlag()) {
                    break;
                }
                count++;
            }
            if (count > maxCount) {
                return ResultBean.fail("响应超时，请联系相关人员");
            }
            //先记录再返回
            RoleContent.recordAnswer(xfWebSocketBigModelListener.getAnswer());
            //封装成ResultBean对象并返回
            return ResultBean.success(xfWebSocketBigModelListener.getAnswer());
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
        String resp = ocrUtil.readAllBytes(is);
        JsonParseOcr myJsonParseOcr = JSON.parseObject(resp, JsonParseOcr.class);
        //获取响应提里Base64编码前的text文本
        String textBase64Decode = new String(Base64.getDecoder().decode(myJsonParseOcr.getPayload().getResult().getText()), "UTF-8");
        //进行解码获取text内容
        JSONObject text = JSON.parseObject(textBase64Decode);

        JsonParseText jsonText = JSON.parseObject(String.valueOf(text), JsonParseText.class);

        StringBuilder finalContent = new StringBuilder();
        //将结果内容拼接起来
        List<Pages> pages = jsonText.getPages();
        for (Pages page : pages)//找到pages页面
        {
            List<Lines> lines = page.getLines(); //找到lines集合
            for (Lines line : lines) {
                List<Words> words = line.getWords();  //找到words列表
                for (Words word : words) {
                    finalContent.append(word.getContent()).append("\n");
                }
            }
        }

        log.info("最终返回的答案是{}", finalContent);
        //封装成ResultBean对象
        return ResultBean.success(finalContent.toString());
    }


    //=========================发起图片理解请求==================================
    public synchronized ResultBean ImageUnderstand(MultipartFile file, String uid, String text) {
        //将text文本内容封装进questions中
        ArrayList<RoleContentImage> questions = new ArrayList();

        Boolean ImageAddFlag = false; // 判断是否添加了图片信息


        // 历史问题获取
        if (historyListImage.size() > 0) { // 保证首个添加的是图片
            for (RoleContentImage tempRoleContent : historyListImage) {
                if (tempRoleContent.getContent_type().equals("image")) { // 保证首个添加的是图片
                    questions.add(tempRoleContent);
                    ImageAddFlag = true;
                }
            }
        }
        if (historyListImage.size() > 0) {
            for (RoleContentImage tempRoleContent : historyListImage) {
                if (!tempRoleContent.getContent_type().equals("image")) { // 添加费图片类型
                    questions.add(tempRoleContent);
                }
            }
        }


        // 最新问题
        RoleContentImage roleContent = new RoleContentImage();
        // 添加图片信息
        if (!ImageAddFlag) {
            roleContent.setRole("user");
            try {
                roleContent.setContent(Base64.getEncoder().encodeToString(file.getBytes()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            roleContent.setContent_type("image");
            questions.add(roleContent);
            historyListImage.add(roleContent);
        }


        // 添加对图片提出要求的信息，也就是最新提出的问题
        RoleContentImage roleContent1 = new RoleContentImage();
        roleContent1.setRole("user");
        roleContent1.setContent(text);  //text为最新提出的问题
        roleContent1.setContent_type("text");
        questions.add(roleContent1);
        historyListImage.add(roleContent1);

        //==========================封装好问题知否发出请求===========================
        //创建一个用于处理响应对象的listener
        XFWebSocketImageUnderstandListener xfwebSocketImageUnderstandListener = new XFWebSocketImageUnderstandListener();

        WebSocket webSocket = xfWebClient.sendImageMessage(uid, questions, xfwebSocketImageUnderstandListener);
        if (webSocket == null) {
            log.error("webSocket连接异常");
            ResultBean.fail("请求异常，请联系管理员");
        }
        try {
            int count = 0;
            int maxCount = xfConfig.getMaxResponseTime() * 5;
            while (count <= maxCount) {
                Thread.sleep(200);
                if (xfwebSocketImageUnderstandListener.isWsCloseFlag()) {
                    break;
                }
                count++;
            }
            if (count > maxCount) {
                return ResultBean.fail("响应超时，请联系相关人员");
            }
            //封装成ResultBean对象并返回
            return ResultBean.success(xfwebSocketImageUnderstandListener.getAnswer());
        } catch (Exception e) {
            log.error("请求异常：{}", e);
        } finally {
            webSocket.close(1000, "");
        }
        return ResultBean.success("");
    }


    public String ImageGeneration(String uid, String content) {

        //发送请求获取字符串类型的答案
        String result = "";
        String resp = xfWebClient.ImageGenerationRequest(uid, content);
        //解析答案
        JsonParseImageG jsonParseImageG = JSON.parseObject(resp, JsonParseImageG.class);

        if (jsonParseImageG.getPayload() != null) {
            //这里的Text类是ImageGeneration包里的类
            List<Text> textList = jsonParseImageG.getPayload().getChoices().getText();

            for (Text temp : textList) {
                //将Text列表集合内的图片内容整理在一起
                //因为每次只能返回一张图片，所以这里就直接用content替换result了，没有处理返回过个图片内容
                result = temp.getContent();
            }

        } else {

           // return ResultBean.fail("响应失败");
            return "响应失败";
        }

//        return ResultBean.success(result);
        return result;
    }


}
