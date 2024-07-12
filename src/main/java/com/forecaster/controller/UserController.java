package com.forecaster.controller;


import com.forecaster.pojo.PageBean;
import com.forecaster.pojo.Result;
import com.forecaster.pojo.User;
import com.forecaster.query.UserQuery;
import com.forecaster.service.IUserService;
import org.apache.ibatis.annotations.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author CosmosBackpacker
 * @since 2024-01-23
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService userService;

    //多条件分页查询
    @GetMapping
    public Result findComplexPage(UserQuery query) {

        PageBean pageBean = userService.findComplexPage(query);
        return Result.success("查询成功",pageBean);
    }

    //根据id查询用户信息
    @GetMapping("/{id}")
    public Result findById(@PathVariable Integer id) {
        User user=userService.getById(id);
        return Result.success("查询成功",user);
    }

    //新增用户
    @PostMapping
    public Result add(@RequestBody User user) {
        userService.save(user);
        return Result.success("信息添加成功");
    }

    //批量删除用户信息
    @DeleteMapping("/{ids}")
    public Result delete(@PathVariable List<Integer> ids) {
        userService.removeBatchByIds(ids);
        return Result.success("删除成功");
    }

    //修改用户信息
    @PutMapping
    public Result update(@RequestBody User user) {
        userService.updateById(user);
        return Result.success("信息更改成功");
    }


}
