package com.tcs.analytics.forecast.weather.business;

import static org.junit.Assert.assertEquals;

import org.apache.log4j.Logger;
import org.junit.Test;

/**
 * Unit test class for WeatherForecastBo.
 * 
 * @author Jyothish Kalavoor Parambil
 * @version 1.0
 */
public class WeatherForecastBoTest {
	public static final Logger LOGGER = Logger.getLogger(WeatherForecastBoTest.class);

	@Test
	public void testCalculateDewPoint() {
		Double temp = 9.6;
		Long rh = 80L;
		Double expectedDewPoint = 5.6;

		Double actualDewPoint = WeatherForecastBo.calculateDewPoint(temp, rh);
		LOGGER.info("dewPoint : " + actualDewPoint);

		assertEquals(expectedDewPoint, actualDewPoint);
	}

	@Test
	public void testCalculateRelativeHumidity() {
		double temp = -6;
		double dp = -3;
		Double expectedRH = 19.0;

		Double actualRH = WeatherForecastBo.calculateRelativeHumidity(temp, dp);
		LOGGER.info("RelativeHumidity : " + actualRH);

		assertEquals(expectedRH, expectedRH);

	}

}
