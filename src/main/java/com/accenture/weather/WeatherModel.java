package com.accenture.weather;

public class WeatherModel
{
    private String date;
    private String day_name;
    private String temp_high_celsius;
    private String forecast_blurb;

    public WeatherModel()
    {
        date = "";
        day_name = "";
        temp_high_celsius = "";
        forecast_blurb = "";
    }

    public WeatherModel(String date, String day, String high, String forecast)
    {
        this.date = date;
        this.day_name = day;
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
