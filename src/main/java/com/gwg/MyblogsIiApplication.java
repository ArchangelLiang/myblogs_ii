package com.gwg;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.gwg.mapper")
public class MyblogsIiApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyblogsIiApplication.class, args);
    }

}