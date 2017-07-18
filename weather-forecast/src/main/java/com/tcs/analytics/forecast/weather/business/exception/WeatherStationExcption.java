/**
 * 
 */
package com.tcs.analytics.forecast.weather.business.exception;

import com.tcs.analytics.forecast.weather.exception.BaseAppException;

/**
 * The WeatherStationExcption class.
 * 
 * @author  Jyothish Kalavoor Parambil
 * @version 1.0
 */
public class WeatherStationExcption extends BaseAppException {
	private static final long serialVersionUID = 1L;

	public WeatherStationExcption(String errorMessage) {
		super(errorMessage);
	}

}
