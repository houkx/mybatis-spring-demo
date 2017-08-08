/**
 * 
 */
package com.mq.demo.springmybatis.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 *
 */
@Aspect
@Component
public class LogAspect {

	@Around("execution (* com.mq.demo.springmybatis.dao.*.*(..))")
	public Object daoLog(ProceedingJoinPoint point) throws Throwable{
		Object rt = null;
		try {
			rt = point.proceed();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			System.out.println("*LogAspect: "+point+", return: "+rt);
		}
		return rt;
	}
}
