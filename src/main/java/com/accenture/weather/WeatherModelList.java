package com.accenture.weather;


public class WeatherModelList
{
    private WeatherModel[] daily;

    protected WeatherModelList() {}

    public WeatherModelList(WeatherModel[] forecasts)
    {
        daily = forecasts;
    }

    public WeatherModel[] getDaily()
    {
        return daily;
    }

    public void setDaily(WeatherModel[] forecasts)
    {
        daily = forecasts;
    }
}
