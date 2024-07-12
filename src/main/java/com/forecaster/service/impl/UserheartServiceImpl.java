package com.forecaster.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.forecaster.pojo.PageBean;
import com.forecaster.pojo.Userheart;
import com.forecaster.mapper.UserheartMapper;
import com.forecaster.query.UserheratQuery;
import com.forecaster.service.IUserheartService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author CosmosBackpacker
 * @since 2024-01-23
 */
@Service
public class UserheartServiceImpl extends ServiceImpl<UserheartMapper, Userheart> implements IUserheartService {
    @Override
    public PageBean findByUid(UserheratQuery userheratQuery) {
        //先定义分页查询规则
        Page<Userheart> p = Page.of(userheratQuery.getPageNo(), userheratQuery.getPageSize());
        //定一排序
        p.addOrder(new OrderItem(userheratQuery.getBalance(), true));

        //用lambda表达式查询
        Page<Userheart> page = lambdaQuery()
                //先根据uid找到某个用户的所有数据，然后再根据分页条件进行查询
                .eq(userheratQuery.getUid() != 0, Userheart::getUid, userheratQuery.getUid())
                .page(p);

        //封装对象
        PageBean pageBean = new PageBean(p.getTotal(), p.getRecords());
        return pageBean;
    }

    @Override
    public Userheart getByIndex(Integer uid, Integer index) {

        //先根据uid查询出列表
       QueryWrapper<Userheart> queryWrapper=new QueryWrapper<>();
       queryWrapper.eq("uid",uid)
               .orderByAsc("id");
       List<Userheart> list=list(queryWrapper);
       //根据index从list中取值
        return  list.get(index);
    }
}

