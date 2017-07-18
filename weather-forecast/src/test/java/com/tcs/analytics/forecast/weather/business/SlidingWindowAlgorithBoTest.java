package com.tcs.analytics.forecast.weather.business;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import com.tcs.analytics.forecast.weather.business.exception.WeatherHistoryBusinessExcption;
import com.tcs.analytics.forecast.weather.domain.EuclideanDistanceVo;
import com.tcs.analytics.forecast.weather.domain.WeatherHistoryDo;

/**
 * Unit test class for the @SlidingWindowAlgorithBo.
 * 
 * @author Jyothish Kalavoor Parambil
 * @version 1.0
 */
public class SlidingWindowAlgorithBoTest {
	public static final Logger LOGGER = Logger.getLogger(SlidingWindowAlgorithBoTest.class);

	private String STATION_ID = null;
	private List<WeatherHistoryDo> weatherHistoryDoList = null;

	@Before
	public void init() {
		STATION_ID = "Sydney";
		try {
			weatherHistoryDoList = WeatherHistoryBo.retrieveLocationBasedHistoricalWeather(STATION_ID);
		} catch (WeatherHistoryBusinessExcption e) {
			LOGGER.error(e.getErrorMessage());
		}
	}
	
	@Test
	public void testCreateSlidingWindows() {

		String PREDICTION_DATE = "2016/01/01";
		Integer WINDOW_DURATION = 14;
		Integer WINDOW_SIZE = 7;
		Double exptectedMinTemp = 17.0;
		long exptectedWindowSize = 8;

		Map<String, List<WeatherHistoryDo>> slidingWindowMap = null;

		List<WeatherHistoryDo> previousYearWeatherDataList = WeatherHistoryBo
				.getPreviousYearWeatherData(weatherHistoryDoList, PREDICTION_DATE, WINDOW_DURATION);

		slidingWindowMap = SlidingWindowAlgorithmBo.createSlidingWindows(previousYearWeatherDataList, WINDOW_SIZE);

		/*
		LOGGER.debug(slidingWindowMap.size());
		Set<String> slidingWindowskeySet = slidingWindowMap.keySet();
		for (String k : slidingWindowskeySet) {
			LOGGER.debug("Window: " + k);
			ReportingHelper.printWeatherData(slidingWindowMap.get(k));
		}
		*/

		assertNotNull(slidingWindowMap);
		assertEquals(exptectedWindowSize, slidingWindowMap.size());
		assertEquals(exptectedMinTemp, slidingWindowMap.get("W2").get(0).getMinTemp());

	}
	
	@Test
	public void testFindEuclideanDistance() {

		String PREDICTION_DATE = "2016/01/01";
		Integer WINDOW_DURATION = 14;
		Integer WINDOW_SIZE = 7;
		String WINDOW_ID = "W1";

		Double exptectedMinTempDiff = 2.0;

		List<EuclideanDistanceVo> slidingWindowEuclideanDistanceVoList = null;

		List<WeatherHistoryDo> currenrYearRecentSevenDaysDataList = WeatherHistoryBo
				.getRecentSevenDaysWeatherData(weatherHistoryDoList);

		List<WeatherHistoryDo> previousYearWeatherDataList = WeatherHistoryBo
				.getPreviousYearWeatherData(weatherHistoryDoList, PREDICTION_DATE, WINDOW_DURATION);

		Map<String, List<WeatherHistoryDo>> slidingWindowMap = SlidingWindowAlgorithmBo
				.createSlidingWindows(previousYearWeatherDataList, WINDOW_SIZE);

		slidingWindowEuclideanDistanceVoList = SlidingWindowAlgorithmBo
				.findEuclideanDistance(currenrYearRecentSevenDaysDataList, slidingWindowMap.get(WINDOW_ID), WINDOW_ID);

		/*
		LOGGER.debug("PreviousYear Date");
		ReportingHelper.printWeatherData(slidingWindowMap.get(WINDOW_ID));

		LOGGER.debug("Current Date");
		ReportingHelper.printWeatherData(currenrYearRecentSevenDaysDataList);
		
		LOGGER.debug(slidingWindowEuclideanDistanceVoList.size());			
		LOGGER.debug("EuclideanDistance");
		ReportingHelper.printEuclideanDistance(slidingWindowEuclideanDistanceVoList);

		*/
		assertNotNull(slidingWindowEuclideanDistanceVoList);
		assertEquals(exptectedMinTempDiff, slidingWindowEuclideanDistanceVoList.get(0).getMinTempDiff());
	}
	
	
	@Test
	public void testMinEuclideanDistance() {

		String PREDICTION_DATE = "2016/01/01";
		Integer WINDOW_DURATION = 14;
		Integer WINDOW_SIZE = 7;
		String WINDOW_ID_01 = "W3";
		String WINDOW_ID_02 = "W4";

		String expectedWindow = "W3";
		List<EuclideanDistanceVo> bestMatchedWindow = null;

		List<WeatherHistoryDo> currenrYearRecentSevenDaysDataList = WeatherHistoryBo
				.getRecentSevenDaysWeatherData(weatherHistoryDoList);
		List<WeatherHistoryDo> previousYearWeatherDataList = WeatherHistoryBo
				.getPreviousYearWeatherData(weatherHistoryDoList, PREDICTION_DATE, WINDOW_DURATION);

		Map<String, List<WeatherHistoryDo>> slidingWindowMap = SlidingWindowAlgorithmBo
				.createSlidingWindows(previousYearWeatherDataList, WINDOW_SIZE);

		List<EuclideanDistanceVo> slidingWindowEuclideanDistanceVoList = SlidingWindowAlgorithmBo.findEuclideanDistance(
				currenrYearRecentSevenDaysDataList, slidingWindowMap.get(WINDOW_ID_01), WINDOW_ID_01);
		List<EuclideanDistanceVo> slidingWindowEuclideanDistanceVoListW2 = SlidingWindowAlgorithmBo
				.findEuclideanDistance(currenrYearRecentSevenDaysDataList, slidingWindowMap.get(WINDOW_ID_02),
						WINDOW_ID_02);

		/*
		LOGGER.info("SlidingWindow_01_Diff :: " + WINDOW_ID_01);
		slidingWindowEuclideanDistanceVoList.forEach(k -> {
			System.out.println(k);
		});

		LOGGER.info("SlidingWindow_02-Diff :: " + WINDOW_ID_02);
		slidingWindowEuclideanDistanceVoListW2.forEach(k -> {
			System.out.println(k);
		});
		LOGGER.info("Min (01 vs 02)");
		 */
		
		bestMatchedWindow = SlidingWindowAlgorithmBo.minEuclideanDistance(slidingWindowEuclideanDistanceVoListW2,
				slidingWindowEuclideanDistanceVoList);
		LOGGER.info("bestMatchedWindow : " + bestMatchedWindow.get(0).getWindowId());
		
		assertTrue(expectedWindow.equalsIgnoreCase(bestMatchedWindow.get(0).getWindowId()));
	}
	
