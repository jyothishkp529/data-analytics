/**
 * 
 */
package com.tcs.analytics.forecast.weather.util;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import com.tcs.analytics.forecast.weather.exception.BaseAppException;

/**
 * The application Exception handler.
 * 
 * @author Jyothish Kalavoor Parambil
 * @version 1.0
 */
public class ExceptionHandler {
	public static final Logger LOGGER = Logger.getLogger(ExceptionHandler.class);

	/**
	 * Records the error message in the log file itself.
	 * 
	 * @param e
	 */
	public static void handleException(BaseAppException e) {
		LOGGER.error(e.getErrorMessage());
	}
	
	/**
	 * Records the error message in the log file itself.
	 * 
	 * @param e
	 */
	public static void handleException(Exception e) {
		StringBuilder msg = new StringBuilder();
		for (String errorTrace : ExceptionUtils.getRootCauseStackTrace(e)) {
			msg.append(errorTrace).append("\n");
		}
		LOGGER.error(msg.toString());
		//ExceptionUtils.printRootCauseStackTrace(e);
	}
}
