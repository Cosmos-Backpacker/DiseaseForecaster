package com.forecaster.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.forecaster.pojo.Admin;
import com.forecaster.mapper.AdminMapper;
import com.forecaster.service.IAdminService;
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
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements IAdminService {

    @Autowired
    private AdminMapper adminMapper;

    @Override
    public Admin getByUsernameAndPassword(Admin admin) {
        //构造查询条件
        QueryWrapper<Admin> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("account", admin.getAccount())
                .eq("password", admin.getPassword());

        //根据条件构造器查询结果并返回结果
        Admin admin1 = adminMapper.selectOne(queryWrapper);

        //返回查询结果
        return admin1;


    }
}
