package com.lgb.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.Arrays;

public class TestAop {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestAop.class);

    //切点
    @Pointcut("execution(* com.lgb.service.serviceImpl.*ServiceImpl.*(..)))")
    public void pointCut(){}

    //前置通知
    @Before("pointCut()")
    public void before(JoinPoint jp ){  //jp连接点（spring拦截的方法）
        //获取参数
        Object[] parameter = jp.getArgs();
        System.out.println(jp);
        System.out.println("before....................开启事物");
    }

    //后置通知
    @After("pointCut()")
    public void after(){
        System.out.println("after....................提交事物");
    }

    //返回通知
    @AfterReturning("pointCut()")
    public void afterReturning(){
        System.out.println("afterReturning.....................释放资源.");
    }

    //环绕增强
    @Around("pointCut()")
    public Object aroud(ProceedingJoinPoint jp) throws Throwable {
        System.out.println("around before.....................");

        // 获取真实类名
        String targetName = jp.getTarget().getClass().getName();
        System.out.println("代理的类是:" + targetName);

        // 获取到方法签名，进而获得方法
        MethodSignature signature = (MethodSignature) jp.getSignature();
        Method method = signature.getMethod();
        System.out.println("增强的方法名字是:" + method.getName());
        //处理一些业务逻辑

        // 获取参数类型
        // 获取到参数
        Object[] parameter = jp.getArgs();
        System.out.println("传入的参数是:" + Arrays.toString(parameter));
        Class<?>[] parameterTypes = method.getParameterTypes();
        System.out.println("参数类型是:" + parameterTypes.toString());

        //代理执行方法
        jp.proceed();
        //System.out.println("around after........................");\
        return null;
    }

    //异常通知
    @AfterThrowing("pointCut()")
    public void afterThrowing(){
        System.out.println("afterThrowing......................事物回滚");
    }


}
