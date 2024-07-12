package com.forecaster.service;

import com.forecaster.pojo.PageBean;
import com.forecaster.pojo.Plane;
import com.baomidou.mybatisplus.extension.service.IService;
import com.forecaster.query.PlaneQuery;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author CosmosBackpacker
 * @since 2024-02-19
 */
@Service
public interface IPlaneService extends IService<Plane> {

    PageBean getByUid(PlaneQuery planeQuery);
}
