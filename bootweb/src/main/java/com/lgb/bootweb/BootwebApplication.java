package com.lgb.bootweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
//@EnableEurekaClient
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
