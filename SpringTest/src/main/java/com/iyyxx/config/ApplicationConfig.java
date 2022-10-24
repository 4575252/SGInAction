package com.iyyxx.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;

/**
 * @className: ApplicationConfig
 * @description: 测试spring采用注解配置
 * @author: eric 4575252@gmail.com
 * @date: 2022/10/20/0020 12:27:46
 **/
@Configuration
@ComponentScan(basePackages = "com.iyyxx")
@PropertySource("jdbc.properties")
@EnableAspectJAutoProxy(proxyTargetClass = false)
public class ApplicationConfig {

    @Value("${jdbc.url}")
    private String url;

    @Bean
    public DruidDataSource dataSource(){
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl(url);
        dataSource.setUsername("root");
        dataSource.setPassword("123456");
        return dataSource;
    }
}
