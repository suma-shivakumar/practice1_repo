package com.example.product.controller;

import com.example.product.domain.responses.CurrentWeather;
import com.example.product.services.LiveWeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CurrentWeatherController {

    @Autowired
    private LiveWeatherService liveWeatherService;

    @GetMapping("/currentWeather")
    public CurrentWeather getCurrentWeather(@RequestParam String city, @RequestParam String country) {
        return liveWeatherService.getCurrentWeather(city, country);
    }
}