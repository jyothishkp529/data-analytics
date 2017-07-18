/**
 * 
 */
package com.tcs.analytics.forecast.weather.business;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.junit.Test;

import com.tcs.analytics.forecast.weather.util.WFDateTimeUtil;

/**
 * Contains Date util test methods.
 * 
 * @author Jyothish Kalavoor Parambil
 * @version 1.0
 */
public class WFDateUtilTest {

	public static final Logger LOGGER = Logger.getLogger(WFDateUtilTest.class);

	@Test
	public void testConvertToDate() {
		String dateStr = "2017/07/17";

		DateTime dt = WFDateTimeUtil.convertToDate(dateStr);
		LOGGER.info("Converted Date : " + dt);
		assertNotNull(dt);
	}

	@Test
	public void testGetNextDay() {
		String dateStr = "2017/07/17";
		String expectedDate = "2017/07/18";

		String nextDay = WFDateTimeUtil.getNextDay(dateStr);
		LOGGER.info("NextDay : " + nextDay);
		assertTrue(expectedDate.equals(nextDay));
	}

}
