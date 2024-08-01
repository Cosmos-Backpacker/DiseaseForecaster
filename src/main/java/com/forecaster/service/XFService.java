package com.forecaster.service;

import com.forecaster.bean.WebSocketBigModel.ResultBean;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface XFService {

    void pushToOne(String uid, String text);

    void pushToAll(String text);

    ResultBean pushMessageToXFServer(String uid, String text);

    ResultBean OcrRequest(MultipartFile file) throws Exception;

     ResultBean ImageUnderstand(MultipartFile file,String uid, String text);

     String ImageGeneration(String uid,String content);
}
