package com.tcs.analytics.forecast.weather.domain;

import java.io.Serializable;

/**
 * Contains the parameter value difference between two windows.
 * 
 * @author Jyothish Kalavoor Parambil
 * @version 1.0
 *
 */
public class EuclideanDistanceVo implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String windowId;
	private String date;
	private String location;
	private Double minTempDiff;
	private Double maxTempDiff;
	private Double temperature;
	private Double dewPointDiff;
	
	/**
	 * @return the windowId
	 */
	public String getWindowId() {
		return windowId;
	}
	/**
	 * @param windowId the windowId to set
	 */
	public void setWindowId(String windowId) {
		this.windowId = windowId;
	}
	/**
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}
	/**
	 * @param location the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}
	/**
	 * @return the date
	 */
	public String getDate() {
		return date;
	}
	/**
	 * @param date the date to set
	 */
	public void setDate(String date) {
		this.date = date;
	}
	/**
	 * @return the minTemp
	 */
	public Double getMinTempDiff() {
		return minTempDiff;
	}
	/**
	 * @param minTemp the minTemp to set
	 */
	public void setMinTempDiff(Double minTempDiff) {
		this.minTempDiff = minTempDiff;
	}
	/**
	 * @return the maxTemp
	 */
	public Double getMaxTempDiff() {
		return maxTempDiff;
	}
	/**
	 * @param maxTemp the maxTemp to set
	 */
	public void setMaxTempDiff(Double maxTempDiff) {
		this.maxTempDiff = maxTempDiff;
	}
	/**
	 * @return the minDewPoint
	 */
	public Double getTemperatureDiff() {
		return temperature;
	}
	/**
	 * @param minDewPoint the minDewPoint to set
	 */
	public void setTemperatureDiff(Double minDewPointDiff) {
		this.temperature = minDewPointDiff;
	}
	/**
	 * @return the maxDewPoint
	 */
	public Double getDewPointDiff() {
		return dewPointDiff;
	}
	/**
	 * @param maxDewPoint the maxDewPoint to set
	 */
	public void setDewPointDiff(Double maxDewPointDiff) {
		this.dewPointDiff = maxDewPointDiff;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "EuclideanDistanceVo [location=" + location + ", windowId=" + windowId + ", date=" + date
				+ ", minTempDiff=" + minTempDiff + ", maxTempDiff=" + maxTempDiff + ", tempDiff=" + temperature
				+ ", dewPointDiff=" + dewPointDiff + "]";
	}
	
}
