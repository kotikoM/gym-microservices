package com.gym.crm.microservice.config;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggingConfig {
    ThreadLocal<String> transactionId = new ThreadLocal<>();

    @Pointcut("execution(* com.gym.crm.microservice.service.*.*(..))")
    private void serviceMethods() {}

    @AfterReturning(pointcut = "serviceMethods()", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        String response = (result != null) ? result.toString() : "null";
        log.info("Transaction ID: {}, Service: {}, Method: {}, Response: {}", transactionId.get(), className, methodName, response);
        transactionId.remove();
    }
}
