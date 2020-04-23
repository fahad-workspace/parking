package com.challenge.parking.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * @author Fahad Sarwar
 */
@Component
@Aspect
public class AOPAutoLogger {

	private final Logger log = LogManager.getLogger(AOPAutoLogger.class);

	@Around("execution(* com.challenge.parking..*.*(..))")
	public Object autoLog(ProceedingJoinPoint joinPoint) throws Throwable {

		Object retVal = null;
		try {
			String startMessageLogger = "Entry -> " + joinPoint.getTarget().getClass().getName() + " - " + joinPoint.getSignature().getName() + "()";
			log.info(startMessageLogger);
			retVal = joinPoint.proceed();
			String endMessageLogger = "Exit -> " + joinPoint.getTarget().getClass().getName() + " - " + joinPoint.getSignature().getName() + "()";
			log.info(endMessageLogger);
		} catch (Exception ex) {
			log.error("Exception cause :: " + ex.getMessage(), ex);
		}
		return retVal;
	}
}
