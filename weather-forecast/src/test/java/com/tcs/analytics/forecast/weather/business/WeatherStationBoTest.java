package com.tcs.analytics.forecast.weather.business;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.junit.Test;

import com.tcs.analytics.forecast.weather.dao.MyBatisSessionFactory;
import com.tcs.analytics.forecast.weather.domain.WeatherStationDo;

/**
 * Unit test class for WeatherStationBo.
 * 
 * @author Jyothish Kalavoor Parambil
 * @version 1.0
 */
public class WeatherStationBoTest {
	public static final Logger LOGGER = Logger.getLogger(WeatherStationBoTest.class);

	@Test
	public void testSession() {
		SqlSession session = MyBatisSessionFactory.getSession();
		assertNotNull(session);
	}

	@Test
	public void testRetrieveWeatherStation() {
		String STATION_ID = "Sydney";
		Double expectedElevation=19.0;
		
		WeatherStationDo weatherStationDo = WeatherStationBo.retrieveWeatherStation(STATION_ID);
		
		LOGGER.info(weatherStationDo);

		assertNotNull(weatherStationDo);
		assertEquals(expectedElevation, weatherStationDo.getElevation());
		
	}

	@Test
	public void testRetrieveAllWeatherStations() {
		long expectedNoOfStations = 4;
		List<WeatherStationDo> weatherStationDoList = WeatherStationBo.retrieveAllWeatherStations();
		LOGGER.info(weatherStationDoList.size());

		weatherStationDoList.forEach(weatherStationDo -> {
			System.out.println(weatherStationDo);

		});
		
		assertNotNull(weatherStationDoList);
		assertEquals(expectedNoOfStations, weatherStationDoList.size());
		
	}
	
}
