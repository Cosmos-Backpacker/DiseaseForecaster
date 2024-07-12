package com.forecaster.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.forecaster.pojo.User;
import com.forecaster.mapper.UserMapper;
import com.forecaster.pojo.PageBean;
import com.forecaster.query.UserQuery;
import com.forecaster.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author CosmosBackpacker
 * @since 2024-01-23
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public PageBean findComplexPage(UserQuery query) {
        //配置分页查询的规则
        //设置查询多少页，每页显示多少条信息
        Page<User> p = Page.of(query.getPageNo(), query.getPageSize());
        //设置排序方式
        p.addOrder(new OrderItem(query.getBalance(), true));//默认为升序排列

        //执行查询
        Page<User> page = lambdaQuery()
                //根据昵称关键字查询
                .like(query.getNickname() != null, User::getNickname, query.getNickname())
                //根据账号查询
                .eq(query.getAccount() != null, User::getAccount, query.getAccount())
                //根据手机号查询
                .eq(query.getPhone() != null, User::getPhone, query.getPhone())
                //根据邮箱查询
                .eq(query.getEmail() != null, User::getEmail, query.getEmail())
                //根据性别查询
                .eq(query.getGender() != null, User::getGender, query.getGender())
                .page(p);

        //封装查询的结果
        PageBean pageBean = new PageBean(page.getTotal(), page.getRecords());
        return pageBean;
    }


    @Override
    public User getByAccountAndPassword(User user) {


        QueryWrapper<User> queryWrapper = new QueryWrapper<User>();
        queryWrapper.eq("account", user.getAccount());
        queryWrapper.eq("password", user.getPassword());


        User user1 = userMapper.selectOne(queryWrapper);

        return user1;
    }
}
