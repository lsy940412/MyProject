package com.itany.bookweb;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;


@Component
public class ApplicationContextHold implements ApplicationContextAware {

	private static ApplicationContext ac;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		ac = applicationContext;
	}

	public static Object getBean(String beanName) {
		return ac.getBean(beanName);
	}

	public static <T> T getBean(Class<T> clazz) {
		return ac.getBean(clazz);
	}

}
