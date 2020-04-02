package com.codesquad.dust3.airquality;

import com.codesquad.dust3.webclient.KakaoApiClient;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AirQualityController {
    private static final KakaoApiClient kakaoApiClient = new KakaoApiClient();

    @GetMapping("air-quality-info")
    public ResponseEntity<AirQualityInfos> getAirQualityInfo(@RequestParam(name = "x") String wgsX,
                                                             @RequestParam(name = "y") String wgsY) {
        try {
            Coordinates wgsCoordinates = new Coordinates(Double.parseDouble(wgsX), Double.parseDouble(wgsY));
            Coordinates tmCoordinates = kakaoApiClient.getTmCoordinatesFrom(wgsCoordinates);
            return ResponseEntity.ok(new AirQualityInfos());
        } catch (JsonProcessingException | IllegalArgumentException e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }
}
