package com.alvaro.techTaskVodafone.service;

import com.alvaro.techTaskVodafone.model.Temperature;
import com.alvaro.techTaskVodafone.repository.TemperatureRepository;
import org.bson.io.BsonOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class TemperatureCacheService {

    @Autowired
    private TemperatureRepository temperatureRepository;

    public Optional<Temperature> getCachedTemperature(double latitude, double longitude){
        System.out.println("Latitud que me llega" + latitude);
        System.out.println("Longitud que me llega" + longitude);


        Optional<Temperature> cachedData = temperatureRepository.findLatestByLatitudeAndLongitude(latitude, longitude);

        if(cachedData.isPresent()) {
            Temperature temperature = cachedData.get();

            if(LocalDateTime.now().isBefore(temperature.getTimeStamp().plusMinutes(1))) {
                return cachedData;
            }
        }
        return Optional.empty();
    }

    public void saveTemperature(Temperature temperature){
        temperatureRepository.save(temperature);
    }

    public void removeOldDuplicates(double latitude, double longitude) {
        List<Temperature> temperatureList = temperatureRepository.findAllByLatitudeAndLongitude(latitude, longitude);


        if (temperatureList.size() > 1) {
            temperatureList.sort(Comparator.comparing(Temperature::getTimeStamp).reversed());

            for (int i = 1; i < temperatureList.size(); i++) {
                temperatureRepository.deleteById(temperatureList.get(i).getId());
            }
        }
    }

    public void deleteByLatitudeAndLongitude(double longitude, double latitude){
        temperatureRepository.deleteByLatitudeAndLongitude(longitude,latitude);
    }
}
