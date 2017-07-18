package com.tcs.analytics.forecast.weather.service;

import java.util.List;

import com.tcs.analytics.forecast.weather.domain.WeatherForecastDo;

/**
 * The forecasting services to predict weather.
 * 
 * @author Jyothish Kalavoor Parambil
 * @version 1.0
 *
 */
public interface WeatherForecastingService {

	/**
	 * Forecast the weather for the number of days of all the stations.
	 * 
	 * @param days
	 *            the number of days from the current date.
	 */
	void forecast(Integer days);

	/**
	 * 
	 * Forecast the weather for the number of days of a single station.
	 * 
	 * @param stationCode the weather station code.
	 * 
	 * @param days
	 *            the number of days from the current date.
	 * 
	 * 
	 * @return the weather forecast of each day from the current date to the
	 *         number of days.
	 */
	List<WeatherForecastDo> forecast(String stationCode, Integer days);
	
	
}
