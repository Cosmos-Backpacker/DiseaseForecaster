package com.forecaster.service;

import com.forecaster.bean.WebSocket.ResultBean;

public interface XFService {

    void pushToOne(String uid, String text);

    void pushToAll(String text);

    ResultBean pushMessageToXFServer(String uid, String text);

     ResultBean OcrRequest() throws Exception;


}
