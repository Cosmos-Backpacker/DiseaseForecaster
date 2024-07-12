package com.forecaster.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
/**
 * <p>
 *
 * </p>
 *
 * @author CosmosBackpacker
 * @since 2024-01-23
 */

/**
 * 分页查询结果封装类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageBean {
    private Long total; //记录总数据数
    private List rows;  //记录返回的列表资源
}
