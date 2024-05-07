package com.accenture.weather;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Controller
public class WeatherController
{
    @Autowired
    private WeatherService svcWeather;

    @GetMapping({"/", "/weather"})
    public String weather(
            @RequestParam(name="ahead", defaultValue="1") int ahead,
            @RequestParam(name="night", defaultValue="false") boolean night,
            Model model)
    {
        WeatherModel modelWeather = svcWeather.getForecastOfDay(ahead, night);

        model.addAttribute("date", modelWeather.get_date());
        model.addAttribute("day_name", modelWeather.get_day_name());
        model.addAttribute("temp_high_celsius", modelWeather.get_temp_high_celsius());
        model.addAttribute("forecast_blurb", modelWeather.get_forecast_blurb());

        return "weather";
    }

    private String getValidDate()
    {
        return null;
    }
}
