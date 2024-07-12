package com.forecaster.service;

import com.forecaster.pojo.PageBean;
import com.forecaster.pojo.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.forecaster.query.UserQuery;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author CosmosBackpacker
 * @since 2024-01-23
 */

@Service
public interface IUserService extends IService<User> {

    PageBean findComplexPage(UserQuery query);

    User getByAccountAndPassword(User user);
}
