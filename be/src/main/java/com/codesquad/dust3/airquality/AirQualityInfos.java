package com.codesquad.dust3.airquality;

import java.util.List;

public class AirQualityInfos {
    private List<AirQualityInfo> airQualityInfos;

    public List<AirQualityInfo> getAirQualityInfos() {
        return airQualityInfos;
    }

    public void setAirQualityInfos(List<AirQualityInfo> airQualityInfos) {
        this.airQualityInfos = airQualityInfos;
    }

    public void addAirQualityInfo(AirQualityInfo airQualityInfo) {
        airQualityInfos.add(airQualityInfo);
    }
}
