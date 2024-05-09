package com.accenture.weather;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;


@Repository
public class WeatherDAO
{
    private static final Logger Logger = LoggerFactory.getLogger(WeatherDAO.class);

    // NOTE This data provides weather periods every 12 hours (day (6am-6pm) and night (6pm-6am))
    private static final String ForecastsDomain = "https://api.weather.gov";
    private static final String ForecastsURI = "/gridpoints/MLB/33,70/forecast";
    private WebClient webForecastSvc;

    public WeatherDAO()
    {
        webForecastSvc = WebClient.builder().baseUrl(ForecastsDomain).build();
    }

    public Mono<WeatherModelList> getForecastToday()
    {
        return webForecastSvc
                .get()
                .uri(ForecastsURI).retrieve()
                .bodyToMono(String.class)
                .map(json ->
                {
                    List<WeatherModel> forecasts = new LinkedList<>();

                    try
                    {
                        ObjectMapper objectMapper = new ObjectMapper();
                        JsonNode nodeRoot = objectMapper.readTree(json);

                        JsonNode nodePeriods = nodeRoot.get("properties").get("periods");
                        if (nodePeriods != null && nodePeriods.isArray())
                        {
                            JsonNode node = nodePeriods.get(0);
                            forecasts.add(parsePeriodToWeatherModel(node));
                        }
                    }
                    catch (Exception ex)
                    {
                        Logger.error(String.format("Error parsing JSON weather data. %s", ex.getMessage()));
                    }

                    return new WeatherModelList(forecasts.toArray(new WeatherModel[0]));
                });
    }

    public Mono<WeatherModelList> getForecastWeek()
    {
        return webForecastSvc
                .get()
                .uri(ForecastsURI).retrieve()
                .bodyToMono(String.class)
                .map(json ->
                {
                    List<WeatherModel> forecasts = new LinkedList<>();

                    try
                    {
                        ObjectMapper objectMapper = new ObjectMapper();
                        JsonNode nodeRoot = objectMapper.readTree(json);

                        JsonNode nodePeriods = nodeRoot.get("properties").get("periods");
                        if (nodePeriods != null && nodePeriods.isArray())
                        {
                            for (JsonNode node : nodePeriods)
                                forecasts.add(parsePeriodToWeatherModel(node));
                        }
                    }
                    catch (Exception ex)
                    {
                        Logger.error(String.format("Error parsing JSON weather data. %s", ex.getMessage()));
                    }

                    return new WeatherModelList(forecasts.toArray(new WeatherModel[0]));
                });
    }

    private WeatherModel parsePeriodToWeatherModel(JsonNode node)
    {
        // parse the "day name"
        String day = node.get("name").asText();

        // parse the temperature (and units)
        String tempUnits = node.get("temperatureUnit").asText();
        int temp = node.get("temperature").asInt();
        if (tempUnits.equalsIgnoreCase("F")) // client wants it in C for now
            temp = WeatherModel.ConvertTempF2C(temp);

        // parse the forecast description
        String blurb = node.get("shortForecast").asText();

        return new WeatherModel(day, temp, blurb);
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class WeatherModelResponse
    {
        public WeatherModelList properties;

        public WeatherModelResponse()
        {
            properties = new WeatherModelList();
        }

        public WeatherModelResponse(WeatherModelList properties)
        {
            this.properties = properties;
        }
    }
}
