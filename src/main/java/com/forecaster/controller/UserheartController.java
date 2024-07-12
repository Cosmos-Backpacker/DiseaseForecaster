package com.forecaster.controller;

import com.forecaster.pojo.PageBean;
import com.forecaster.pojo.Result;
import com.forecaster.pojo.Userheart;
import com.forecaster.query.UserheratQuery;
import com.forecaster.service.IUserheartService;
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
 *
 */
@RestController
@RequestMapping("/user/userheart")
public class UserheartController {
    @Autowired
    private IUserheartService userheartService;

    //根据uid分页查询
    @GetMapping
    public Result findByUid(UserheratQuery userheratQuery) {
        PageBean pageBean = userheartService.findByUid(userheratQuery);
        return Result.success("查询成功",pageBean);

    }

    //根据uid查询出列表后根据Id排序然后根据index取值
    @GetMapping("/{uid}")
    public Result finByIndex(@PathVariable Integer uid ,Integer index ){
          Userheart userheart= userheartService.getByIndex(uid,index);
          return Result.success("查询成功",userheart);
    }





    //用户提交心脏病预测相关数据
    @PostMapping
    public Result addInfo(@RequestBody Userheart userheart) {
        userheartService.save(userheart);
        return Result.success("数据已上传");
    }


    //用户批量删除相关数据
    @DeleteMapping("/{ids}")
    public Result delete(@PathVariable List<Integer> ids) {
        userheartService.removeBatchByIds(ids);
        return Result.success("信息删除成功");
    }

    //修改用户相关数据
    @PutMapping
    public Result update(@RequestBody Userheart userheart) {

        userheartService.updateById(userheart);
        return Result.success("信息已更新");
    }


}
