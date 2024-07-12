package com.forecaster.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import java.io.IOException;

//因为这个工具在此项目中用处较少，所以直接在这里面处理返回值了
public class OkHttpUtil {
    public String get(String issue) {
        String errorTip="出错了呢，请联系管理员处理";
        OkHttpClient client = new OkHttpClient();

        String url = "https://api.ownthink.com/bot?appid=686ad2b95029592a5311d0ec5edcbca7&userid=user&spoken=" + issue;
        Request request = new Request.Builder()
                .url(url)
                .build();
        try {
            Response response = client.newCall(request).execute();

            if (response.isSuccessful()) {
                String responseBody = response.body().string();

                //这是获取到的内容
///{
//                    "message": "success",
//                            "data": {
//                        "type": 5000,
//                                "info": {
//                            "text": "你也好啊"
//                        }
//                    }
//                }

                JSONObject jsonObject = JSON.parseObject(responseBody);
                //先获取到data数据{"type":5000,"info":{"text":"你也好啊"}}
                Object data = jsonObject.get("data");
                JSONObject dataJson = JSON.parseObject(data.toString());
                //获取下面的info对象
                String infoJson = dataJson.get("info").toString();
                //获取里面text的值
                String answer=JSON.parseObject(infoJson).get("text").toString();

                return answer;
            } else {

                System.out.println("请求失败，错误码：" + response.code());
                return "请求失败，错误码：" + response.code();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return errorTip;
    }


}
