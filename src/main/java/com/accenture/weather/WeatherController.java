package com.accenture.weather;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;


@RestController
@Validated
@RequestMapping("/weather")
public class WeatherController
{
    @Autowired
    private WeatherService svcWeather;

    @GetMapping
    public Flux<WeatherModel> getForecast(@Valid @NotNull @RequestParam(name="week", defaultValue="false") Boolean isWeek)
    {
        return svcWeather.getForecast(isWeek);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class, ConstraintViolationException.class, TypeMismatchException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity<String> handleParamExceptions(Exception e)
    {
        return new ResponseEntity<>("URL param validation error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
