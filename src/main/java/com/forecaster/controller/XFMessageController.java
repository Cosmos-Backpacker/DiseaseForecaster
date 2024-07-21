package com.forecaster.controller;

import com.forecaster.bean.WebSocket.ResultBean;
import com.forecaster.pojo.Result;
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

    @GetMapping
    public ResultBean test(String id, String text) {
        if (StringUtils.isEmpty(id) || StringUtils.isEmpty(text)) {
            log.error("uid或text不能为空");
            return ResultBean.fail("uid或text不能为空");
        }
        return XFService.pushMessageToXFServer(id, text);
    }


    @PostMapping("/OCR")
    public ResultBean XFOcr(@RequestParam("file") MultipartFile file)
    {
        try {
            return XFService.OcrRequest(file);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
