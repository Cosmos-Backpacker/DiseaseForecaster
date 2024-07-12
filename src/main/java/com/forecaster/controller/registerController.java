package com.forecaster.controller;


import com.forecaster.pojo.Result;
import com.forecaster.pojo.User;
import com.forecaster.service.IUserService;
import com.forecaster.service.impl.UserServiceImpl;
import io.jsonwebtoken.impl.crypto.RsaProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/register")
public class registerController {
    @Autowired
    private IUserService userService;

    @PostMapping
    public Result userRegister(@RequestBody User user) {


        try {
            userService.save(user);
            return Result.success("恭喜你注册成功，请重新登陆一次");
        } catch (Exception e) {
            return Result.error("用户名已存在");
        }
        //用if,else拦截不到这个异常
//        if (isRegister) {
//            return Result.success("恭喜你，注册成功");
//        } else {
//            System.out.println("注册失败");
//            return Result.error("用户名已存在，请尝试登录");
//        }

    }


}
