package com.codesquad.dust3;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@RestController
public class AirQualityController {

    @GetMapping("/air-quality-info")
    public ResponseEntity<AirQualityInfos> getAirQualityInfo(@RequestParam String x, @RequestParam String y) {
        List<AirQualityInfo> airQualityInfos = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now().truncatedTo(ChronoUnit.HOURS);
        for (int i = 0; i < 24; i++) {
            AirQualityInfo aq = new AirQualityInfo();
            int PM10 = (1317121 * (i + 1)) % 300;
            aq.setPM10(PM10);
            int airQualityIndex = (PM10 / 50) + 1;
            if (airQualityIndex > 4) {
                airQualityIndex = 4;
            }
            String airQuality = "";
            switch (airQualityIndex) {
                case (1):
                    airQuality = "좋음";
                    break;
                case (2):
                    airQuality = "보통";

                case (3):
                    airQuality = "나쁨";
                    break;
                case (4):
                    airQuality = "매우 나쁨";
                    break;
            }
            aq.setAirQualityIndex(airQuality);
            LocalDateTime time = now.minusHours(i);
            aq.setCurrentTime(time.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
            aq.setLocation("종로구");
            airQualityInfos.add(aq);
        }
        return ResponseEntity.ok(new AirQualityInfos(airQualityInfos));
    }

    @GetMapping("/forecast-info")
    public ResponseEntity<ForecastInfos> getForecastInfo() {
        List<ForecastInfo> forecastInfos = new ArrayList<>();
        for (int i = 0; i < 24; i++) {
            ForecastInfo forecastInfo = new ForecastInfo();
            forecastInfo.setGeneralForecast("[미세먼지] 전 권역이 '좋음'∼'보통'으로 예상됨. 다만, 서울·경기도·충청권은 오전에 일시적으로 '나쁨' 수준일 것으로 예상됨.");
            forecastInfo.setAirQualityIndexByRegion("서울 : 보통,제주 : 좋음,전남 : 보통,전북 : 보통,광주 : 보통,경남 : 좋음,경북 : 좋음,울산 : 좋음,대구 : 좋음,부산 : 좋음,충남 : 보통,충북 : 보통,세종 : 보통,대전 : 보통,영동 : 좋음,영서 : 보통,경기남부 : 보통,경기북부 : 보통,인천 : 보통");
            forecastInfo.setForecastImage("52.78.203.80:8080/test-" + i + ".png");
            forecastInfos.add(forecastInfo);
        }
        return ResponseEntity.ok(new ForecastInfos(forecastInfos));
    }
}
