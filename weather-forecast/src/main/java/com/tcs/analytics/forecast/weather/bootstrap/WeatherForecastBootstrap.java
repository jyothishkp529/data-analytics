package com.tcs.analytics.forecast.weather.bootstrap;

import org.apache.log4j.Logger;

import com.tcs.analytics.forecast.weather.constants.ApplicationConstants;
import com.tcs.analytics.forecast.weather.service.HistoricalDataBasedWeatherForecastingService;
import com.tcs.analytics.forecast.weather.service.WeatherForecastingService;
import com.tcs.analytics.forecast.weather.util.ExceptionHandler;

/**
 * Starting point of the application. Validates the input and initiate the WeatherData generation.
 * 
 * @author Jyothish Kalavoor Parambil
 * @version 1.0
 */
public class WeatherForecastBootstrap {

	static final Logger LOGGER = Logger.getLogger(WeatherForecastBootstrap.class);

	public static void main(String[] args) {
		int preditionDays = ApplicationConstants.DEFAULT_PREDICTION_DAYS;
		try {
			if (args.length == 1) {
				preditionDays = Integer.parseInt(args[0].trim());
				
			} 
			WeatherForecastingService weatherForecastingService = new HistoricalDataBasedWeatherForecastingService();
			weatherForecastingService.forecast(preditionDays);
			
			
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
		}

		
	}

}
