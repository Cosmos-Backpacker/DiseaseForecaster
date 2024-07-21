package com.forecaster.listener;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.forecaster.bean.WebSocket.RoleContent;
import com.forecaster.config.XFConfig;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.io.*;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.forecaster.utils.OcrUtil.read;


/**
 * @Author: ChengLiang
 * @CreateTime: 2023-10-19  11:04
 * @Description: TODO
 * @Version: 1.0
 */
@Slf4j
@Component
public class XFWebClient {

    @Autowired
    private XFConfig xfConfig;

    /**
     * 发送消息
     *
     * @param uid       每个用户的id，用于区分不同用户
     * @param questions 发送给大模型的消息，可以包含上下文内容
     * @return 获取websocket连接，以便于我们在获取完整大模型回复后手动关闭连接
     */
    /**
     * @description: 发送请求至大模型方法
     * @author: ChengLiang
     * @date: 2023/10/19 16:27
     * @param: [用户id, 请求内容, 返回结果监听器listener]
     * @return: okhttp3.WebSocket
     **/

    //通用大模型发起请求
    public WebSocket sendMsg(String uid, ArrayList<RoleContent> questions, WebSocketListener listener) {
        // 获取鉴权url
        String authUrl = null;
        try {
            authUrl = getAuthUrl_WebSocket(xfConfig.getHostUrlWebsocket(), xfConfig.getApiKey(), xfConfig.getApiSecret());
        } catch (Exception e) {
            log.error("鉴权失败：{}", e);
            return null;
        }
        // 鉴权方法生成失败，直接返回 null
        OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
        // 将 https/http 连接替换为 ws/wss 连接
        String url = authUrl.replace("http://", "ws://").replace("https://", "wss://");
        Request request = new Request.Builder().url(url).build();
        // 建立 wss 连接
        WebSocket webSocket = okHttpClient.newWebSocket(request, listener);
        log.info("这里的question是{}", questions);
        // 组装请求参数
        JSONObject requestDTO = createRequestParams_WebSocket(uid, questions);
        // 发送请求
        webSocket.send(JSONObject.toJSONString(requestDTO));
        return webSocket;
    }


    /**
     * @description: 鉴权方法
     * @author: CosmosBackpacker
     * @date: 2024/7/20 16:25
     * @param: [讯飞大模型请求地址, apiKey, apiSecret]
     * @return: java.lang.String
     **/
    //WebSocket通用协议鉴权
    public static String getAuthUrl_WebSocket(String hostUrl, String apiKey, String apiSecret) throws Exception {
        URL url = new URL(hostUrl);
        // 时间
        SimpleDateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
        format.setTimeZone(TimeZone.getTimeZone("GMT"));
        String date = format.format(new Date());
        // 拼接
        String preStr = "host: " + url.getHost() + "\n" +
                "date: " + date + "\n" +
                "GET " + url.getPath() + " HTTP/1.1";
        // SHA256加密
        Mac mac = Mac.getInstance("hmacsha256");
        SecretKeySpec spec = new SecretKeySpec(apiSecret.getBytes(StandardCharsets.UTF_8), "hmacsha256");
        mac.init(spec);

        byte[] hexDigits = mac.doFinal(preStr.getBytes(StandardCharsets.UTF_8));

        // Base64加密
        String sha = Base64.getEncoder().encodeToString(hexDigits);
        // 拼接
        String authorization = String.format("api_key=\"%s\", algorithm=\"%s\", headers=\"%s\", signature=\"%s\"", apiKey, "hmac-sha256", "host date request-line", sha);
        // 拼接地址
        HttpUrl httpUrl = Objects.requireNonNull(HttpUrl.parse("https://" + url.getHost() + url.getPath())).newBuilder().//
                addQueryParameter("authorization", Base64.getEncoder().encodeToString(authorization.getBytes(StandardCharsets.UTF_8))).//
                addQueryParameter("date", date).//
                addQueryParameter("host", url.getHost()).//
                build();

        return httpUrl.toString();
    }

