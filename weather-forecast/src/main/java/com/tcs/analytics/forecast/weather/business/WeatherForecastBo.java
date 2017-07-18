package com.tcs.analytics.forecast.weather.business;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.apache.log4j.Logger;

import com.tcs.analytics.forecast.weather.constants.ApplicationConstants;
import com.tcs.analytics.forecast.weather.enums.WeatherConditionsEnum;

/**
 * Contains methods which calculates the weather parameters once the basic
 * features are forecasted.
 * 
 * @author Jyothish Kalavoor Parambil
 * @version 1.0
 */
public class WeatherForecastBo {
	public static final Logger LOGGER = Logger.getLogger(WeatherForecastBo.class);

	/**
	 * The pressure is calculated by using Hypsometric formula. Ref:
	 * http://keisan.casio.com/exec/system/1224579725
	 * 
	 * @param temperature
	 *            the current temperature at the station.
	 * @param elevation
	 *            the elevation of the station.
	 * @return the pressure in Hpa unit.
	 */
	public static Double calculatePressure(Double temperature, Double elevation) {
		LOGGER.debug("Enter calculatePressure.");
		double heightFactor = (0.0065 * elevation);
		double denominator = temperature + heightFactor + ApplicationConstants.KELVIN_SCALE;

		double pressureInAtm = 1 - (heightFactor / denominator);
		double pressureInHpa = ApplicationConstants.HPA_CONVERTION_FACTOR * pressureInAtm;
		
		pressureInHpa = BigDecimal.valueOf(pressureInHpa).setScale(1, RoundingMode.HALF_UP).doubleValue();
		
		LOGGER.debug("Exit calculatePressure.");
		return pressureInHpa;
	}

	/**
	 * 
	 * The relative Humidity(RH) is calculated with the formula. RH = 100 - 5(T
	 * - Tdp) Ref: https://en.wikipedia.org/wiki/Dew_point
	 * 
	 * @param temp
	 *            the current temperature at the station.
	 * @param dewPoint
	 *            the dewPoint temperature at the station at a particular hour
	 *            of estimation.
	 * @return the Relative humidity in percentage.
	 */
	public static Double calculateRelativeHumidity(Double temp, Double dewPoint) {

		LOGGER.debug("Enter calculateRelativeHumidity.");

		
		Double rh = Math.abs(100 - (5 * (temp - dewPoint)));
		Double relativeHumidity = BigDecimal.valueOf(rh).setScale(1, RoundingMode.HALF_UP).doubleValue();

		LOGGER.debug("Exit calculateRelativeHumidity.");
		return relativeHumidity;

	}

	/**
	 * 
	 * The DewPoint(DP) is calculated with the formula. DP = T - ((100 - RH)/5)
	 * Ref: https://en.wikipedia.org/wiki/Dew_point
	 * 
	 * @param temp
	 *            the current temperature at the station.
	 * @param rh
	 *            Relative humidity in percentage.
	 * @return the dewPoint temperature at the station at a particular hour.
	 */
	public static Double calculateDewPoint(Double temp, Long rh) {

		LOGGER.debug("Enter calculateRelativeHumidity.");

		Double dp = temp - ((100 - rh) / 5);
		Double dewPoint = BigDecimal.valueOf(dp).setScale(1, RoundingMode.HALF_UP).doubleValue();
		LOGGER.debug("Exit calculateRelativeHumidity.");
		return dewPoint;

	}

	/**
	 * To predict event based on given parameters
	 * 
	 * @param temperature
	 * @param relativeHumidity
	 * @return
	 */
	public static WeatherConditionsEnum predictEvent(Double temperature, Double dewPoint, Double relativeHumidity) {
		LOGGER.debug("Enter predictEvent.");

		WeatherConditionsEnum weatherConditionsEnum = null;

		if (relativeHumidity > 75) {
			if (temperature <= 5) {
				weatherConditionsEnum = WeatherConditionsEnum.SNOW;
			} else if (temperature > 5 && temperature <= 25) {
				weatherConditionsEnum = WeatherConditionsEnum.RAIN;
			} else if (temperature > 25) {
				weatherConditionsEnum = WeatherConditionsEnum.FOG;
			}

		} else if (relativeHumidity >= 60 && relativeHumidity <= 75) {
			weatherConditionsEnum = WeatherConditionsEnum.CLOUDY;
		} else {
			weatherConditionsEnum = WeatherConditionsEnum.SUNNY;
		}
		LOGGER.debug("Exit predictEvent.");
		return weatherConditionsEnum;
	}

}
