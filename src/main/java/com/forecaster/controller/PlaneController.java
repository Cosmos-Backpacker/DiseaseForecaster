package com.forecaster.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.forecaster.pojo.PageBean;
import com.forecaster.pojo.Plane;
import com.forecaster.pojo.Result;
import com.forecaster.query.PlaneQuery;
import com.forecaster.service.IPlaneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 * @author CosmosBackpacker
 * @since 2024-02-14
 */
@RestController
@RequestMapping("/plane")
public class PlaneController {

    @Autowired
    private IPlaneService planeService;

    //根据uid分页查询
    @GetMapping
    public Result findByUid(PlaneQuery planeQuery){
       PageBean pageBean= planeService.getByUid(planeQuery);

        return Result.success("查询成功",pageBean);
    }

    //新增计划
    @PostMapping
    public Result add(@RequestBody Plane plane){
        planeService.save(plane);
        return Result.success("添加成功");
    }

    //批量删除信息
    @DeleteMapping("/{ids}")
    public Result delete(@PathVariable List<Integer> ids){
        planeService.removeBatchByIds(ids);
        return Result.success("删除成功");
    }

    //修改信息
    @PutMapping
    public Result update(@RequestBody Plane plane){
        planeService.updateById(plane);
        return Result.success("修改成功");
    }


}
