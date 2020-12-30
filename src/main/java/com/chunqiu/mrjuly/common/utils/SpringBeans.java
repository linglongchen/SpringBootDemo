package com.chunqiu.mrjuly.common.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;


/**
 * Created on 2018/1/15
 *
 * @author YJP
 */
@Component
public class SpringBeans implements ApplicationContextAware {

	private static ApplicationContext context;

	@Override
	public void setApplicationContext(ApplicationContext context) throws BeansException {
		SpringBeans.context = context;
	}

	public static Object getBean(String beanName) {
		if(StringUtils.isEmpty(beanName)){
			throw new IllegalArgumentException("bean name is required, may not be empty");
		}
		return context == null ? null : context.getBean(beanName);
	}

	public static String[] getBeanDefinitionNames() {
		return context.getBeanDefinitionNames();
	}
}