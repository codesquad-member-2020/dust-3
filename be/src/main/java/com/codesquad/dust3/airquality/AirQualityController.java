package com.codesquad.dust3.airquality;

import com.codesquad.dust3.webclient.KakaoApiClient;
import com.codesquad.dust3.webclient.OpenApiClient;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AirQualityController {
    private static final KakaoApiClient kakaoApiClient = new KakaoApiClient();
    private static final OpenApiClient openApiClient = new OpenApiClient();

    @CrossOrigin("*")
    @GetMapping("air-quality-info")
    public ResponseEntity<AirQualityInfos> getAirQualityInfo(@RequestParam(name = "x") String wgsX,
                                                             @RequestParam(name = "y") String wgsY) throws JsonProcessingException {
        Coordinates wgsCoordinates = new Coordinates(Double.parseDouble(wgsX), Double.parseDouble(wgsY));
        Coordinates tmCoordinates = kakaoApiClient.getTmCoordinatesFrom(wgsCoordinates);
        String stationName = openApiClient.getNearestStationFrom(tmCoordinates);
        AirQualityInfos airQualityInfos = openApiClient.getAirQualityInfosOf(stationName);
        return ResponseEntity.ok(airQualityInfos);
    }
}
