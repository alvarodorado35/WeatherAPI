package com.alvaro.techTaskVodafone.repository;

import com.alvaro.techTaskVodafone.model.Temperature;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TemperatureRepository extends MongoRepository<Temperature,String> {
    @Query(value = "{ 'latitude': ?0, 'longitude': ?1 }", sort = "{ 'timestamp': -1 }")
    Optional<Temperature> findLatestByLatitudeAndLongitude(double latitude, double longitude);

    List<Temperature> findAllByLatitudeAndLongitude(double latitude, double longitude);

    void deleteByLatitudeAndLongitude(double latitude, double longitude);

}
