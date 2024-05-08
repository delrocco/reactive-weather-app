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
    public Mono<WeatherModel> getForecastToday()
    {
        return svcWeather.getForecastOfDay("1", "false");
    }

    @GetMapping("/{dayidx}")
    public Mono<WeatherModel> getForecastOfDay(
            @PathVariable String dayidx,
            @RequestParam(name="night", defaultValue="false") String night)
    {
        return svcWeather.getForecastOfDay(dayidx, night);
    }
}
