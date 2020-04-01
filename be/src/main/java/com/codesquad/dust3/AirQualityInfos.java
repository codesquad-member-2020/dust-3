package com.codesquad.dust3;

import java.util.List;

public class AirQualityInfos {
    public List<AirQualityInfo> getAirQualityInfos() {
        return airQualityInfos;
    }

    private List<AirQualityInfo> airQualityInfos;

    public AirQualityInfos(List<AirQualityInfo> airQualityInfos) {
        this.airQualityInfos = airQualityInfos;
    }
}
