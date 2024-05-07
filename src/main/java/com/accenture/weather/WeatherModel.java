package com.accenture.weather;

import org.springframework.ui.Model;

import java.time.LocalDate;

public class WeatherModel
{
    private String date;
    private String day_name;
    private String temp_high_celsius;
    private String forecast_blurb;

    public WeatherModel()
    {
        date = "empty";
        day_name = "empty";
        temp_high_celsius = "empty";
        forecast_blurb = "empty";
    }

    public WeatherModel(LocalDate date, String high, String forecast)
    {
        this.date = date.toString();
        this.day_name = date.getDayOfWeek().toString();
        this.temp_high_celsius = high;
        this.forecast_blurb = forecast;
    }

    public String get_date()
    {
        return date;
    }

    public String get_day_name()
    {
        return day_name;
    }

    public String get_temp_high_celsius()
    {
        return temp_high_celsius;
    }

    public String get_forecast_blurb()
    {
        return forecast_blurb;
    }
}
