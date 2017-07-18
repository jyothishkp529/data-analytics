/**
 * 
 */
package com.tcs.analytics.forecast.weather.util;

import java.util.Calendar;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import com.tcs.analytics.forecast.weather.constants.ApplicationConstants;

/**
 *
 * Contains Date conversion utility methods.
 * 
 * @author Jyothish Kalavoor Parambil
 * @version 1.0
 */
public class WFDateTimeUtil {

	public static final Logger LOGGER = Logger.getLogger(WFDateTimeUtil.class);

	/**
	 * Converts the DateString to @java.util.Date format.
	 * 
	 * @param dateStr
	 * @return
	 */
	public static DateTime convertToDate(String dateStr) {
		return DateTimeFormat.forPattern(ApplicationConstants.DATE_FORMAT).parseDateTime(dateStr);
	}

	/**
	 * Retrieves the next day in yyyy/MM/dd format.
	 * 
	 * @param currDate
	 * @return
	 */
	public static String getNextDay(String currDate) {
		return convertToString(convertToDate(currDate).plusDays(1));
	}

	/**
	 * Converts Date to String in yyyy/MM/dd format.
	 * 
	 * @param date
	 * @return
	 */
	public static String convertToString(DateTime dateTime) {
		return dateTime.toString(ApplicationConstants.DATE_FORMAT);

	}
	
	/**
	 * Generates a random number.
	 * 
	 * @param lower
	 *            the lower bound.
	 * @param upper
	 *            the upper bound.
	 * @return the random number.
	 */
	public static int getRandomNum(int lower, int upper) {
		return ThreadLocalRandom.current().nextInt(lower, upper + 1);
	}
	
	/**
	 * Returns the forecast weather reporting time.
	 * 
	 * @param preditionDate
	 *            the date on which the weather is calculated.
	 * @return forecastingDateTime
	 */
	public static String getForecastingDateTime(String preditionDate) {
		String forecastingDateTime = null;

		int hour = getRandomNum(13, 16);
		int minute = getRandomNum(01, 59);
		int second = getRandomNum(01, 59);
		Calendar pd = Calendar.getInstance();
		pd.setTime(WFDateTimeUtil.convertToDate(preditionDate).toDate());
		pd.set(Calendar.HOUR_OF_DAY, hour);
		pd.set(Calendar.MINUTE, minute);
		pd.set(Calendar.SECOND, second);

		forecastingDateTime = DateTimeFormat.forPattern(ApplicationConstants.REPORTING_DATE_FORMAT)
				.print(new DateTime(pd.getTime()));

		return forecastingDateTime;
	}
	
	
}
