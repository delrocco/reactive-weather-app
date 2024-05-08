package com.accenture.weather;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;


@RestController
@Validated
@RequestMapping("/weather")
public class WeatherController
{
    @Autowired
    private WeatherService svcWeather;

    @GetMapping
    public Mono<WeatherModel> getForecastOfDay(
            @Valid @Min(1) @Max(7)
            @RequestParam(name="dayidx", defaultValue="1")  Integer dayidx,
            @Valid @NotNull
            @RequestParam(name="night", defaultValue="false") Boolean isNight)
    {
        return svcWeather.getForecastOfDay(dayidx, isNight);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class, ConstraintViolationException.class, TypeMismatchException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity<String> handleParamExceptions(Exception e)
    {
        return new ResponseEntity<>("URL param validation error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
