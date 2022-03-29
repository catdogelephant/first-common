package com.mx.goldnurse.config;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author yho
 */
@Component
public class SpringContextHolder implements ApplicationContextAware {

    private static ApplicationContext appContext = null;

    /**
     * 通过name获取 Bean.
     *
     * @param name
     * @return
     */
    public static Object getBean(String name) {
        return appContext.getBean(name);

    }

    /**
     * 通过class获取Bean.
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T getBean(Class<T> clazz) {
        return appContext.getBean(clazz);
    }

    /**
     * 通过name,以及Clazz返回指定的Bean
     *
     * @param name
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T getBean(String name, Class<T> clazz) {
        return appContext.getBean(name, clazz);
    }

    /**
     * 获取多个bean
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> List<T> getBeans(Class<T> clazz) {

        List<T> result = new ArrayList<>();
        try {
            Map<String, T> beansOfType = appContext.getBeansOfType(clazz);
            Collection<T> values = beansOfType.values();
            result = new ArrayList<>(values);
        } catch (BeansException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (appContext == null) {
            appContext = applicationContext;
        }
    }
}
