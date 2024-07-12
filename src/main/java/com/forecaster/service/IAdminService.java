package com.forecaster.service;

import com.forecaster.pojo.Admin;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author CosmosBackpacker
 * @since 2024-01-23
 */
@Service
public interface IAdminService extends IService<Admin> {

    Admin getByUsernameAndPassword(Admin admin);
}
