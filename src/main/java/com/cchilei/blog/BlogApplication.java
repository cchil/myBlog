package com.cchilei.blog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;

/**
 * @author
 * 启动Spring Boot
 */
@MapperScan("com.cchilei.blog.dao")
@SpringBootApplication
@ServletComponentScan
@EnableCaching
public class BlogApplication {

    public static void main(String[] args) {
        //Jar 模式启动器
        SpringApplication.run(BlogApplication.class, args);

    }
}
