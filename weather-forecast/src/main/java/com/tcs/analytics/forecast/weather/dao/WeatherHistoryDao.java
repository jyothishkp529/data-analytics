package com.tcs.analytics.forecast.weather.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.joda.time.DateTime;

import com.tcs.analytics.forecast.weather.domain.WeatherHistoryDo;

/**
* @author Jyothish Kalavoor Parambil
* @version 1.0
* 
*/
public interface WeatherHistoryDao {

	//WeatherHistoryDo getHistoricalWeather(String location, DateTime time);

	List<WeatherHistoryDo> getLocationBasedHistoricalWeather(@Param("location") String location);

	List<WeatherHistoryDo> getHistoryWeatherData(@Param("stationId") String stationId,
			@Param("startDate") String startDate, @Param("endDate") String endDate);
}
