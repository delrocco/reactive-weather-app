package com.accenture.weather;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;


@Repository
public class WeatherDAO
{
    private static final Logger Logger = LoggerFactory.getLogger(WeatherDAO.class);

    // NOTE This data provide weather periods every 12 hours (day (6am-6pm) and night (6pm-6am))
    private static final String URLForecast = "https://api.weather.gov/gridpoints/MLB/33,70/forecast";

    private List<WeatherModel> mWeekForecasts = new ArrayList<>(32);

    public WeatherDAO()
    {
    }

    public Flux<WeatherModel> getForecast(boolean isWeek)
    {
        // for now, always fetch whole week and return the day/night of interest

        // should be able to do this w/ Project Reactor async Mono or Flux, but having trouble...
        //fetchWeeklyForecast();

        // BLOCKING!
        fetchWeeklyForecastBlocking();

        if (!isWeek)
            return Flux.just( mWeekForecasts.get(0) );
        else
        {
            return Flux.fromIterable(mWeekForecasts);
        }
    }

    private void fetchWeeklyForecast()
    {
        mWeekForecasts.clear();

//        // We should really do this in a separate thread and return a Flux of models...
//        webClient.get().retrieve().bodyToFlux(WeatherModel.class).subscribe(
//                result -> {
//                    // parse JSON, get models
//                    mWeekForecasts.add(new WeatherModel());
//                },
//                error -> {
//                    Logger.error(String.format("Error parsing weather data JSON. %s", error.getMessage()));
//                }
//        );

//        // parse the JSON
//        try
//        {
//
//
//            // talk to weather service, get forecast and parse JSON to models
//            WebClient webClient = WebClient.create(URLForecast);
//            //String resultJSON = webClient.get().retrieve().bodyToMono(String.class).toFuture().get();
//            webClient.get().retrieve().bodyToMono(String.class).subscribe(
//                    bodyJSON -> {
//                        try
//                        {
//                            ObjectMapper objectMapper = new ObjectMapper();
//                            JsonNode nodeRoot = objectMapper.readTree(bodyJSON);
//                            JsonNode nodePeriods = nodeRoot.get("properties").get("periods");
//
//                            if (nodePeriods != null && nodePeriods.isArray())
//                            {
//                                for (JsonNode node : nodePeriods)
//                                {
//                                    // parse the date and day
//                                    String day = node.get("name").asText();
//                                    String dtstr = node.get("startTime").asText();
//                                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX");
//                                    ZonedDateTime zonedDateTime = ZonedDateTime.parse(dtstr, formatter);
//
//                                    // parse the temperature
//                                    int temp = node.get("temperature").asInt();
//
//                                    // parse the forecast description
//                                    String blurb = node.get("shortForecast").asText();
//
//                                    mWeekForecasts.add(new WeatherModel(
//                                            zonedDateTime.toLocalDate().toString(),
//                                            day,
//                                            String.valueOf(temp),
//                                            blurb)
//                                    );
//                                }
//                            }
//                        }
//                        catch (Exception ex)
//                        {
//
//                        }
//                    }
//            );
//        }
//        catch (Exception ex)
//        {
//            Logger.error(String.format("Error parsing JSON weather data. %s", ex.getMessage()));
//        }
    }

    private void fetchWeeklyForecastBlocking()
    {
        try
        {
            // fetch JSON from webservice
            URL url = new URL(URLForecast);
            HttpURLConnection connex = (HttpURLConnection) url.openConnection();
            connex.setRequestMethod("GET");
            BufferedReader reader = new BufferedReader(new InputStreamReader(connex.getInputStream()));
            StringBuilder responseBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null)
                responseBuilder.append(line);
            reader.close();
            String bodyJSON = responseBuilder.toString();
            connex.disconnect();

            // parse JSON
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode nodeRoot = objectMapper.readTree(bodyJSON);
            JsonNode nodePeriods = nodeRoot.get("properties").get("periods");
            if (nodePeriods != null && nodePeriods.isArray())
            {
                for (JsonNode node : nodePeriods)
                {
                    // parse the date and day
                    String day = node.get("name").asText();
                    String dtstr = node.get("startTime").asText();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX");
                    ZonedDateTime zonedDateTime = ZonedDateTime.parse(dtstr, formatter);

                    // parse the temperature (and units)
                    String tempUnits = node.get("temperatureUnit").asText();
                    int temp = node.get("temperature").asInt();
                    if (tempUnits.equalsIgnoreCase("F")) // client wants it in C for now
                        temp = WeatherModel.ConvertTempF2C(temp);

                    // parse the forecast description
                    String blurb = node.get("shortForecast").asText();

                    mWeekForecasts.add(new WeatherModel(
                            //zonedDateTime.toLocalDate().toString(),
                            day,
                            temp,
                            blurb)
                    );
                }
            }
        }
        catch (Exception ex)
        {
            Logger.error(String.format("Error parsing JSON weather data. %s", ex.getMessage()));
        }
    }
}
