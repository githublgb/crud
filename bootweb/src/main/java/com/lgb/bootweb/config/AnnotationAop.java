package com.lgb.bootweb.config;

import com.lgb.bootweb.annotation.MyAnnotation;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//@Component
//@Aspect//切面 =切点+ 增强（通知）
public class AnnotationAop {
    public  static final Logger LOGGER = LoggerFactory.getLogger(AnnotationAop.class);

    /**
     * 前置通知：获取调用*serviceImpl的什么方法和ip和入参
     * @param joinPoint：连接点
     * @throws Exception
     */
    @Before("@annotation(myAnnotation)")
    public String allBefore(JoinPoint joinPoint, MyAnnotation myAnnotation) throws Exception {

        //根据注解的aop
        LOGGER.info("注解Aop的切入点"+myAnnotation.name());
        //被代理的真实类
        String className = joinPoint.getTarget().getClass().getName();

        //获取被代理类中方法的签名，继而获取方法名称
        String methodName = joinPoint.getSignature().getName();

        return "这是这个方法的前置通知";
    }
}
