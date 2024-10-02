package com.alvaro.techTaskVodafone.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OpenMeteoResponse {
    private double latitude;
    private double longitude;
    private Current current;

    public double getLatitude() {
        return latitude;
    }

    public Current getCurrent() {
        return current;
    }

    public void setCurrent(Current current) {
        this.current = current;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public static class Current {
        @JsonProperty("temperature_2m")
        private double temperature2m;


        public double getTemperature2m() {
            return temperature2m;
        }

        public void setTemperature2m(double temperature2m) {
            this.temperature2m = temperature2m;
        }
    }
}
