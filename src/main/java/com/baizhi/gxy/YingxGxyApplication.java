package com.baizhi.gxy;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@tk.mybatis.spring.annotation.MapperScan("com.baizhi.gxy.dao")
@MapperScan("com.baizhi.gxy.dao")
@SpringBootApplication
public class YingxGxyApplication {

    public static void main(String[] args) {
        SpringApplication.run(YingxGxyApplication.class, args);
    }

}
