package com.tcs.analytics.forecast.weather.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.tcs.analytics.forecast.weather.business.SlidingWindowAlgorithmBo;
import com.tcs.analytics.forecast.weather.business.WeatherForecastBo;
import com.tcs.analytics.forecast.weather.business.WeatherHistoryBo;
import com.tcs.analytics.forecast.weather.business.WeatherStationBo;
import com.tcs.analytics.forecast.weather.business.exception.WeatherHistoryBusinessExcption;
import com.tcs.analytics.forecast.weather.constants.ApplicationConstants;
import com.tcs.analytics.forecast.weather.domain.WeatherForecastDo;
import com.tcs.analytics.forecast.weather.domain.WeatherHistoryDo;
import com.tcs.analytics.forecast.weather.domain.WeatherStationDo;
import com.tcs.analytics.forecast.weather.enums.WeatherConditionsEnum;
import com.tcs.analytics.forecast.weather.helper.ReportingHelper;
import com.tcs.analytics.forecast.weather.util.ExceptionHandler;
import com.tcs.analytics.forecast.weather.util.WFDateTimeUtil;

/**
 * The forecasting services to predict weather. The prediction is based on
 * Sliding Window Algorithm.
 * 
 * @author Jyothish Kalavoor Parambil
 * @version 1.0
 *
 */
public class HistoricalDataBasedWeatherForecastingService implements WeatherForecastingService {

	public static final Logger LOGGER = Logger.getLogger(HistoricalDataBasedWeatherForecastingService.class);

	/**
	 * Forecast the weather for the number of days of all the stations.
	 * 
	 * @param days
	 *            the number of days from the current date.
	 */
	public void forecast(Integer days) {
		LOGGER.debug("Enter forecast.");

		List<WeatherForecastDo> allStationWeatherForecastDoList = new ArrayList<WeatherForecastDo>();

		LOGGER.info("Weather Data Generation started");
		List<WeatherStationDo> weatherStationDoList = WeatherStationBo.retrieveAllWeatherStations();
		LOGGER.info("No Of Stations : "+weatherStationDoList.size()+" , No of Days : "+days);
		LOGGER.info("Forecasting started...");
		int indx = 1;
		
		for (WeatherStationDo ws : weatherStationDoList) {
			LOGGER.info("  " + indx++ +"] "+ ws.getName());

			List<WeatherForecastDo> weatherForecastDoList = forecast(ws.getName(), days);
			if (null != weatherForecastDoList) {
				allStationWeatherForecastDoList.addAll(weatherForecastDoList);
			}
		}
			

		LOGGER.info("Weather data Generation has been completed.");

		if (!allStationWeatherForecastDoList.isEmpty()) {
			LOGGER.info("Preparing weather Report.");
			ReportingHelper.printWeatherForecastingData(allStationWeatherForecastDoList);
		} else {
			LOGGER.error("Unable to generate weather data. Please check the history dataset.");
		}

		LOGGER.debug("Exit forecast.");
	}

	/**
	 * 
	 * Forecast the weather for the number of days of a single station.
	 * 
	 * @param stationCode
	 *            the weather station code.
	 * 
	 * @param days
	 *            the number of days from the current date.
	 * 
	 * 
	 * @return the list of weather forecast of each day from the current date to
	 *         the number of days.
	 */
	public List<WeatherForecastDo> forecast(String stationCode, Integer days) {
		LOGGER.debug("Enter forecast.");
		List<WeatherForecastDo> weatherForecastDoList = null;

		WeatherStationDo weatherStationDo = WeatherStationBo.retrieveWeatherStation(stationCode);

		weatherForecastDoList = forecast(weatherStationDo, days);
		LOGGER.debug("Exit forecast.");
		return weatherForecastDoList;
	}

