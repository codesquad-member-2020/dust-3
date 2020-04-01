package com.codesquad.dust3;

public class ForecastInfo {
    private String forecastImage;
    private String generalForecast;
    private String airQualityIndexByRegion;

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

    @Override
    public String toString() {
        return "ForecastInfo{" +
                "forecastImage='" + forecastImage + '\'' +
                ", generalForecast='" + generalForecast + '\'' +
                ", airQualityIndexByRegion='" + airQualityIndexByRegion + '\'' +
                '}';
    }
}
