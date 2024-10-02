package com.alvaro.techTaskVodafone.service;

import com.alvaro.techTaskVodafone.model.OpenMeteoResponse;
import com.alvaro.techTaskVodafone.model.WeatherResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {

    private final String API_URL = "https://api.open-meteo.com/v1/forecast";

    public WeatherResponse getCurrentWeather(double latitude, double longitude){

        RestTemplate restTemplate = new RestTemplate();
        String url = API_URL + "?latitude=" + String.valueOf(latitude) +
                    "&longitude=" + String.valueOf(longitude) +
                    "&current=temperature_2m";
        OpenMeteoResponse response = restTemplate.getForObject(url, OpenMeteoResponse.class);

        double temperature = response.getCurrent().getTemperature2m();

        return new WeatherResponse(latitude,longitude,temperature);

    }
}
