package com.accenture.weather;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class WeatherService
{
    public WeatherService()
    {

    }

    public Mono<WeatherModel> getForecastOfDay(String dayidx, String night)
    {
        return Mono.just(new WeatherModel());
    }
}
