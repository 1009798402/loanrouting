package com.yongjun.loanrouting.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author jianchun.chen.
 * @Date 2019-08-19 17:20.
 * @Description: 日志打印
 */
@Slf4j
@Aspect
@Component
public class RoutingAspect {

    @Pointcut("execution(public * com.yongjun.loanrouting.controller.*.*(..))")
    public void log(){}

    /**
     * 获得请求的信息打印日志
     * @param joinPoint
     */
    @Before("log()")
    public void before(JoinPoint joinPoint){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert attributes != null;
        HttpServletRequest request = attributes.getRequest();

        log.info("[用户请求] ip = {} , url = {} , method = {} , class method = {} , args = {} ",
                request.getRemoteAddr()
                ,request.getRequestURL()
                ,request.getMethod()
                ,joinPoint.getSignature().getDeclaringTypeName()+"."+joinPoint.getSignature().getName()
                , joinPoint.getArgs());
    }

    @After("log()")
    public void after(){
    }


    /**
     * 获得返回的信息打印日志
     */
    @AfterReturning(returning = "object",pointcut = "log()")
    public void doAfterReturning(Object object){
        log.info("response = {}",object);
    }
}
