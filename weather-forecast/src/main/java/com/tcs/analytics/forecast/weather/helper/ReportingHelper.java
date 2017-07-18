
package com.tcs.analytics.forecast.weather.helper;

import java.util.List;

import com.tcs.analytics.forecast.weather.domain.EuclideanDistanceVo;
import com.tcs.analytics.forecast.weather.domain.WeatherForecastDo;
import com.tcs.analytics.forecast.weather.domain.WeatherHistoryDo;

/**
 *
 * @author  Jyothish Kalavoor Parambil
 * @version 1.0
 */
public class ReportingHelper {

	public static void printWeatherForecastingData(List<WeatherForecastDo> weatherForecastDoList) {

		String Header = "StationCode||Latitude|Longitude|DateTime|Conditions|Temp|Pressure|Humidity";
		System.out.println("\n"+Header);
		for (WeatherForecastDo weatherForecastDo : weatherForecastDoList) {
			String data = new StringBuilder().append(weatherForecastDo.getStationCode()).append("|")
					.append(weatherForecastDo.getLatitude()).append("|").append(weatherForecastDo.getLongitude())
					.append("|").append(weatherForecastDo.getTime()).append("|")
					.append(weatherForecastDo.getWeatherCondition()).append("|")
					.append(weatherForecastDo.getTemperature()).append("|").append(weatherForecastDo.getPressure())
					.append("|").append(weatherForecastDo.getHumidity()).toString();

			System.out.println(data);
		}

	}
	
	/**
	 * @param weatherHistoryDoList
	 */
	public static void printWeatherData(List<WeatherHistoryDo> weatherHistoryDoList) {
		weatherHistoryDoList.forEach(weatherStationDo -> {
			System.out.println(weatherStationDo);
		});
	}
	
	/**
	 * @param weatherHistoryDoList
	 */
	public static void printEuclideanDistance(List<EuclideanDistanceVo> euclideanDistanceVoList) {
		euclideanDistanceVoList.forEach(r -> {
			System.out.println(r);
		});
	}
}
