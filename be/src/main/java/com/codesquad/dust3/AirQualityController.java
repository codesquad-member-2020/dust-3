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
    public ResponseEntity<List<AirQualityInfo>> getAirQualityInfo(@RequestParam String x, @RequestParam String y) {
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
        return ResponseEntity.ok(airQualityInfos);
    }

    @GetMapping("/forecast-info")
    public void getForecastInfo() {

    }
}
