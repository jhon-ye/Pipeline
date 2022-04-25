package com.opencn.mesh;import org.springframework.beans.BeansException;import org.springframework.beans.factory.config.BeanPostProcessor;import org.springframework.context.ApplicationContext;import org.springframework.context.ApplicationContextAware;import org.springframework.core.annotation.AnnotationUtils;import org.springframework.util.ObjectUtils;import org.springframework.util.ReflectionUtils;public class BizMappingBeanPostProcessor implements BeanPostProcessor, ApplicationContextAware {    @Override    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {        return bean;    }    @Override    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {        BizService bizService = AnnotationUtils.findAnnotation(bean.getClass(), BizService.class);        if (!ObjectUtils.isEmpty(bizService)) {            ReflectionUtils.doWithMethods(bean.getClass(), (method) -> {                BizMapping annotation = AnnotationUtils.findAnnotation(method, BizMapping.class);                if (!ObjectUtils.isEmpty(annotation)) {                    String bizKey = annotation.bizKey();                    BizMappingRegister.registry(bizKey, beanName, method);                }            });        }        return bean;    }    @Override    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {        BizMappingRegister.applicationContext = applicationContext;    }}