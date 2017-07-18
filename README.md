# Weather Forecast
**weather-forecast** is a Java based utility project to predict the weather conditions for the upcoming days. Historical weather data is considered to forecast the weather for the future dates.

## Approach
The weather station and Historical weather data are stored in CSV file.  Historical weather of 2014 & 2015 are taken as base years. Weather history contains the Minimum &  Maximum temperatures of every day, DewPoint(DP) & the Temparature at which the DP is recorded.  A csv based _Database query engine_ **CsvJdbc** is used to query the csv files.  The time of the weather parameters is generated ramdomly. Other parameters like pressure, relative humidity and weather conditions are calculated with the temperature, elevation and other known parameters. This is finally encapsulated into the WetherForecast model and emitted in the below format.


Eg: **SYD|-33.865143|151.2099|2016-01-01T15:29:38Z|SUNNY|25.0|1012.8|55.0**

This approach is just to simulate a toy model of future environment from the base year’s weather data with a few set of basic paramters. Considering other parameters like climate changes, wind direction and other meteorological aspects to improve the accuracy of results. 

The calculation of Humidity and Event predictions need significant improvements. Current season’s average values for temperature and humidity can be considered to get more reasonable results.

## Other Approaches
Machine learning techniques can be used on historical data to get more accurate results and also consider additional features to improve the accuracy.

## Technology Stack
* Java 8
* MyBatis
* Maven
* JUnit

## Usage Instructions
1. Build using maven 
    ```mvn clean package```

2. Excute utility
    ```cd target
    java -jar weather-forecast-1.0.0-jar-with-dependencies.jar <optional args : no of days>
