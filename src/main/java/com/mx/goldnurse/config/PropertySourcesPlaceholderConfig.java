package com.mx.goldnurse.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.annotation.Order;

/**
 * <br>
 * created date 2021/5/18 16:47
 *
 * @author DongJunHao
 */
@Order(1)
@Configuration
public class PropertySourcesPlaceholderConfig extends PropertySourcesPlaceholderConfigurer implements BeanDefinitionRegistryPostProcessor {

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanDefinitionRegistry) throws BeansException {
        postProcessBeanFactory((ConfigurableListableBeanFactory) beanDefinitionRegistry);
    }
}
