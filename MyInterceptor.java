package com.example.userweb.interceptor;

import com.example.userweb.applicationContext.MyApplicationContext;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MyInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        StringRedisTemplate redisTemplate = (StringRedisTemplate) MyApplicationContext.getBean(StringRedisTemplate.class);
        Boolean flag = false;
        System.out.println("---MyInterceptor----");

        Cookie[] cookies = request.getCookies();
        if(cookies != null){
            for (int i = 0;i<cookies.length;i++){
                Cookie cookie = cookies[i];
                if(cookie.getName().equals("BOOK_TOKEN")){
                    String token = cookie.getValue();
                    String str = redisTemplate.opsForValue().get("USER_TOKEN::"+token);
                    flag = true;
                }
            }
        }
        if (flag == false){

            response.sendRedirect("/user/showlogin");
        }
        //response.getOutputStream();
        return flag;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
