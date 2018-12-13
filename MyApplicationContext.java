package com.example.userweb.applicationContext;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class MyApplicationContext implements ApplicationContextAware {

    private static ApplicationContext ac ;

    @Override
    public void setApplicationContext(org.springframework.context.ApplicationContext applicationContext) throws BeansException {
        ac = applicationContext;

    }

    public static Object getBean(String beanName){
        return ac.getBean(beanName);
    }

    public static Object getBean(Class clazz){
        return ac.getBean(clazz);
    }

}
