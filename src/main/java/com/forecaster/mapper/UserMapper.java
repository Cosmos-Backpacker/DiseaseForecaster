package com.forecaster.mapper;

import com.forecaster.pojo.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author CosmosBackpacker
 * @since 2024-01-23
 */

@Mapper
public interface UserMapper extends BaseMapper<User> {

}
