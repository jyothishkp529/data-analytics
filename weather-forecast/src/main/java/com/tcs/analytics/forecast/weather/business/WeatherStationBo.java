package com.tcs.analytics.forecast.weather.business;

import java.util.List;

import org.apache.log4j.Logger;

import com.tcs.analytics.forecast.weather.dao.WeatherForecastDaoFactory;
import com.tcs.analytics.forecast.weather.dao.WeatherStationDao;
import com.tcs.analytics.forecast.weather.domain.WeatherStationDo;

/**
 * Contains methods to retrieve WeatherStation details.
 * 
 * @author Jyothish Kalavoor Parambil
 * @version 1.0
 */
public class WeatherStationBo {
	public static final Logger LOGGER = Logger.getLogger(WeatherStationBo.class);

	/**
	 * Retrieves the details of a single weather station.
	 * 
	 * @param stationCode
	 * @return the domain objects contains details of a single weather station.
	 */
	public static WeatherStationDo retrieveWeatherStation(String stationCode) {
		LOGGER.debug("Enter retrieveWeatherStation.");

		WeatherStationDao weatherStationDao = WeatherForecastDaoFactory.getWeatherStationDao();

		LOGGER.debug("Exit retrieveWeatherStation.");
		return weatherStationDao.getWeatherStation(stationCode);
	}

	/**
	 * Retrieves all the weather station details.
	 * 
	 * @return the list of domain objects contains details of all weather
	 *         stations.
	 */
	public static List<WeatherStationDo> retrieveAllWeatherStations() {
		LOGGER.debug("Enter retrieveAllWeatherStations.");

		WeatherStationDao weatherStationDao = WeatherForecastDaoFactory.getWeatherStationDao();

		LOGGER.debug("Exit retrieveAllWeatherStations.");
		return weatherStationDao.getAllWeatherStations();
	}

}
