package com.accenture.weather;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping("/weather")
public class WeatherController
{
    @Autowired
    private WeatherService svcWeather;

    @GetMapping
    public Mono<WeatherModel> getForecastOfDay(
            @RequestParam(name="dayidx", defaultValue="1") Integer dayidx,
            @RequestParam(name="night", defaultValue="false") Boolean isNight)
    {
        return svcWeather.getForecastOfDay(dayidx, isNight);
    }
}
