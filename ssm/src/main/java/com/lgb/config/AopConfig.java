package com.lgb.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lgb.util.IpUtils;
import com.lgb.util.PageResult;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@Component
@Aspect//切面 =切点+ 增强（通知）
public class AopConfig {
    private static final Logger LOGGER = LoggerFactory.getLogger(AopConfig.class);
    private static final String POINT_CUT = "execution(* com.lgb.service.serviceImpl.*ServiceImpl.*(..)))";
    private static final String POINT_CUT_CONTROLLER = "execution(* com.lgb.controller..*.*(..))";

    @Pointcut(POINT_CUT)
    private void pointcut() {
    }

    @Pointcut(POINT_CUT_CONTROLLER)
    public void pointcutController() {
    }
    /**
     * 前置通知：获取调用*serviceImpl的什么方法和ip和入参
     * @param joinPoint：连接点
     * @throws Exception
     */
    @Before("pointcut()")
    public void allBefore(JoinPoint joinPoint) throws Exception {
        //被代理的真实类
        String className = joinPoint.getTarget().getClass().getName();

        //获取被代理类中方法的签名，继而获取方法名称
        String methodName = joinPoint.getSignature().getName();
        StringBuilder log = new StringBuilder();


        log.append(new StringBuilder().append("ip地址:")
                .append(IpUtils.getHostIp()).toString())

                //代理的真实类
                .append(new StringBuilder().append("所属类方法:").append(className).toString())
                .append(new StringBuilder().append(".").append(methodName).toString())

                .append("输入参数params:");

        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            log.append(new StringBuilder().append(JSONObject.toJSONString(arg)).append(", ").toString());
        }
        LOGGER.info(log.toString());
    }

    /**
     * 返回通知，获取调用*serviceImpl的什么方法和返回值
     * @param returnObj
     */
   @AfterReturning(value = "pointcut()", returning = "returnObj")
    public void afterReturn(Object returnObj) {
        StringBuilder sb = new StringBuilder();
        if (returnObj != null) {
            if ((returnObj instanceof PageResult)) {
                PageResult rd = (PageResult) returnObj;
                sb.append("返回参数params:");
                sb.append(new StringBuilder().append("total:").append(rd.getTotal()).toString());

                if (rd.getRows() != null) {
                    sb.append(new StringBuilder().append(",data:").append(JSONObject.toJSONString(rd.getRows())).toString());
                }
                if (rd.getRows().isEmpty()){

                    sb.append(new StringBuilder().append(",rows:").append(rd.getRows().size()).toString());
                }
            } else if ((returnObj instanceof List)) {
                List list = (List) returnObj;
                sb.append(new StringBuilder().append("返回参数params数据条数: ").append(list.size()).toString());
            } else {
                String result = JSONObject.toJSONString(returnObj);
                sb.append(new StringBuilder().append("返回参数params: ").append(result).toString());
            }
        }
        LOGGER.info(sb.toString());
    }

    /**
     * 异常通知
     * @param e
     */
    @AfterThrowing(value = "pointcut()", throwing = "e")
    public void afterThrowing(Throwable e) {
        LOGGER.error(e.getMessage(), e);
    }

    /**
     * 环绕通知：获取调用*serviceImpl的什么方法的耗时
     * @param joinPoint  ：连接点
     * @return
     * @throws Throwable
     */
    @Around("pointcut()")
    public Object allAround(ProceedingJoinPoint joinPoint) throws Throwable {
        //被代理的真实类
        String className = joinPoint.getTarget().getClass().getName();

        //获取被理类中方法的签名，继而获取方法名称
        String methodName = joinPoint.getSignature().getName();

        StringBuilder sb = new StringBuilder();

        Long begin = Long.valueOf(System.currentTimeMillis());
        Object result = joinPoint.proceed();
        Long end = Long.valueOf(System.currentTimeMillis());

             sb .append(new StringBuilder().append("所属类方法:").append(className).toString())
                .append(new StringBuilder().append(".").append(methodName).toString())
                .append("环绕通知: ")
                .append("执行时间: ")
                .append(end.longValue() - begin.longValue()).append("ms");
        LOGGER.info(sb.toString());
        return result;
    }
    /**
     * contorller 的前置通知,记录操作日志？
     * @param joinPoint
     * @param optLog
     */
    @Before("pointcutController()")
    public void beforeController(JoinPoint joinPoint) {

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

        LOGGER.info(data.toString()+"记录的是什么日志");
    }


}


