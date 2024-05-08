package com.accenture.weather;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;


@Service
public class WeatherService
{
    private static final Logger Logger = LoggerFactory.getLogger(WeatherService.class);

    @Autowired
    private WeatherDAO daoWeather;

    public WeatherService()
    {

    }

    public Mono<WeatherModel> getForecastOfDay(int dayidx, boolean isNight)
    {
        Logger.info("------------------------------------------------------------");
        Logger.info(String.format("Forecast requested for: dayidx(%d) isnight(%s)", dayidx, String.valueOf(isNight)));

        Mono<WeatherModel> weather = daoWeather.getForecastOfDay(dayidx, isNight);

        Logger.info("------------------------------------------------------------");

        return weather;
    }
}
