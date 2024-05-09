package com.accenture.weather;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


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
                .bodyToMono(WeatherModelResponse.class)
                .map(response -> response.properties)
                .map(list -> list.ListSingleForecast(list, 0));
    }

    public Mono<WeatherModelList> getForecastWeek()
    {
        return webForecastSvc
                .get()
                .uri(ForecastsURI).retrieve()
                .bodyToMono(WeatherModelResponse.class)
                .map(response -> response.properties);
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class WeatherModelResponse
    {
        @JsonProperty("properties")
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
