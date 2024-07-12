package com.forecaster.controller;

import com.forecaster.utils.MailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.forecaster.service.SentMailService;

/**
 * 测试邮件发送
 * @author qzz
 */
@RestController
@RequestMapping("/mail")
public class SendMailController {

    @Autowired
    private MailUtil mailService;
    /**
     * 发送文本邮件
     * @param to
     * @param subject
     * @param text
     */
    @GetMapping("/sendTextMail")
    public void sendTextMail(String to,String subject,String text){

        mailService.sendTextMailMessage(to,subject,text);
    }

}

