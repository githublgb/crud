package com.lgb.bootweb.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lgb.bootweb.annotation.SysOptLog;
import com.lgb.bootweb.domain.Person;
import com.lgb.bootweb.service.serviceImpl.PersonServiceImpl;
import com.lgb.bootweb.util.IpUtils;
import com.lgb.bootweb.util.PageResult;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Component
@Aspect//切面 =切点+ 增强（通知）
public class AopConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(AopConfig.class);

    private static final String POINT_CUT = "execution(* com.lgb.bootweb.service.serviceImpl.*ServiceImpl.*(..)))";
    private static final String POINT_CUT_CONTROLLER = "execution(* com.lgb.bootweb.controller..*.*(..))";

    //@Resource
    //private UserDubboService userDubboService;

    @Pointcut(POINT_CUT)
    private void pointcut() {
    }

    @Pointcut(POINT_CUT_CONTROLLER)
    public void pointcutController() {
    }

    /**
     * 前置通知：获取调用*serviceImpl的什么方法和ip和入参
     *
     * @param joinPoint：连接点
     * @throws Exception
     */
    @Before("pointcut()")
    public void allBefore(JoinPoint joinPoint) throws Exception {

        Signature signature = joinPoint.getSignature();//方法签名
        Object target = (PersonServiceImpl) joinPoint.getTarget();//获取目标类的对象
        String className = joinPoint.getTarget().getClass().getName();
        ((MethodSignature) joinPoint.getSignature()).getMethod();//获取方法

        String methodName = joinPoint.getSignature().getName();//这个是获取方法名称
        String name = joinPoint.getSignature().getDeclaringTypeName();//这个是获取类名称

        LOGGER.debug("前置通知: IP:{}  拦截的方法{}.{}() 方法参数 = {}", IpUtils.getHostIp(), joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(), Arrays.toString(joinPoint.getArgs()));

    }

    /**
     * 返回通知，获取调用*serviceImpl的什么方法和返回值
     *
     * @param returnObj
     */
    @AfterReturning(value = "pointcut()", returning = "returnObj")
    public void afterReturn(JoinPoint joinPoint, Object returnObj) {
        StringBuilder sb = new StringBuilder();
        //根据返回结果来打印,我的serverImpl只会返回Interger和PageResult
        long total;
        String s = "";
        if ((returnObj instanceof PageResult)) {//分页查询
            PageResult rd = (PageResult) returnObj;
            total = rd.getTotal();
            s = rd.getRows().toString();


        } else if ((returnObj instanceof Person)) {//单个查询
            Person per = (Person) returnObj;
            total = 0L;
            s = per.toString();
        } else {//删除，增加
            total = (long) ((int) returnObj);
            s = "";
        }

        LOGGER.debug("返回通知: IP:{}  拦截的方法{}.{}() 方法参数 = {} 返回结果:{}", IpUtils.getHostIp(), joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(), Arrays.toString(joinPoint.getArgs()), "条数" + total + "具体值" + s);
    }

    /**
     * 异常通知
     *
     * @param e
     */
    @AfterThrowing(value = "pointcut()", throwing = "e")
    public void afterThrowing(JoinPoint joinPoint, Throwable e) {

        LOGGER.info("异常通知：Exception in {}.{}() with cause = \'{}\' and exception = \'{}\'", joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(), e.getCause() != null ? e.getCause() : "NULL", e.getMessage(), e);

    }

    /**
     * 环绕通知：获取调用*serviceImpl的什么方法的耗时
     *
     * @param joinPoint ：连接点
     * @return
     * @throws Throwable
     */
    @Around("pointcut()")
    public Object allAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Signature signature = joinPoint.getSignature();//方法签名
        Object target = (PersonServiceImpl) joinPoint.getTarget();//获取目标类的对象
        String className = joinPoint.getTarget().getClass().getName();
        ((MethodSignature) joinPoint.getSignature()).getMethod();//获取方法

        String methodName = joinPoint.getSignature().getName();//这个是获取方法名称
        String name = joinPoint.getSignature().getDeclaringTypeName();//这个是获取类名称

        long begin = System.currentTimeMillis();
        Object result = joinPoint.proceed();//环绕通知这个方法是制作想目标方法
        long end = System.currentTimeMillis();

        LOGGER.debug("环绕通知: IP:{} , 拦截的方法{}.{}() 方法参数 = {} , 执行时间：{}", IpUtils.getHostIp(), joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(), Arrays.toString(joinPoint.getArgs()), (end - begin) + "ms");
        return result;
    }


    /**
     * contorller 的前置通知,记录操作日志
     *
     * @param joinPoint
     * @param optLog
     */
    @Before("pointcutController() && @annotation(sysOptLog)")
    public void beforeController(JoinPoint joinPoint, SysOptLog sysOptLog) {

        //被代理的真实类
        String className = joinPoint.getTarget().getClass().getName();

        //获取代被理类中方法的签名，继而获取方法名称
        String methodName = joinPoint.getSignature().getName();
        StringBuffer data = new StringBuffer();

        Map reuqstParam = null;
        Object[] args = joinPoint.getArgs();
        for (Object arg : args)
            if (!(arg instanceof HttpServletResponse)) {
                if ((arg instanceof HttpServletRequest)) {
                    HttpServletRequest request = (HttpServletRequest) arg;
                    reuqstParam = request.getParameterMap();
                } else {
                    data.append(new StringBuilder().append(JSONObject.toJSONString(arg)).append(", ").toString());
                }
            }
        if ((StringUtils.isEmpty(data.toString())) && (reuqstParam != null)) {
            data.append(JSON.toJSONString(reuqstParam));
        }


        LOGGER.info(sysOptLog.optStatus().getMsg() + "***" + sysOptLog.optStatus().getCode());
        LOGGER.info("操作日志：{}", data.toString());
    }


}


