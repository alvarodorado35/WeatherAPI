package com.alvaro.techTaskVodafone.controller;

import com.alvaro.techTaskVodafone.model.Temperature;
import com.alvaro.techTaskVodafone.model.WeatherResponse;
import com.alvaro.techTaskVodafone.service.TemperatureCacheService;
import com.alvaro.techTaskVodafone.service.WeatherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;

@Tag(name = "Weather API", description = "API for retrieving weather information")

@RestController
@RequestMapping("/weather")
public class WeatherController {

    @Autowired
    private WeatherService weatherService;

    @Autowired
    private TemperatureCacheService temperatureCacheService;
    @Operation(summary = "Get current temperature", description = "Provide latitude and longitude to get the current temperature")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved weather information",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = WeatherResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid latitude or longitude provided",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)
    })
    @GetMapping
    public WeatherResponse getWeather(
            @Parameter(description = "Latitude of the location", required = true, example = "36.72016")
            @RequestParam double latitude,
            @Parameter(description = "Longitude of the location", required = true, example = "-4.42034")
            @RequestParam double longitude){
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
        
        temperatureCacheService.saveTemperature(temperature);

        return weatherResponse;
    }

    @Tag(name = "Weather API", description = "Operations related to weather data")
    @Operation(summary = "Delete temperature data",
            description = "Delete temperature data for a specific location by providing its latitude and longitude")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Temperature data deleted successfully",
                    content = @Content(mediaType = "text/plain")),
            @ApiResponse(responseCode = "400", description = "Invalid latitude or longitude provided",
                    content = @Content(mediaType = "text/plain")),
            @ApiResponse(responseCode = "500", description = "Error deleting temperature data",
                    content = @Content(mediaType = "text/plain"))
    })
    @DeleteMapping
    public ResponseEntity<String> deleteTemperature(
            @Parameter(description = "Latitude of the location", required = true, example = "36.72016")
            @RequestParam double latitude,
            @Parameter(description = "Longitude of the location", required = true, example = "-4.42034")
            @RequestParam double longitude){
        try {
            temperatureCacheService.deleteByLatitudeAndLongitude(latitude, longitude);
            return ResponseEntity.ok("Temperature data deleted successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error deleting temperature data.");
        }

    }



}
