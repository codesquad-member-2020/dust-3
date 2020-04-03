package com.codesquad.dust3.forecast;

import com.fasterxml.jackson.databind.JsonNode;

public class ForecastInfo {
    private String forecastImage;
    private String generalForecast;
    private String airQualityIndexByRegion;

    public ForecastInfo(int hour, JsonNode nodeOfForecastInfo) {
        forecastImage = "http://52.78.203.80:8080/forecast-" + hour + ".png";
        generalForecast = nodeOfForecastInfo.required("informCause").asText();
        airQualityIndexByRegion = nodeOfForecastInfo.required("informGrade").asText();
    }

    public String getForecastImage() {
        return forecastImage;
    }

    public void setForecastImage(String forecastImage) {
        this.forecastImage = forecastImage;
    }

    public String getGeneralForecast() {
        return generalForecast;
    }

    public void setGeneralForecast(String generalForecast) {
        this.generalForecast = generalForecast;
    }

    public String getAirQualityIndexByRegion() {
        return airQualityIndexByRegion;
    }

    public void setAirQualityIndexByRegion(String airQualityIndexByRegion) {
        this.airQualityIndexByRegion = airQualityIndexByRegion;
    }
}
