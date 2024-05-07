package com.accenture.weather;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class WeatherService
{
    public static final String URLForecast = "https://api.weather.gov/gridpoints/MLB/33,70/forecast";

    public WeatherService()
    {

    }

    public WeatherModel getForecastOfDay(int ahead, boolean night)
    {
        WeatherModel modelWeather;

        modelWeather = getForecastOfDayAsync(ahead, night).block();

        return modelWeather;
    }

    private Mono<WeatherModel> getForecastOfDayAsync(int ahead, boolean night)
    {
        WeatherModel modelWeather = new WeatherModel();
        JsonObject rootobj = null;

        // get root JSON
        try
        {
            URL url = new URL(URLForecast);
            URLConnection request = url.openConnection();
            request.connect();
            rootobj = JsonParser.parseReader(
                    new InputStreamReader((InputStream)request.getContent())).getAsJsonObject();
        }
        catch (IOException ex)
        {
            System.out.println("Error! Could not connect and stream weather data!");
        }

        // indices of periods are 2 per day, so...
        // today = 1
        // tonight = 2
        // tomorrow day = 3
        // tomorrow night = 4
        // etc.
        int periodIdx = 2 * ahead + (night ? 1 : 0);

        if (rootobj != null)
        {
            JsonArray periods = rootobj.get("properties").getAsJsonObject().get("periods").getAsJsonArray();
            JsonObject forecastDay = periods.get(periodIdx).getAsJsonObject();

            //LocalDate date = LocalDateTime.parse(forecastDay.get("startTime").getAsString()).toLocalDate();
            //LocalDate date = LocalDateTime.parse("2024-05-08").toLocalDate();
            String datetime = forecastDay.get("startTime").getAsString();
            String temp = forecastDay.get("temperature").getAsString();
            String blurb = forecastDay.get("shortForecast").getAsString();

            // create weather model
            modelWeather = new WeatherModel(LocalDate.now(), temp, blurb);
        }

        return Mono.just(modelWeather);
    }
}