	/**
	 * 
	 * Forecast the weather for the number of days of a single station.
	 * 
	 * @param weatherStationDo
	 *            the domain objects contains weather station details .
	 * 
	 * @param days
	 *            the number of days from the current date.
	 * 
	 * @return the list of weather forecast of each day from the current date to
	 *         the number of days.
	 */
	private List<WeatherForecastDo> forecast(WeatherStationDo weatherStationDo, Integer days) {
		LOGGER.debug("Enter forecast.");

		WeatherForecastDo weatherForecastDo = null;

		List<WeatherForecastDo> weatherForecastDoList = null;
		List<WeatherHistoryDo> stationWeatherHistoryDoList = null;

		try {
			stationWeatherHistoryDoList = WeatherHistoryBo
					.retrieveLocationBasedHistoricalWeather(weatherStationDo.getName());

			weatherForecastDoList = new ArrayList<WeatherForecastDo>();
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug(new StringBuilder().append("Data Generation Started. [WeatherStation : ")
						.append(weatherStationDo.getStationCode()).append(" ,No ofDays: ").append(days).append("]")
						.toString());
			}
			for (int i = 0; i < days; i++) {

				// Step1:: Retrieve most recently available7 days weather history data.
				List<WeatherHistoryDo> recentSevenDaysWeatherHistoryDoList = WeatherHistoryBo
						.getRecentSevenDaysWeatherData(stationWeatherHistoryDoList);

				WeatherHistoryDo mostRecentWeatherHistoryDo = recentSevenDaysWeatherHistoryDoList.get(6);
				String latestAvailableDate = mostRecentWeatherHistoryDo.getDate();

				String preditionDate = WFDateTimeUtil.getNextDay(latestAvailableDate);
				LOGGER.debug("Next Pediction Date : " + preditionDate);

				// Step2:: Retrieve weather history data from the previous year.
				Integer windowDuration = ApplicationConstants.SWA_PREV_YEAR_WINDOW_DURATION;
				List<WeatherHistoryDo> previousYearWeatherDataList = WeatherHistoryBo
						.getPreviousYearWeatherData(stationWeatherHistoryDoList, preditionDate, windowDuration);

				// Step3:: Create sliding windows with each window contains 7 days of weather data.
				Map<String, List<WeatherHistoryDo>> slidingWindowMap = SlidingWindowAlgorithmBo
						.createSlidingWindows(previousYearWeatherDataList, ApplicationConstants.SLIDING_WINDOW_SIZE);

				// Step4:: Identify the window of previous year which is close to the current week data.
				String bestMatchedWindowId = SlidingWindowAlgorithmBo
						.findBestMatchedSlidingWindow(recentSevenDaysWeatherHistoryDoList, slidingWindowMap);
				List<WeatherHistoryDo> bestMatchedWindow = slidingWindowMap.get(bestMatchedWindowId);

				// Step5 :: Calculate VariationFactors of each of the Features.
				Long minTempVariationFactor = SlidingWindowAlgorithmBo
						.computeMinTempVariationFactor(recentSevenDaysWeatherHistoryDoList, bestMatchedWindow);
				Long maxTempVariationFactor = SlidingWindowAlgorithmBo
						.computeMaxTempVariationFactor(recentSevenDaysWeatherHistoryDoList, bestMatchedWindow);
				Long tempFactor = SlidingWindowAlgorithmBo
						.computeTemparatureVariationFactor(recentSevenDaysWeatherHistoryDoList, bestMatchedWindow);
				Long dewPointFactor = SlidingWindowAlgorithmBo
						.computeDewPointVariationFactor(recentSevenDaysWeatherHistoryDoList, bestMatchedWindow);

				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug(new StringBuilder().append("MinTempVariationFactor :")
							.append("minTempVariationFactor :").append("MaxTempVariationFactor :")
							.append(maxTempVariationFactor).append("TempFactor :").append(tempFactor)
							.append("DewPointFactor : ").append(dewPointFactor).toString());
				}

				// Step6 :: Predict (or Calculate) NextDay weather features.
				double newMinTemp = mostRecentWeatherHistoryDo.getMinTemp().doubleValue() + minTempVariationFactor;
				double newMaxTemp = mostRecentWeatherHistoryDo.getMaxTemp() + maxTempVariationFactor;

				double newTemp = mostRecentWeatherHistoryDo.getTemp() + tempFactor;
				double newDewPoint = mostRecentWeatherHistoryDo.getDewPoint() + dewPointFactor;

				
				// Step7 :: Calculate RelativeHumity(RH), Pressure, Weather condition
				// Calculate RH
				double relativeHumidity = WeatherForecastBo.calculateRelativeHumidity(newTemp, newDewPoint);

				// Calculate Pressure
				double pressure = WeatherForecastBo.calculatePressure(newTemp, weatherStationDo.getElevation());

				// Predict Weather condition
				WeatherConditionsEnum weatherConditionEnum = WeatherForecastBo.predictEvent(new Double(newTemp),
						new Double(newDewPoint), new Double(pressure));

				// Step8 ::Assemble weatherForecastDo.
				weatherForecastDo = new WeatherForecastDo();
				weatherForecastDo.setStationCode(weatherStationDo.getStationCode());
				weatherForecastDo.setLatitude(weatherStationDo.getLatitude());
				weatherForecastDo.setLongitude(weatherStationDo.getLongitude());
				
				weatherForecastDo.setTime(WFDateTimeUtil.getForecastingDateTime(preditionDate));
				weatherForecastDo.setWeatherCondition(weatherConditionEnum);
				weatherForecastDo.setTemperature(newTemp);
				weatherForecastDo.setHumidity(relativeHumidity);
				weatherForecastDo.setPressure(pressure);

				// Step9 ::Create weather history for next iteration
				WeatherHistoryDo foreCastedWeatherData = new WeatherHistoryDo();

				foreCastedWeatherData.setDate(preditionDate);
				foreCastedWeatherData.setLocation(mostRecentWeatherHistoryDo.getLocation());
				foreCastedWeatherData.setMinTemp(newMinTemp);
				foreCastedWeatherData.setMaxTemp(newMaxTemp);
				foreCastedWeatherData.setTemp(newTemp);
				foreCastedWeatherData.setDewPoint(newDewPoint);

				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug(foreCastedWeatherData);

				}

				weatherForecastDoList.add(weatherForecastDo);

				// Add the recently forecasted data to the weather history list
				// for further prediction.
				stationWeatherHistoryDoList.add(foreCastedWeatherData);

			}

		} catch (WeatherHistoryBusinessExcption e) {
			ExceptionHandler.handleException(e);
		}

		LOGGER.debug("Exit forecast.");
		return weatherForecastDoList;
	}
}
