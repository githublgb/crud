package com.lgb.bootweb.config;

import com.alibaba.druid.pool.DruidDataSource;

import com.p6spy.engine.spy.P6DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.sql.DataSource;

@Configuration
//@Import(AopConfig.class)  //导入其他的配置类
//@ImportResource(locations = {"classpath:springConfig/spring.xml"})  //导入spirngXml文件的配置类
public class DataSourceConfig {

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DruidDataSource getDruidDataSource(){

        return  new DruidDataSource();
    }


}
