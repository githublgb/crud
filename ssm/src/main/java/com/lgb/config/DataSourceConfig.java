package com.lgb.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.context.annotation.Bean;

//@Configuration
//@Import(AopConfig.class)  //导入其他的配置类
//@ImportResource(locations = {"classpath:springConfig/spring.xml"})  //导入spirngXml文件的配置类
public class DataSourceConfig {

    @Bean
   // @ConfigurationProperties(prefix = "spring.datasource")
    public DruidDataSource getDruidDataSource(){

        return  new DruidDataSource();
    }


}
