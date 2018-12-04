package com.aisafer.webgis.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 获取application对象工具类
 *
 * @Author:weiyuanlong
 * @Date: Created in 2018-10-25 14:43:27
 * @Modified By:
 */
@Component
public class SpringContextUtil implements ApplicationContextAware {

    /** 日志 */
    private static Logger logger = LoggerFactory.getLogger(SpringContextUtil.class);

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        logger.info("setApplicationContext 初始化。。。。。。");
        if (SpringContextUtil.applicationContext == null) {
            SpringContextUtil.applicationContext = applicationContext;
        }
    }

    /**
     * 实例化对象
     *
     * @param className
     * @return
     */
    public static Object getInstanceBean (String className){
        return applicationContext.getBean(className);
    }

    /**
     * 带参数类型实例化对象
     *
     * @param beanName
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T getInstanceBean(String beanName, Class<T> clazz) {
        T obj = applicationContext.getBean(beanName, clazz);
        return obj;
    }

}