package com.spring.boot;

import com.spring4all.swagger.EnableSwagger2Doc;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.annotation.MapperScans;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @SpringBootApplication 是 Spring Boot 的核心注解，
 * 是一个组合注解，该注解组合了：
 *     @Configuration
 *     @EnableAutoConfiguration  让 Spring Boot 根据类路径中的 jar 包依赖为当前项目进行自动配置
 *     @ComponentScan  自动扫描 @SpringBootApplication 所在类的同级包以及下级包里的 Bean，可以自定义扫描的包
 */
@EnableSwagger2Doc  // 使能 swagger 配置
@MapperScan("com.spring.boot.dao")
@SpringBootApplication
public class SpringBootDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootDemoApplication.class, args);
    }

}
