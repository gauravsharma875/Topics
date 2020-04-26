package com.example.RabbitMqListener.storeandforward.beancontextUtils;

import lombok.val;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

import static java.lang.String.format;

public class ApplicationContextUtils {

    public static <B> B createBeanInApplicationContext(
            ApplicationContext applicationContext, String beanName, B bean) {

        try {
            ConfigurableListableBeanFactory beanFactory = beanFactory(applicationContext);
            if (!beanFactory.containsSingleton(beanName)) {
                beanFactory.registerSingleton(beanName, bean);
            }
            return (B) beanFactory.getSingleton(beanName);

        } catch (Exception e) {
            throw new RuntimeException(
                    format(
                            "Error creating bean with name %s and value " +
                                    "%s in application context",
                            beanName, bean), e);
        }
    }

    private static ConfigurableListableBeanFactory beanFactory(
            ApplicationContext applicationContext) {
        return ((ConfigurableApplicationContext) applicationContext)
                .getBeanFactory();
    }
}
