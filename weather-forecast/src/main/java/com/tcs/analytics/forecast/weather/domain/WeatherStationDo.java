package com.tcs.analytics.forecast.weather.domain;

import java.io.Serializable;

/**
 * WeatherStationDo entity
 * 
 * @author Jyothish Kalavoor Parambil
 * @version 1.0
 *
 */
public class WeatherStationDo implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String stationCode;
	private String name;
	private Double latitude;
	private Double longitude;
	private Double elevation;

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
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
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
	 * @return the elevation
	 */
	public Double getElevation() {
		return elevation;
	}

	/**
	 * @param elevation
	 *            the elevation to set
	 */
	public void setElevation(Double elevation) {
		this.elevation = elevation;
	}

	@Override
	public String toString() {
		return "WeatherStationDo [name=" + name + ", stationCode=" + stationCode + ", latitude=" + latitude
				+ ", longitude=" + longitude + ", elevation=" + elevation + "]";
	}

}
