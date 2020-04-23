package com.challenge.parking.utils;

import java.util.Arrays;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

/**
 * @author Fahad Sarwar
 */
@Component
public class AOPTimeLogger {

	private final Logger log = LogManager.getLogger(AOPTimeLogger.class);

	@Around("execution(* com.challenge.parking..*.*(..))")
	public Object timeMethod(ProceedingJoinPoint joinPoint) throws Throwable {

		StopWatch ticktock = new StopWatch();
		ticktock.start();
		Object retVal = joinPoint.proceed();
		ticktock.stop();
		StringBuilder logMessage = new StringBuilder();
		logMessage.append(joinPoint.getTarget().getClass().getName());
		logMessage.append(".");
		logMessage.append(joinPoint.getSignature().getName());
		logMessage.append("(");
		Object[] args = joinPoint.getArgs();
		Arrays.stream(args).forEachOrdered(arg -> logMessage.append(arg).append(","));
		if (args.length > 0) {
			logMessage.deleteCharAt(logMessage.length() - 1);
		}
		logMessage.append(")");
		logMessage.append(" Execution Time :: ");
		logMessage.append(ticktock.getTotalTimeMillis());
		logMessage.append(" ms.");
		log.info(logMessage.toString());
		return retVal;
	}
}