	@Test
	public void testFindBestMatchedSlidingWindow() {

		String PREDICTION_DATE = "2016/01/01";
		Integer WINDOW_DURATION = 14;
		Integer WINDOW_SIZE = 7;

		String expectedWindow = "W3";
		String bestMatchedWindowId = null;
		List<WeatherHistoryDo> bestMatchedWindow = null;


			List<WeatherHistoryDo> currenrYearRecentSevenDaysDataList = WeatherHistoryBo
					.getRecentSevenDaysWeatherData(weatherHistoryDoList);
			List<WeatherHistoryDo> previousYearWeatherDataList = WeatherHistoryBo
					.getPreviousYearWeatherData(weatherHistoryDoList, PREDICTION_DATE, WINDOW_DURATION);

			Map<String, List<WeatherHistoryDo>> slidingWindowMap = SlidingWindowAlgorithmBo
					.createSlidingWindows(previousYearWeatherDataList, WINDOW_SIZE);

			bestMatchedWindowId = SlidingWindowAlgorithmBo
					.findBestMatchedSlidingWindow(currenrYearRecentSevenDaysDataList, slidingWindowMap);
			bestMatchedWindow = slidingWindowMap.get(bestMatchedWindowId);

			/*
			LOGGER.info("Best Match :: " + bestMatchedWindowId);
			bestMatchedWindow.forEach(k -> {
				System.out.println(k);
			});
			*/
			
		assertNotNull(bestMatchedWindow);
		assertTrue(expectedWindow.equalsIgnoreCase(bestMatchedWindowId));
	}
	
	
	@Test
	public void testVariationFactorCalculation() {

		String PREDICTION_DATE = "2016/01/01";
		Integer WINDOW_DURATION = 14;
		Integer WINDOW_SIZE = 7;

		Long expectedMinTempVariationFactor = 2L;
		Long actualMinTempVariationFactor = null;
		

		List<WeatherHistoryDo> currenrYearRecentSevenDaysDataList = WeatherHistoryBo
				.getRecentSevenDaysWeatherData(weatherHistoryDoList);
		List<WeatherHistoryDo> previousYearWeatherDataList = WeatherHistoryBo
				.getPreviousYearWeatherData(weatherHistoryDoList, PREDICTION_DATE, WINDOW_DURATION);

		Map<String, List<WeatherHistoryDo>> slidingWindowMap = SlidingWindowAlgorithmBo
				.createSlidingWindows(previousYearWeatherDataList, WINDOW_SIZE);

		String bestMatchedWindowId = SlidingWindowAlgorithmBo
				.findBestMatchedSlidingWindow(currenrYearRecentSevenDaysDataList, slidingWindowMap);
		List<WeatherHistoryDo> bestMatchedWindow = slidingWindowMap.get(bestMatchedWindowId);

		actualMinTempVariationFactor = SlidingWindowAlgorithmBo
				.computeMinTempVariationFactor(currenrYearRecentSevenDaysDataList, bestMatchedWindow);
		LOGGER.debug("minTempVariationFactor :" + actualMinTempVariationFactor);

		Long actualMaxTempVariationFactor = SlidingWindowAlgorithmBo
				.computeMaxTempVariationFactor(currenrYearRecentSevenDaysDataList, bestMatchedWindow);
		LOGGER.debug("maxTempVariationFactor :" + actualMaxTempVariationFactor);

		Long actualTemparatureVariationFactor = SlidingWindowAlgorithmBo
				.computeTemparatureVariationFactor(currenrYearRecentSevenDaysDataList, bestMatchedWindow);
		LOGGER.debug("temparatureVariationFactor :" + actualTemparatureVariationFactor);

		Long actualDewPointVariationFactor = SlidingWindowAlgorithmBo.computeDewPointVariationFactor(currenrYearRecentSevenDaysDataList,
				bestMatchedWindow);
		LOGGER.debug("dewPoint :" + actualDewPointVariationFactor);

		assertEquals(expectedMinTempVariationFactor, actualMinTempVariationFactor);
	}
	
	
	
}
