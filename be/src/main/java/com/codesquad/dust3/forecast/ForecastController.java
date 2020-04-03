package com.codesquad.dust3.forecast;

import com.codesquad.dust3.webclient.OpenApiClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class ForecastController {
    private static final OpenApiClient openApiClient = new OpenApiClient();

    @CrossOrigin("*")
    @GetMapping("/forecast-info")
    public ResponseEntity<ForecastInfos> getForecastInfo() throws IOException {
        ForecastInfos forecastInfos = openApiClient.getForecastInfos();
        return ResponseEntity.ok(forecastInfos);
    }
}
