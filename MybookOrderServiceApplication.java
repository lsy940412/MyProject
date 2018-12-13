package com.itany.orderservice;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import com.alibaba.dubbo.remoting.http.servlet.DispatcherServlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;

@SpringBootApplication
@EnableDubbo
public class MybookOrderServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MybookOrderServiceApplication.class, args);
    }


}
