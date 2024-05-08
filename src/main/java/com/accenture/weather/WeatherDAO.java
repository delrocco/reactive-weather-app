package com.accenture.weather;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;


@Repository
public class WeatherDAO
{
    private static final Logger Logger = LoggerFactory.getLogger(WeatherDAO.class);
    private static final String URLForecast = "https://api.weather.gov/gridpoints/MLB/33,70/forecast";

    public Mono<WeatherModel> getForecastOfDay(int dayidx, boolean isNight)
    {
        return Mono.just(new WeatherModel());
    }

    private int clampInt(int n, int min, int max)
    {
        return Math.min(Math.max(n, min), max);
    }

//    public Mono<WeatherModel> getForecastOfDay(int ahead, boolean night)
//    {
//        WeatherModel modelWeather = new WeatherModel();

//       JsonObject rootobj = null;

//        // get root JSON
//        try
//        {
//            URL url = new URL(URLForecast);
//            URLConnection request = url.openConnection();
//            request.connect();
//            rootobj = JsonParser.parseReader(
//                    new InputStreamReader((InputStream)request.getContent())).getAsJsonObject();
//        }
//        catch (IOException ex)
//        {
//            System.out.println("Error! Could not connect and stream weather data!");
//        }
//
//        // indices of periods are 2 per day, so...
//        // today = 1
//        // tonight = 2
//        // tomorrow day = 3
//        // tomorrow night = 4
//        // etc.
//        int periodIdx = 2 * ahead + (night ? 1 : 0);
//
//        if (rootobj != null)
//        {
//            JsonArray periods = rootobj.get("properties").getAsJsonObject().get("periods").getAsJsonArray();
//            JsonObject forecastDay = periods.get(periodIdx).getAsJsonObject();
//
//            //LocalDate date = LocalDateTime.parse(forecastDay.get("startTime").getAsString()).toLocalDate();
//            //LocalDate date = LocalDateTime.parse("2024-05-08").toLocalDate();
//            String datetime = forecastDay.get("startTime").getAsString();
//            String temp = forecastDay.get("temperature").getAsString();
//            String blurb = forecastDay.get("shortForecast").getAsString();
//
//            // create weather model
//            modelWeather = new WeatherModel(LocalDate.now(), temp, blurb);
//        }

//        return Mono.just(modelWeather);
//    }
}
