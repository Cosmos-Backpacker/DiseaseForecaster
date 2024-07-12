package com.forecaster.service;

import com.forecaster.pojo.PageBean;
import com.forecaster.pojo.Userheart;
import com.baomidou.mybatisplus.extension.service.IService;
import com.forecaster.query.UserheratQuery;
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
public interface IUserheartService extends IService<Userheart> {

    PageBean findByUid(UserheratQuery userheratQuery);

    Userheart getByIndex(Integer uid, Integer index);
}
