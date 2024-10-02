package com.alvaro.techTaskVodafone.controller;

import com.alvaro.techTaskVodafone.model.Temperature;
import com.alvaro.techTaskVodafone.model.WeatherResponse;
import com.alvaro.techTaskVodafone.service.TemperatureCacheService;
import com.alvaro.techTaskVodafone.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/weather")
public class WeatherController {

    @Autowired
    private WeatherService weatherService;

    @Autowired
    private TemperatureCacheService temperatureCacheService;

    @GetMapping
    public WeatherResponse getWeather(@RequestParam double latitude, @RequestParam double longitude){
        System.out.println("%%%%%%%%%%%Elimino duplicados%%%%%%%%%%%");
        temperatureCacheService.removeOldDuplicates(latitude,longitude);
        Optional<Temperature> cachedData = temperatureCacheService.getCachedTemperature(latitude,longitude);

        if(cachedData.isPresent()) {
            return new WeatherResponse(latitude,longitude,cachedData.get().getTemperature());
        }

        WeatherResponse weatherResponse = weatherService.getCurrentWeather(latitude,longitude);

        Temperature temperature = new Temperature();

        temperature.setLatitude(latitude);
        temperature.setLongitude(longitude);
        temperature.setTemperature(weatherResponse.getTemperature());
        temperature.setTimeStamp(LocalDateTime.now());


        System.out.println("%%%%%%%%%%%Guardo en base de datos%%%%%%%%%%%");
        temperatureCacheService.saveTemperature(temperature);
        System.out.println("%%%%%%%%%%%Guardado%%%%%%%%%%%");

        return weatherResponse;
    }

    @DeleteMapping
    public ResponseEntity<String> deleteTemperature(@RequestParam double latitude, @RequestParam double longitude){
        try {
            temperatureCacheService.deleteByLatitudeAndLongitude(latitude, longitude);
            return ResponseEntity.ok("Temperature data deleted successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error deleting temperature data.");
        }

    }



}
