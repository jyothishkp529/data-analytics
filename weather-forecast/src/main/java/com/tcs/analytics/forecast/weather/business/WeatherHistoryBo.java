package com.tcs.analytics.forecast.weather.business;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;

import com.tcs.analytics.forecast.weather.business.exception.WeatherHistoryBusinessExcption;
import com.tcs.analytics.forecast.weather.dao.WeatherForecastDaoFactory;
import com.tcs.analytics.forecast.weather.dao.WeatherHistoryDao;
import com.tcs.analytics.forecast.weather.domain.WeatherHistoryDo;
import com.tcs.analytics.forecast.weather.util.WFDateTimeUtil;

/**
 * Contains methods to retrieve weather history details.
 * 
 * @author Jyothish Kalavoor Parambil
 * @version 1.0
 */
public class WeatherHistoryBo {
	public static final Logger LOGGER = Logger.getLogger(WeatherHistoryBo.class);

	public static List<WeatherHistoryDo> retrieveLocationBasedHistoricalWeather(String location) throws WeatherHistoryBusinessExcption {
		LOGGER.debug("Enter retrieveLocationBasedHistoricalWeather.");
		WeatherHistoryDao weatherHistoryDao = WeatherForecastDaoFactory.getWeatherHistoryDao();
		List<WeatherHistoryDo> weatherHistoryDoList = weatherHistoryDao.getLocationBasedHistoricalWeather(location);
		if (weatherHistoryDoList.isEmpty()) {
			throw new WeatherHistoryBusinessExcption("No Weather History data found. StationId : " + location);
		}
		LOGGER.debug("Exit retrieveLocationBasedHistoricalWeather.");
		return weatherHistoryDoList;
	}

	public static List<WeatherHistoryDo> getRecentSevenDaysWeatherData(List<WeatherHistoryDo> weatherHistoryDoList) {
		LOGGER.debug("Enter getRecentSevenDaysWeatherData.");
		List<WeatherHistoryDo> currentYearRecentSevenDaysData = null;
		if (null != weatherHistoryDoList) {

			currentYearRecentSevenDaysData = new ArrayList<WeatherHistoryDo>();
			int upperBound = weatherHistoryDoList.size();
			int lowerBound = upperBound -7;
			for (int i = lowerBound; i < upperBound; i++) {
				currentYearRecentSevenDaysData.add(weatherHistoryDoList.get(i));
			}
		}
		LOGGER.debug("Exit getRecentSevenDaysWeatherData.");
		return currentYearRecentSevenDaysData;
	}

	/**
	 * 
	 * @param weatherHistoryDoList
	 * @param predictionDate
	 * @param windowDuration
	 * @return
	 */
	public static List<WeatherHistoryDo> getPreviousYearWeatherData(List<WeatherHistoryDo> weatherHistoryDoList,
			String predictionDate, Integer windowDuration) {
		
		LOGGER.debug("Enter getPreviousYearWeatherData.");
		List<WeatherHistoryDo> previousYearWeatherDataList = null;

		if (null != weatherHistoryDoList) {

			previousYearWeatherDataList = new ArrayList<WeatherHistoryDo>();

			DateTime prevYearDate = WFDateTimeUtil.convertToDate(predictionDate).plusYears(-1);
			String windowFrom = WFDateTimeUtil.convertToString(prevYearDate.plusDays(-7));
			String windowTo = WFDateTimeUtil.convertToString(prevYearDate.plusDays(6));

			
			for (WeatherHistoryDo weatherHistoryDo : weatherHistoryDoList) {
				
				/*
				if (windowTo.compareTo(weatherHistoryDo.getDate()) > 0) {
					break;
				}
				*/

				if (weatherHistoryDo.getDate().compareTo(windowFrom) >= 0
						&& weatherHistoryDo.getDate().compareTo(windowTo) <= 0) {
					previousYearWeatherDataList.add(weatherHistoryDo);
				}
			}

		}
		LOGGER.debug("Exit getPreviousYearWeatherData.");
		
		return previousYearWeatherDataList;
	}

}
