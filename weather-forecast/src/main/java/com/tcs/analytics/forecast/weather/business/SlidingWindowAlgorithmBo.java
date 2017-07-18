package com.tcs.analytics.forecast.weather.business;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.log4j.Logger;

import com.tcs.analytics.forecast.weather.domain.EuclideanDistanceVo;
import com.tcs.analytics.forecast.weather.domain.WeatherHistoryDo;

/**
 * Contains methods which implements the varies steps in Sliding Window
 * Algorithm.
 * 
 * @author Jyothish Kalavoor Parambil
 * @version 1.0
 */
public class SlidingWindowAlgorithmBo {
	static final Logger LOGGER = Logger.getLogger(SlidingWindowAlgorithmBo.class);


	/**
	 * Create the weather data into a list of eight slots (Window). Each window
	 * contains seven days of weather data.
	 * 
	 * @param previousYearWeatherDataList
	 * @param windowSize
	 *            duration of each window.
	 * @return slidingWindowMap contains the map of eight weekly weather
	 *         datasets.
	 */
	public static Map<String, List<WeatherHistoryDo>> createSlidingWindows(
			List<WeatherHistoryDo> previousYearWeatherDataList, Integer windowSize) {
		LOGGER.debug("Enter createSlidingWindows.");

		List<ArrayList<WeatherHistoryDo>> slidingWindowsList = new ArrayList<ArrayList<WeatherHistoryDo>>();
		Map<String, List<WeatherHistoryDo>> slidingWindowMap = new HashMap<String, List<WeatherHistoryDo>>();
		Integer windowDuration = previousYearWeatherDataList.size();
		Integer noOfWindows = (windowDuration - windowSize) + 1;

		for (int i = 0; i < noOfWindows; i++) {
			ArrayList<WeatherHistoryDo> slidingWindows = new ArrayList<WeatherHistoryDo>();
			for (int j = i; j < (i + windowSize); j++) {
				slidingWindows.add(previousYearWeatherDataList.get(j));
			}

			slidingWindowMap.put("W" + (i + 1), slidingWindows);

			slidingWindowsList.add(slidingWindows);
		}

		LOGGER.debug("Exit createSlidingWindows.");

		return slidingWindowMap;

	}

	/**
	 * Returns the window which closely matches the current weekly data.
	 * 
	 * @param currentYearSevenDaysWeatherDataList
	 * @param slidingWindowMap
	 * @return bestMatchedWindowId the ID of the best sliding window.
	 */
	public static String findBestMatchedSlidingWindow(List<WeatherHistoryDo> currentYearSevenDaysWeatherDataList,
			Map<String, List<WeatherHistoryDo>> slidingWindowMap) {

		LOGGER.debug("Enter findBestMatchedSlidingWindow.");

		String bestMatchedWindowId = null;
		List<EuclideanDistanceVo> bestMatchedSlidingWindowEuclideanDistance = null;

		List<EuclideanDistanceVo> currentWindowEuclideanDistance = null;

		Set<String> slidingWindowskeySet = slidingWindowMap.keySet();

		for (String windowId : slidingWindowskeySet) {
			currentWindowEuclideanDistance = findEuclideanDistance(currentYearSevenDaysWeatherDataList,
					slidingWindowMap.get(windowId), windowId);
			if (bestMatchedSlidingWindowEuclideanDistance == null) {
				bestMatchedSlidingWindowEuclideanDistance = currentWindowEuclideanDistance;
			} else {
				bestMatchedSlidingWindowEuclideanDistance = minEuclideanDistance(currentWindowEuclideanDistance,
						bestMatchedSlidingWindowEuclideanDistance);
			}
		}

		bestMatchedWindowId = bestMatchedSlidingWindowEuclideanDistance.get(0).getWindowId();

		LOGGER.debug("Exit findBestMatchedSlidingWindow.");
		return bestMatchedWindowId;
	}

	
	/**
	 * Find the difference in parameters between the current week & sliding windows. Compare parameters of each day,
	 * 
	 * @param currentYearSevenDaysWeatherDataList
	 * @param previousYearSlidingWindow
	 * @param windowId
	 * @return slidingWindowEuclideanDistanceVoList
	 */
	
