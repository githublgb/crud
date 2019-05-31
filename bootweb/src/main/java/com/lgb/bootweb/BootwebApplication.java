package com.lgb.bootweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@SpringBootApplication
//@EnableEurekaClient
@EnableSwagger2 //启动swagger注解
@ImportResource(locations = {"classpath:springConfig/spring.xml"})  //导入spirngXml文件的配置类
public class BootwebApplication {
    public static void main(String[] args) {
      /*  new Object() {
            void f (Object j) {
                // todo something
            }
        }.f(new Object());*/
        SpringApplication.run(BootwebApplication.class, args);
    }
}
