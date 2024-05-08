package com.accenture.weather;

import com.fasterxml.jackson.annotation.JsonProperty;


public class WeatherModel
{
    //private String date;
    private String dayName;
    private int tempHighCelsius;
    private String forecastBlurb;

    public WeatherModel()
    {
        //date = "";
        dayName = "";
        tempHighCelsius = 0;
        forecastBlurb = "";
    }

    public WeatherModel(String day, int high, String forecast)
    {
        //this.date = date;
        this.dayName = day;
        this.tempHighCelsius = high;
        this.forecastBlurb = forecast;
    }

//    public String get_date()
//    {
//        return date;
//    }

    public String getDayName()
    {
        return dayName;
    }

    public int getTempHighCelsius()
    {
        return tempHighCelsius;
    }

    public String getForecastBlurb()
    {
        return forecastBlurb;
    }

    public static int ConvertTempF2C(int fahrenheit)
    {
        return (int)((fahrenheit - 32.0) * (0.5555));
    }

    public static int ConvertTempC2F(int celsius)
    {
        return (int)((celsius * 1.8) + 32.0);
    }
}
