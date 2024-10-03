package com.alvaro.techTaskVodafone.controller;
import com.alvaro.techTaskVodafone.service.WeatherService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.alvaro.techTaskVodafone.model.WeatherResponse;
import com.alvaro.techTaskVodafone.service.TemperatureCacheService;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;


@SpringBootTest
public class WeatherControllerTest {

    @Autowired
    private WeatherController weatherController;

    private WeatherService weatherService = Mockito.mock(WeatherService.class);
    private TemperatureCacheService temperatureCacheService = Mockito.mock(TemperatureCacheService.class);

    @Test
    public void testGetWeather() {
        double latitude = 36.72016;
        double longitude = -4.42034;

        WeatherResponse mockResponse = new WeatherResponse(latitude, longitude, 28.0);
        when(weatherService.getCurrentWeather(latitude, longitude)).thenReturn(mockResponse);
        when(temperatureCacheService.getCachedTemperature(latitude, longitude)).thenReturn(Optional.empty());


        WeatherResponse response = weatherController.getWeather(latitude, longitude);

        assertNotNull(response);
        assertEquals(latitude, response.getLatitude());
        assertEquals(longitude, response.getLongitude());
        assertEquals(28.0, response.getTemperature()); // Needs to be updated to the current temperature
    }
    @Test
    public void testDeleteTemperature_Success() {
        double latitude = 36.72016;
        double longitude = -4.42034;

        // Act
        ResponseEntity<String> response = weatherController.deleteTemperature(latitude, longitude);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Temperature data deleted successfully.", response.getBody());
    }
}

