package cn.tedu.store.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TimerAspect {
	
	@Around("execution(* cn.tedu.store.service.impl.*.*(..))")
	public Object aaaa(ProceedingJoinPoint pjp) throws Throwable{
		//记录开始时间
		long start = System.currentTimeMillis();
		
		//执行Service中的方法
		Object result = pjp.proceed();

		//记录结束时间对比
		long end = System.currentTimeMillis();
		System.err.println(end-start);
		//返回 
		return result;
	}
	
}
