package com.forecaster.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageQuery {
    //代表查询第几页,默认从第一页开始
    private int pageNo = 1;
    //代表一页显示多少信息，默认一页显示6个
    private int pageSize = 6;
    //按照什么属性来排序,默认用id来排序
    private String balance = "id";

}