	public static List<EuclideanDistanceVo> findEuclideanDistance(List<WeatherHistoryDo> currentYearSevenDaysWeatherDataList,
			List<WeatherHistoryDo> previousYearSlidingWindow, String windowId) {
		
		List<EuclideanDistanceVo> slidingWindowEuclideanDistanceVoList = new ArrayList<EuclideanDistanceVo>();
		EuclideanDistanceVo euclideanDistanceVo = null;
		WeatherHistoryDo cdWeather = null; // current year
		WeatherHistoryDo pdWeather = null; // previous Year
		for (int i =0;i<currentYearSevenDaysWeatherDataList.size();i++){
			cdWeather = currentYearSevenDaysWeatherDataList.get(i);
			pdWeather = previousYearSlidingWindow.get(i);
			
			euclideanDistanceVo = new EuclideanDistanceVo();
			
			euclideanDistanceVo.setWindowId(windowId);
			euclideanDistanceVo.setDate(pdWeather.getDate());
			euclideanDistanceVo.setLocation(pdWeather.getLocation());
			
			double minTempDiff = 0;
			if (pdWeather.getMinTemp() - cdWeather.getMinTemp() !=0){
				minTempDiff = Math.sqrt(Math.pow(pdWeather.getMinTemp() - cdWeather.getMinTemp(), 2));
			} 
			euclideanDistanceVo.setMinTempDiff(minTempDiff);
			
			
			double maxTempDiff = 0;
			if (pdWeather.getMinTemp() - cdWeather.getMinTemp() !=0){
				maxTempDiff = Math.sqrt(Math.pow(pdWeather.getMaxTemp() - cdWeather.getMaxTemp(), 2));
			} 
			euclideanDistanceVo.setMaxTempDiff(maxTempDiff);
			
			
			double temperatureDiff = 0;
			if(pdWeather.getTemp() - cdWeather.getTemp() !=0){
				temperatureDiff = Math.sqrt(Math.pow(pdWeather.getTemp() - cdWeather.getTemp(), 2));
			}
			euclideanDistanceVo.setTemperatureDiff(temperatureDiff);
			
			
			double dewPointDiff = 0;
			if(pdWeather.getTemp() - cdWeather.getTemp() !=0){
				dewPointDiff = Math.sqrt(Math.pow(pdWeather.getDewPoint() - cdWeather.getDewPoint(), 2));
			}
			euclideanDistanceVo.setDewPointDiff(dewPointDiff);
			
			slidingWindowEuclideanDistanceVoList.add(euclideanDistanceVo);
				
			
		}
		return slidingWindowEuclideanDistanceVoList;

	}
	
	
	/**
	 * Compare and returns the list which has less difference in weather
	 * parameters,
	 * 
	 * @param currentWindowList
	 * @param bestSlidingWindowList
	 * @return
	 */
	public static List<EuclideanDistanceVo> minEuclideanDistance(List<EuclideanDistanceVo> currentWindowList,
			List<EuclideanDistanceVo> bestSlidingWindowList) {
		List<EuclideanDistanceVo> slidingWindowWithMinDistanceList = null;

		double currWindowDistance = 0.0;
		double bestWindowDistance = 0.0;

		for (EuclideanDistanceVo r : currentWindowList) {
			currWindowDistance += r.getMinTempDiff() + r.getMaxTempDiff() + r.getTemperatureDiff()
					+ r.getDewPointDiff();
		}

		for (EuclideanDistanceVo r : bestSlidingWindowList) {
			bestWindowDistance += r.getMinTempDiff() + r.getMaxTempDiff() + r.getTemperatureDiff()
					+ r.getDewPointDiff();
		}

		slidingWindowWithMinDistanceList = currWindowDistance < bestWindowDistance ? currentWindowList
				: bestSlidingWindowList;
		return slidingWindowWithMinDistanceList;

	}
	
	

