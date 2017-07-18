package com.tcs.analytics.forecast.weather.domain;

import java.io.Serializable;

/**
 * @author Jyothish Kalavoor Parambil
 * @version 1.0
 *
 */
public class WeatherHistoryDo implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String location;
	private Double minTemp;
	private Double maxTemp;
	private String event;
	private Double temp;
	private Double dewPoint;
	private String date;
	
	public WeatherHistoryDo() {
	}


	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}


	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public Double getMinTemp() {
		return minTemp;
	}

	public void setMinTemp(Double minTemp) {
		this.minTemp = minTemp;
	}

	public Double getMaxTemp() {
		return maxTemp;
	}

	public void setMaxTemp(Double maxTemp) {
		this.maxTemp = maxTemp;
	}

	public Double getTemp() {
		return temp;
	}

	public void setTemp(Double minDewPoint) {
		this.temp = minDewPoint;
	}

	public Double getDewPoint() {
		return dewPoint;
	}

	public void setDewPoint(Double dewPoint) {
		this.dewPoint = dewPoint;
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

	public String toString() {
		return "WeatherHistoryDo [location=" + location + ", date=" + date + ", minTemp=" + minTemp + ", maxTemp="
				+ maxTemp + ", temp=" + temp + ", dewPoint=" + dewPoint + ", event=" + event + "]";
	}

}
