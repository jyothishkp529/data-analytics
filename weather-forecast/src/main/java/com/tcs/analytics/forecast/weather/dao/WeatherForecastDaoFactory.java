/**
 * 
 */
package com.tcs.analytics.forecast.weather.dao;

import org.apache.ibatis.session.SqlSession;

/**
 * The factory , which creates the Dao objects to retrieve data from
 * WeatherHistory and WeatherStation datasets.
 * 
 * @author Jyothish Kalavoor Parambil
 * @version 1.0
 */
public class WeatherForecastDaoFactory {

	private static SqlSession sqlSession = null;

	/**
	 * Opens a new session if no active session found.
	 */
	private static void init() {
		if (null == sqlSession) {
			sqlSession = MyBatisSessionFactory.getSession();
		}
	}

	/**
	 * 
	 * Returns the WeatherHistoryDao object.
	 */
	public static WeatherHistoryDao getWeatherHistoryDao() {
		init();
		return sqlSession.getMapper(WeatherHistoryDao.class);
	}

	/**
	 * 
	 * Returns the WeatherStationDao object.
	 */
	public static WeatherStationDao getWeatherStationDao() {
		init();
		return sqlSession.getMapper(WeatherStationDao.class);
	}

}
