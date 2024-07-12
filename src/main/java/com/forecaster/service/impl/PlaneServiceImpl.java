package com.forecaster.service.impl;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.forecaster.pojo.PageBean;
import com.forecaster.pojo.Plane;
import com.forecaster.mapper.PlaneMapper;
import com.forecaster.query.PlaneQuery;
import com.forecaster.service.IPlaneService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author CosmosBackpacker
 * @since 2024-02-19
 */
@Service
public class PlaneServiceImpl extends ServiceImpl<PlaneMapper, Plane> implements IPlaneService {
    @Autowired
    private PlaneMapper planeMapper;

    @Override
    public PageBean getByUid(PlaneQuery planeQuery) {
        //先定义分页查询规则
        Page<Plane> p= Page.of(planeQuery.getPageNo(),planeQuery.getPageSize());
        //设置排序规则
        p.addOrder(new OrderItem(planeQuery.getBalance(),true));

        Page<Plane> page=lambdaQuery()
                //根据uid查询到此用户所有信息再分页
                .eq(planeQuery.getUid()!=0,Plane::getUid,planeQuery.getUid())
                .page(p);

        PageBean pageBean=new PageBean(p.getTotal(),p.getRecords());

        return pageBean;
    }
}
