package com.forecaster.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//配置分页插件
@Configuration
public class MyBatisPlusConfig {
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();

        //先创建一个分页插件实体类，然后设置一下你要配置的相关信息，
        PaginationInnerInterceptor pageinterceptor = new PaginationInnerInterceptor(DbType.H2);
        pageinterceptor.setMaxLimit(100L);      //设置单次最多查询100条数据
        interceptor.addInnerInterceptor(pageinterceptor);       //然后将方法添加到总的Interceptorl里面

        return interceptor;
    }

}
