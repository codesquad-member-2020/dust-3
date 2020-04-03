package com.codesquad.dust3.forecast;

import java.util.List;

public class ForecastInfos {
    private List<ForecastInfo> forecastInfos;

    public ForecastInfos(List<ForecastInfo> forecastInfos) {
        this.forecastInfos = forecastInfos;
    }

    public List<ForecastInfo> getForecastInfos() {
        return forecastInfos;
    }

    public void setForecastInfos(List<ForecastInfo> forecastInfos) {
        this.forecastInfos = forecastInfos;
    }
}
