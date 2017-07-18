package com.tcs.analytics.forecast.weather.constants;

/**
 * ApplicationConstants used for various various weather calculations.
 * 
 * @author Jyothish Kalavoor Parambil
 * @version 1.0
 *
 */
public class ApplicationConstants {

	public static final String DATE_FORMAT = "yyyy/MM/dd";
	public static final String REPORTING_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'";
	
	public static final double HPA_CONVERTION_FACTOR = 1013.25;
	public static final Double KELVIN_SCALE = 273.15;
	
	
	public static final int DEFAULT_PREDICTION_DAYS = 1;
	public static final int SLIDING_WINDOW_SIZE = 7;
	public static final int SWA_PREV_YEAR_WINDOW_DURATION = 14;
}
