package com.forecaster.controller;


import com.forecaster.pojo.Result;
import com.forecaster.utils.OkHttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/medicalRobot")
public class SiZhiRobotController {

    @GetMapping
    public Result chatRobot(String issue) {
        //因为只有这一个功能所以就直接在这里封装了
        OkHttpUtil util = new OkHttpUtil();
        String answer = util.get(issue);

        return Result.success("success",answer);
    }

}
