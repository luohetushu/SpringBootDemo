package com.spring.boot.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;

@Controller
public class ThymeleafController {

    @RequestMapping("/Thymeleaf")
    public String helloThymeleaf(ModelMap map){
        map.addAttribute("hello", "Hello, Thymeleaf!!!");
        map.addAttribute("today", new Date());
        return "hello";   // 默认对应 src/main/resources/templates/目录下的 hello.html 模板页面
    }

}
