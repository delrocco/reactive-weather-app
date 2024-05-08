package com.accenture.weather;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;


@Service
public class WeatherService
{
    @Autowired
    private WeatherDAO daoWeather;

    public WeatherService()
    {
    }

    public Flux<WeatherModel> getForecast(boolean isWeek)
    {
        if (isWeek)
            return daoWeather.getForecastWeek();
        else
            return daoWeather.getForecastToday();
    }
}
