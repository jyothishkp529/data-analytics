/**
 * 
 */
package com.tcs.analytics.forecast.weather.business.exception;

import com.tcs.analytics.forecast.weather.exception.BaseAppException;

/**
 * The  WeatherHistoryBusinessExcption class.
 * 
 * @author  Jyothish Kalavoor Parambil
 * @version 1.0
 */
public class WeatherHistoryBusinessExcption extends BaseAppException {
	private static final long serialVersionUID = 1L;

	public WeatherHistoryBusinessExcption(String errorMessage) {
		super(errorMessage);
	}

}
