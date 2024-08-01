package com.forecaster.listener;

import com.alibaba.fastjson.JSON;
import com.forecaster.bean.ImageUnderstand.JsonParse;
import com.forecaster.bean.ImageUnderstand.RoleContentImage;
import com.forecaster.bean.ImageUnderstand.Text;

import lombok.extern.slf4j.Slf4j;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

import java.io.IOException;
import java.util.List;


import static com.forecaster.service.impl.XFServiceImpl.historyListImage;
import static com.forecaster.utils.OcrUtil.canAddHistory;


@Slf4j
public class XFWebSocketImageUnderstandListener extends WebSocketListener {

    private boolean wsCloseFlag = false;

    //语句组装buffer，将大模型返回结果全部接收，在组装成一句话返回
    private StringBuilder answer = new StringBuilder();

    public String getAnswer() {
        return answer.toString();
    }

    public boolean isWsCloseFlag() {
        return wsCloseFlag;
    }

    @Override
    public void onOpen(WebSocket webSocket, Response response) {
        super.onOpen(webSocket, response);
        log.info("大模型服务器连接成功！");
    }


    @Override
    public void onMessage(WebSocket webSocket, String text) {
        super.onMessage(webSocket, text); //先掉用父类的方法接受返回的数据，然后对数据进行处理
        // System.out.println(userId + "用来区分那个用户的结果" + text);
        JsonParse myJsonParse = JSON.parseObject(text, JsonParse.class);
        if (myJsonParse.getHeader().getCode() != 0) {
            System.out.println("发生错误，错误码为：" + myJsonParse.getHeader().getCode());
            System.out.println("本次请求的sid为：" + myJsonParse.getHeader().getSid());
            webSocket.close(1000, "");
        }

        //组装答案
        List<Text> textList = myJsonParse.getPayload().getChoices().getText();
        for (Text temp : textList) {
            //System.out.print(temp.getContent());
            //将答案汇总到一起
            this.answer.append(temp.getContent());
        }
        log.info("最终返回答案是：{}",answer);
        if (myJsonParse.getHeader().getStatus() == 2) {
            // 可以关闭连接，释放资源
            System.out.println();
            System.out.println("*************************************************************************************");
            if (canAddHistory()) {
                RoleContentImage roleContent = new RoleContentImage();
                roleContent.setRole("assistant");
                roleContent.setContent(answer.toString());
                roleContent.setContent_type("text");
                historyListImage.add(roleContent);
            } else {
                historyListImage.remove(0);
                RoleContentImage roleContent = new RoleContentImage();
                roleContent.setRole("assistant");
                roleContent.setContent(answer.toString());
                roleContent.setContent_type("text");
                historyListImage.add(roleContent);
            }
            wsCloseFlag = true;  //关闭连接

        }
    }

    @Override
    public void onFailure(WebSocket webSocket, Throwable t, Response response) {
        super.onFailure(webSocket, t, response);
        try {
            if (null != response) {
                int code = response.code();
                System.out.println("onFailure code:" + code);
                System.out.println("onFailure body:" + response.body().string());
                if (101 != code) {
                    System.out.println("connection failed");
                    System.exit(0);
                }
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


}
