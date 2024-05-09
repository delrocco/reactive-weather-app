package com.accenture.weather;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherModelList
{
    @JsonProperty("periods")
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

    public WeatherModelList ListSingleForecast(WeatherModelList list, int whichIdx)
    {
        return new WeatherModelList(new WeatherModel[] { list.getDaily()[whichIdx] });
    }
}
