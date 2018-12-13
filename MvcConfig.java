package com.example.userweb.conf;

import com.example.userweb.interceptor.MyInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new MyInterceptor())
                .addPathPatterns("/userserver/findUsers");

    }

    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/showlogin").setViewName("login");
        registry.addViewController("/showregister").setViewName("register");
        registry.addViewController("/showindex").setViewName("index");
    }

}
