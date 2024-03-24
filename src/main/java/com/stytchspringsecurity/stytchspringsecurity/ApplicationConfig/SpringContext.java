package com.stytchspringsecurity.stytchspringsecurity.ApplicationConfig;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author https://techgry.com
 * @created 10/29/2021
 *  NOTE:  This class is used to get a spring bean from IOC container
 *      to be used for regular java pojo classes.
 */

@Component
public class SpringContext implements ApplicationContextAware {

    private static ApplicationContext context;

    public static <T extends Object> T getBean(Class<T> beanClass) {
        return context.getBean(beanClass);
    }

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        setContext(context);
    }

    private static synchronized void setContext(ApplicationContext context){
        SpringContext.context = context;
    }
}