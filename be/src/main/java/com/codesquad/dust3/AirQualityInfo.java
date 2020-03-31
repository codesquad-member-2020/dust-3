package com.codesquad.dust3;

public class AirQualityInfo {
    private String airQualityIndex;
    private int PM10;
    private String currentTime;
    private String location;

    public String getAirQualityIndex() {
        return airQualityIndex;
    }

    public void setAirQualityIndex(String airQualityIndex) {
        this.airQualityIndex = airQualityIndex;
    }

    public int getPM10() {
        return PM10;
    }

    public void setPM10(int PM10) {
        this.PM10 = PM10;
    }

    public String getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(String currentTime) {
        this.currentTime = currentTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "AirQualityInfo{" +
                "airQualityIndex='" + airQualityIndex + '\'' +
                ", PM10='" + PM10 + '\'' +
                ", currentTime='" + currentTime + '\'' +
                ", location='" + location + '\'' +
                '}';
    }
}
