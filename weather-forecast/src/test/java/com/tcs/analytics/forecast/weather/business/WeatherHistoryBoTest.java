package com.tcs.analytics.forecast.weather.business;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import com.tcs.analytics.forecast.weather.dao.WeatherHistoryDao;
import com.tcs.analytics.forecast.weather.business.exception.WeatherHistoryBusinessExcption;
import com.tcs.analytics.forecast.weather.dao.MyBatisSessionFactory;
import com.tcs.analytics.forecast.weather.domain.WeatherHistoryDo;
import com.tcs.analytics.forecast.weather.helper.ReportingHelper;

/**
 * Unit test class for the WeatherHistoryBo.
 * 
 * @author Jyothish Kalavoor Parambil
 * @version 1.0
 */
public class WeatherHistoryBoTest {
	public static final Logger LOGGER = Logger.getLogger(WeatherHistoryBoTest.class);

	private static String STATION_ID = null;
	List<WeatherHistoryDo> weatherHistoryDoList = null;

	@Before
	public void init() {
		STATION_ID = "Sydney";
		try {
			weatherHistoryDoList = WeatherHistoryBo.retrieveLocationBasedHistoricalWeather(STATION_ID);
		} catch (WeatherHistoryBusinessExcption e) {
			LOGGER.error(e.getErrorMessage());
		}
	}

	@Test
	public void testPeriodBasedHistoricalWeather()  {
		SqlSession session = MyBatisSessionFactory.getSession();
		WeatherHistoryDao weatherHistoryDao = session.getMapper(WeatherHistoryDao.class);
		String stationId = "Sydney";
		String startDate = "2015/09/11";
		String endDate = "2015/09/11";
		List<WeatherHistoryDo> weatherHistoryDoList = weatherHistoryDao.getHistoryWeatherData(stationId, startDate,
				endDate);
		assertNotNull(weatherHistoryDoList);
	}

	@Test
	public void testGetRecentSevenDaysWeatherData() {

		List<WeatherHistoryDo> sevenDaysWeatherHistoryDoList = null;
		sevenDaysWeatherHistoryDoList = WeatherHistoryBo.getRecentSevenDaysWeatherData(weatherHistoryDoList);
		LOGGER.info(sevenDaysWeatherHistoryDoList.size());

		ReportingHelper.printWeatherData(sevenDaysWeatherHistoryDoList);
		assertNotNull(sevenDaysWeatherHistoryDoList);
		assertEquals(7, sevenDaysWeatherHistoryDoList.size());
	}

	@Test
	public void testGetPreviousYearWeatherData() {

		String PREDICTION_DATE = "2016/01/02";
		Integer windowDuration = 14;
		long expectedListSize = 14;

		List<WeatherHistoryDo> previousYearWeatherDataList = WeatherHistoryBo
				.getPreviousYearWeatherData(weatherHistoryDoList, PREDICTION_DATE, windowDuration);
		LOGGER.info(previousYearWeatherDataList.size());
		ReportingHelper.printWeatherData(previousYearWeatherDataList);

		assertNotNull(previousYearWeatherDataList);
		assertEquals(expectedListSize, previousYearWeatherDataList.size());
	}

}
