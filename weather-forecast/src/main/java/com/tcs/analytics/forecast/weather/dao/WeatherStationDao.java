package com.tcs.analytics.forecast.weather.dao;

import java.util.List;

import com.tcs.analytics.forecast.weather.domain.WeatherStationDo;

/**
* @author Jyothish Kalavoor Parambil
* @version 1.0
* 
*/
public interface WeatherStationDao {

	List<WeatherStationDo> getAllWeatherStations();
		//	throws ForeCasterDataException;
	
	WeatherStationDo getWeatherStation(String stationId);

}
