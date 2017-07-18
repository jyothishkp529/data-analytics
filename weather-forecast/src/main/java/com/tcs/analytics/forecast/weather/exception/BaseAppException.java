/**
 * 
 */
package com.tcs.analytics.forecast.weather.exception;

/**
 * The Base Exception class for WeatherForecast module.
 * 
 * @author Jyothish Kalavoor Parambil
 * @version 1.0
 */
public class BaseAppException extends Exception {

	private static final long serialVersionUID = 1L;
	
	private String errorMessage;

	/**
	 * @param errorMessage
	 */
	public BaseAppException(String errorMessage) {
		super();
		this.errorMessage = errorMessage;
	}

	/**
	 * @return the errorMessage
	 */
	public String getErrorMessage() {
		return errorMessage;
	}

}
