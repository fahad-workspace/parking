package com.challenge.parking.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

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
			StopWatch ticktock = new StopWatch();
			ticktock.start();
			retVal = joinPoint.proceed();
			ticktock.stop();
			String endMessageLogger =
					"Exit -> " + joinPoint.getTarget().getClass().getName() + " - " + joinPoint.getSignature().getName() + "() - Execution Time :: " + ticktock.getTotalTimeMillis()
							+ " ms.";
			log.info(endMessageLogger);
		} catch (Exception ex) {
			log.error("Exception cause :: " + ex.getMessage(), ex);
		}
		return retVal;
	}
}
