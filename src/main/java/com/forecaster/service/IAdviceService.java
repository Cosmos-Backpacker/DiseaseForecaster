package com.forecaster.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.forecaster.pojo.Advice;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author CosmosBackpacker
 * @since 2024-01-29
 */
public interface IAdviceService extends IService<Advice> {

    Advice getHeartByRandom();
}
