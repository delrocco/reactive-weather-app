package com.accenture.weather;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public final class WeatherModel
{
    private final String dayName;
    private final int tempHighCelsius;
    private final String forecastBlurb;

    public WeatherModel()
    {
        dayName = "";
        tempHighCelsius = 0;
        forecastBlurb = "";
    }

    public WeatherModel(String day, int high, String forecast)
    {
        this.dayName = day;
        this.tempHighCelsius = high;
        this.forecastBlurb = forecast;
    }

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
