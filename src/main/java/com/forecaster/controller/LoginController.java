package com.forecaster.controller;


import com.forecaster.pojo.Admin;
import com.forecaster.pojo.Result;
import com.forecaster.pojo.User;
import com.forecaster.service.IAdminService;
import com.forecaster.service.IUserService;
import com.forecaster.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private IUserService userService;

    @Autowired
    private IAdminService adminService;

    @PostMapping("/user")
    public Result userLogin(@RequestBody User user) {
        //先通过传入的账号和密码找到用户
        User user1 = userService.getByAccountAndPassword(user);
        if (user1 != null) {
            //然后通过查询到的数据生成jwt令牌
            //设置加密载荷
            HashMap<String, Object> claims = new HashMap<>();
            claims.put("id", user1.getId().toString());
            claims.put("account", user1.getAccount());
            claims.put("password", user1.getPassword());
            //生成jwt令牌
            String jwt = JwtUtils.generateJwt(claims);
            //封装map信息返回
            HashMap<String,String> map=new HashMap<>();
            map.put("id",user1.getId().toString());
            map .put("account",user1.getAccount());
            map.put("nickname",user1.getNickname());
            map.put("token",jwt);
            return Result.success("登陆成功",map);
        } else {
            return Result.error("用户名或密码错误");
        }

    }


    @PostMapping("/admin")
    public Result adminLogin(@RequestBody Admin admin) {
        //先通过传入的数据找到管理员
        Admin admin1 = adminService.getByUsernameAndPassword(admin);
        //如果查询到了
        if (admin1 != null) {
            //先构造加密的内容（载荷）
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("id", admin1.getId().toString());
            hashMap.put("account", admin1.getAccount());
            hashMap.put("password", admin1.getPassword());
            //根据构建的载荷生成jwt令牌
            String jwt = JwtUtils.generateJwt(hashMap);
            hashMap.put("token",jwt);
            //重新组装json数据并返回(因为上面加密时必须是object对象)
            //如果直接讲HasMap当做结果传回，会因为有Object的存在导致穿的数据出现问题，所以重新构建一个map传回
            HashMap<String ,String > map=new HashMap<>();
            map.put("id",admin1.getId().toString());
            map.put("account", admin1.getAccount());
            map.put("nickname",admin1.getNickname());
            map.put("token",jwt);
            return Result.success("登陆成功",map);
        } else {
            return Result.error("用户名或密码错误");
        }
    }
}
