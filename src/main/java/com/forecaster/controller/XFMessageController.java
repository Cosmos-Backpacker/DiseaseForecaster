package com.forecaster.controller;

import com.forecaster.bean.WebSocketBigModel.ResultBean;
import com.forecaster.service.XFService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author: ChengLiang
 * @CreateTime: 2023-10-18  15:15
 * @Description: TODO
 * @Version: 1.0
 */


@Slf4j
@RestController
@RequestMapping("/xfModel")
public class XFMessageController {

    @Autowired
    private XFService XFService;

    @GetMapping("/BigModel")
    public ResultBean test(String id, String text) {
        if (StringUtils.isEmpty(id) || StringUtils.isEmpty(text)) {
            log.error("uid或text不能为空");
            return ResultBean.fail("uid或text不能为空");
        }
        return XFService.pushMessageToXFServer(id, text);
    }


    @PostMapping("/OCR")
    public ResultBean XFOcr(@RequestParam("file") MultipartFile file) {
        try {
            return XFService.OcrRequest(file);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/imageU")
    public ResultBean XFImageU(@RequestParam("file") MultipartFile file, String id, String text) {
        if (StringUtils.isEmpty(id) || StringUtils.isEmpty(text)) {
            log.error("uid或text不能为空");
            return ResultBean.fail("uid或text不能为空");
        }
        return XFService.ImageUnderstand(file, id, text);
    }


    @PostMapping("/imageG")
    public ResultBean XFImageG(String uid, String content) {
        if (StringUtils.isEmpty(uid) || StringUtils.isEmpty(content)) {
            return ResultBean.fail("uid或text不能为空");
        }

        return ResultBean.success( XFService.ImageGeneration(uid, content));
        //return ResultBean.success("");
    }


}