	/**
	 * Computes the variation factor of MinTemp parameter.
	 * 
	 * @param currenrYearRecentSevenDaysDataList
	 * @param bestMatchedWindowDataList
	 * @return
	 */
	public static Long computeMinTempVariationFactor(List<WeatherHistoryDo> currenrYearRecentSevenDaysDataList,
			List<WeatherHistoryDo> bestMatchedWindowDataList) {
		List<Double> minTempVariationList = new ArrayList<Double>();
		for (int i = 0; i < (bestMatchedWindowDataList.size() - 1); i++) {
			double minTempChange = bestMatchedWindowDataList.get(i).getMinTemp()
					- currenrYearRecentSevenDaysDataList.get(i + 1).getMinTemp();
			minTempVariationList.add(minTempChange);

		}
		Long meanMinTemp = 0L;
		double totalMinTempChange = 0;
		for (Double minTempChamge : minTempVariationList) {
			totalMinTempChange += minTempChamge;
		}
		meanMinTemp = Math.round(totalMinTempChange / minTempVariationList.size());

		return meanMinTemp;
	}
	
	/**
	 * Computes the variation factor of MaxTemp parameter.
	 * 
	 * @param currenrYearRecentSevenDaysDataList
	 * @param bestMatchedWindowDataList
	 * @return
	 */
	public static Long computeMaxTempVariationFactor(List<WeatherHistoryDo> currenrYearRecentSevenDaysDataList,
			List<WeatherHistoryDo> bestMatchedWindowDataList) {
		List<Double> maxTempVariationList = new ArrayList<Double>();
		for (int i = 0; i < (bestMatchedWindowDataList.size() - 1); i++) {
			double maxTempChange = bestMatchedWindowDataList.get(i).getMaxTemp()
					- currenrYearRecentSevenDaysDataList.get(i + 1).getMaxTemp();
			maxTempVariationList.add(maxTempChange);

		}
		
		Long meanMaxTemp = 0L;
		double totalMaxTempChange = 0;
		for (Double maxTempChamge : maxTempVariationList) {
			totalMaxTempChange += maxTempChamge;
		}
		meanMaxTemp = Math.round(totalMaxTempChange / maxTempVariationList.size());

		return meanMaxTemp;
	}
	
	
	/**
	 * Computes the variation factor of Temperature parameter.
	 * @param currenrYearRecentSevenDaysDataList
	 * @param bestMatchedWindowDataList
	 * @return
	 */
	public static Long computeTemparatureVariationFactor(List<WeatherHistoryDo> currenrYearRecentSevenDaysDataList,
			List<WeatherHistoryDo> bestMatchedWindowDataList) {
		List<Double> minDewPointVariationList = new ArrayList<Double>();
		for (int i = 0; i < (bestMatchedWindowDataList.size() - 1); i++) {
			double minDewPointChange = bestMatchedWindowDataList.get(i).getTemp()
					- currenrYearRecentSevenDaysDataList.get(i + 1).getTemp();
			minDewPointVariationList.add(minDewPointChange);

		}
		Long meanMinDewPoint = 0L;
		double totalMinDewPointChange = 0;
		for (Double minDewPointChamge : minDewPointVariationList) {
			totalMinDewPointChange += minDewPointChamge;
		}
		meanMinDewPoint = Math.round(totalMinDewPointChange / minDewPointVariationList.size());

		return meanMinDewPoint;
	}
	
	/**
	 * Computes the variation factor of DewPoint parameter. 
	 * @param currenrYearRecentSevenDaysDataList
	 * @param bestMatchedWindowDataList
	 * @return
	 */
	public static Long computeDewPointVariationFactor(List<WeatherHistoryDo> currenrYearRecentSevenDaysDataList,
			List<WeatherHistoryDo> bestMatchedWindowDataList) {
		List<Double> maxDewPointVariationList = new ArrayList<Double>();
		for (int i = 0; i < (bestMatchedWindowDataList.size() - 1); i++) {
			double maxDewPointChange = bestMatchedWindowDataList.get(i).getDewPoint()
					- currenrYearRecentSevenDaysDataList.get(i + 1).getDewPoint();
			maxDewPointVariationList.add(maxDewPointChange);

		}
		Long meanMaxDewPoint = 0L;
		double totalMaxDewPointChange = 0;
		for (Double maxDewPointChamge : maxDewPointVariationList) {
			totalMaxDewPointChange += maxDewPointChamge;
		}
		meanMaxDewPoint = Math.round(totalMaxDewPointChange / maxDewPointVariationList.size());

		return meanMaxDewPoint;
	}

}
