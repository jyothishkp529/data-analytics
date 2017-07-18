/**
 * 
 */
package com.tcs.analytics.forecast.weather.domain;

import java.io.Serializable;

import com.tcs.analytics.forecast.weather.enums.WeatherConditionsEnum;

/**
 * The weather condition of a station.
 * 
 * @author Jyothish Kalavoor Parambil
 * @version 1.0
 */
public class WeatherForecastDo implements Serializable {

	private static final long serialVersionUID = 1L;

	private String stationCode;
	private Double latitude;
	private Double longitude;
	private String time;
	private WeatherConditionsEnum weatherCondition;
	private Double temperature;
	private Double pressure;
	private Double humidity;

	/**
	 * @return the stationCode
	 */
	public String getStationCode() {
		return stationCode;
	}

	/**
	 * @param stationCode
	 *            the stationCode to set
	 */
	public void setStationCode(String stationCode) {
		this.stationCode = stationCode;
	}

	/**
	 * @return the latitude
	 */
	public Double getLatitude() {
		return latitude;
	}

	/**
	 * @param latitude
	 *            the latitude to set
	 */
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	/**
	 * @return the longitude
	 */
	public Double getLongitude() {
		return longitude;
	}

	/**
	 * @param longitude
	 *            the longitude to set
	 */
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	/**
	 * @return the time
	 */
	public String getTime() {
		return time;
	}

	/**
	 * @param time
	 *            the time to set
	 */
	public void setTime(String time) {
		this.time = time;
	}

	/**
	 * @return the weatherCondition
	 */
	public WeatherConditionsEnum getWeatherCondition() {
		return weatherCondition;
	}

	/**
	 * @param weatherCondition
	 *            the weatherCondition to set
	 */
	public void setWeatherCondition(WeatherConditionsEnum weatherCondition) {
		this.weatherCondition = weatherCondition;
	}

	/**
	 * @return the temperature
	 */
	public Double getTemperature() {
		return temperature;
	}

	/**
	 * @param temperature
	 *            the temperature to set
	 */
	public void setTemperature(Double temperature) {
		this.temperature = temperature;
	}

	/**
	 * @return the pressure
	 */
	public Double getPressure() {
		return pressure;
	}

	/**
	 * @param pressure
	 *            the pressure to set
	 */
	public void setPressure(Double pressure) {
		this.pressure = pressure;
	}

	/**
	 * @return the humidity
	 */
	public Double getHumidity() {
		return humidity;
	}

	/**
	 * @param humidity
	 *            the humidity to set
	 */
	public void setHumidity(Double humidity) {
		this.humidity = humidity;
	}

	@Override
	public String toString() {
		return "WeatherForecastDo [stationCode=" + stationCode + ", latitude=" + latitude + ", longitude=" + longitude
				+ ", time=" + time + ", weatherCondition=" + weatherCondition + ", temperature=" + temperature + ", pressure="
				+ pressure + ", humidity=" + humidity + "]";
	}

}
