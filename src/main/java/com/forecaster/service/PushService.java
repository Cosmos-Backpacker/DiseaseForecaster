package com.forecaster.service;

import com.forecaster.bean.ResultBean;

public interface PushService {

    void pushToOne(String uid, String text);

    void pushToAll(String text);

    ResultBean pushMessageToXFServer(String uid, String text);
}