    /**
     * @description: 请求参数组装方法
     * @author: CosmosBackpacker
     * @date: 2023/12/29 16:26
     * @param: [用户id, 请求内容]
     * @return: com.alibaba.fastjson.JSONObject
     **/


    //构建请求体
    public JSONObject createRequestParams_WebSocket(String uid, List<RoleContent> questions) {
        JSONObject requestJson = new JSONObject();
        // header参数
        JSONObject header = new JSONObject();
        header.put("app_id", xfConfig.getAppId());
        header.put("uid", uid);
        // parameter参数
        JSONObject parameter = new JSONObject();
        JSONObject chat = new JSONObject();
        chat.put("domain", "generalv2");
        chat.put("temperature", 0.5);
        chat.put("max_tokens", 4096);
        parameter.put("chat", chat);
        // payload参数
        JSONObject payload = new JSONObject();
        JSONObject message = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        jsonArray.addAll(questions);

        log.info("questionis{}", questions);
        log.info("jsonArrayis{}", jsonArray);

        message.put("text", jsonArray);
        payload.put("message", message);
        requestJson.put("header", header);
        requestJson.put("parameter", parameter);
        requestJson.put("payload", payload);
        return requestJson;
    }

    //=======================OCR鉴权===========================
    public static String getAuthUrl_OCR(String hostUrl, String apiKey, String apiSecret) throws Exception {
        //host
        URL url = new URL(hostUrl);
        //date
        SimpleDateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
        format.setTimeZone(TimeZone.getTimeZone("GMT"));
        String date = format.format(new Date());

        //拼接signature_origin
        String signature_origin = "host: " + url.getHost() + "\n" +
                "date: " + date + "\n" +
                "POST " + url.getPath() + " HTTP/1.1";

        // 创建一个Mac实例，指定HMAC-SHA256算法
        Mac sha256HMAC = Mac.getInstance("HmacSHA256");
        // 初始化Mac实例，使用提供的密钥
        SecretKeySpec secretKey = new SecretKeySpec(apiSecret.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
        sha256HMAC.init(secretKey);
        // 对原始签名进行更新
        sha256HMAC.update(signature_origin.getBytes(StandardCharsets.UTF_8));
        // 计算摘要并编码为Base64
        byte[] bytes = sha256HMAC.doFinal();

        String signature = Base64.getEncoder().encodeToString(bytes);

        //根据以上信息拼接authorization base64编码前（authorization_origin）的字符串
        String authorization_origin = String.format("api_key=\"%s\", algorithm=\"%s\", headers=\"%s\", signature=\"%s\"", apiKey, "hmac-sha256", "host date request-line", signature);

        //最后再对authorization_origin进行base64编码获得最终的authorization参数。
        String authorization = Base64.getEncoder().encodeToString(authorization_origin.getBytes(StandardCharsets.UTF_8));

        //拼接地址
        HttpUrl httpUrl = Objects.requireNonNull(HttpUrl.parse("https://" + url.getHost() + url.getPath())).newBuilder().//
                addQueryParameter("authorization", authorization).//
                addQueryParameter("date", date).//
                addQueryParameter("host", url.getHost()).//
                build();
        return httpUrl.toString();
    }


    //构造OCR请求体
    public String createRequestParams_OCR() throws IOException {
        String param = "{"+
                "    \"header\": {"+
                "        \"app_id\": \""+xfConfig.getAppId()+"\","+
                "        \"status\": 3"+
                "    },"+
                "    \"parameter\": {"+
                "        \"sf8e6aca1\": {"+
                "            \"category\": \"ch_en_public_cloud\","+
                "            \"result\": {"+
                "                \"encoding\": \"utf8\","+
                "                \"compress\": \"raw\","+
                "                \"format\": \"json\""+
                "            }"+
                "        }"+
                "    },"+
                "    \"payload\": {"+
                "        \"sf8e6aca1_data_1\": {"+
                "            \"encoding\": \"jpg\","+
                "            \"status\": " + 3 + "," +
                "            \"image\": \""+Base64.getEncoder().encodeToString(read("C:/Users/18730/Desktop/测试.png"))+"\""+
                "        }"+
                "    }"+
                "}";

        return param;
    }













}


