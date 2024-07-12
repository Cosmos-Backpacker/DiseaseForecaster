package com.forecaster.service;

import com.forecaster.utils.MailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Service
public class SentMailService {
    @Autowired
    private MailUtil mailUtil;


    @Scheduled(cron = "0 0 8,22 * * ?")//每天早上八点和晚上十点执行
    public void test() {
        String to="3317194303@qq.com";
        String subject="这是一个主题";
        String text="这是邮件内容";

//        LocalTime localTime = LocalTime.now();
//        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
//       String time2=dtf.format(localTime);
//
//        System.out.println(time2);
//
//       String userTime=planeService.getById(2).getPlanetime();
//        if (time2.equals(userTime)) {
//            System.out.println(localTime);
//        }


        mailUtil.sendTextMailMessage(to,subject,text);


    }


}
