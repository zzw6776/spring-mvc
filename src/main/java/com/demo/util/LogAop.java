package com.demo.util;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogAop {
	private static final Logger logger = LoggerFactory.getLogger(LogAop.class);
	@Around("execution(* com.demo.mybatis3.dao.*.*(..))")
	public Object mybatisLog(ProceedingJoinPoint pjp) throws Throwable {
		Object result = pjp.proceed();
		logger.info("我是mybatisAop环绕日志");
		return result;
	}
}
