package com.forecaster.controller;


import com.forecaster.pojo.Admin;
import com.forecaster.pojo.Result;
import com.forecaster.service.IAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 * @author CosmosBackpacker
 * @since 2024-01-23
 */
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private IAdminService adminService;

    //查询所有管理员信息
    @GetMapping
    public Result findAll() {
        List<Admin> list = adminService.list();
        return Result.success("信息查询成功",list);
    }

    //添加管理员信息
    @PostMapping
    public Result add(@RequestBody Admin admin) {

        admin.setCreatTime(LocalDateTime.now());
        admin.setUpdateTime(LocalDateTime.now());

        adminService.save(admin);
        return Result.success("信息添加成功");
    }

    //批量删除管理员信息
    @DeleteMapping("/{ids}")
    public Result delete(@PathVariable List<Integer> ids) {
        adminService.removeBatchByIds(ids);
        return Result.success("删除成功");
    }

    //根据id修改管理员信息
    @PutMapping
    public Result update(@RequestBody Admin admin) {
        admin.setUpdateTime(LocalDateTime.now());
        adminService.updateById(admin);
        return Result.success("信息更改成功");
    }

}
