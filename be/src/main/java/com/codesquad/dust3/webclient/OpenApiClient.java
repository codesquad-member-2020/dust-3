package com.codesquad.dust3.webclient;

import com.codesquad.dust3.airquality.AirQualityInfo;
import com.codesquad.dust3.airquality.AirQualityInfos;
import com.codesquad.dust3.airquality.Coordinates;
import com.codesquad.dust3.forecast.ForecastInfo;
import com.codesquad.dust3.forecast.ForecastInfos;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class OpenApiClient {
    private static WebClient client;
    private static MultiValueMap<String, String> defaultQueryParams;
    private static ObjectMapper mapper;

    public OpenApiClient() {
        buildClient();
        buildDefaultQueryParams();
        mapper = new ObjectMapper();
    }

    public String getNearestStationFrom(Coordinates tmCoordinates) throws JsonProcessingException {
        String jsonOfNearestStations = getJsonOfNearestStationsFrom(tmCoordinates);
        return extractStationNameFrom(jsonOfNearestStations);
    }

    public AirQualityInfos getAirQualityInfosOf(String stationName) throws JsonProcessingException {
        String jsonOfAirQualityInfos = getJsonOfAirQualityInfosOf(stationName);
        return extractAirQualityInfosFrom(jsonOfAirQualityInfos, stationName);
    }

    public ForecastInfos getForecastInfos() throws IOException {
        String jsonOfForecastInfos = getJsonOfForecastInfos();
        return extractForecastInfosFrom(jsonOfForecastInfos);
    }

    private ForecastInfos extractForecastInfosFrom(String jsonOfForecastInfos) throws IOException {
        JsonNode root = mapper.readTree(jsonOfForecastInfos);
        JsonNode forecastInfos = root.required("list");
        JsonNode forecastOfToday = forecastInfos.required(0);
        JsonNode forecastOfTomorrow = forecastInfos.required(1);
        URL gifUrl = new URL(forecastOfToday.required("imageUrl7").asText());
        storeGif(gifUrl);
        convertGifToPng();
        return initializeForecastInfos(forecastOfToday, forecastOfTomorrow);
    }

    private ForecastInfos initializeForecastInfos(JsonNode forecastOfToday, JsonNode forecastOfTomorrow) {
        List<ForecastInfo> forecastInfos = new ArrayList<>();
        int now = ZonedDateTime.now(ZoneId.of("Asia/Seoul")).getHour();
        addForecastInfosOfToday(forecastOfToday, forecastInfos, now);
        addForecastInfosOfTomorrow(forecastOfTomorrow, forecastInfos, now + 24);
        return new ForecastInfos(forecastInfos);
    }

    private void addForecastInfosOfToday(JsonNode forecastOfToday, List<ForecastInfo> forecastInfos, int start) {
        for (int hour = start; hour < 24; hour++) {
            ForecastInfo forecastInfo = new ForecastInfo(hour, forecastOfToday);
            forecastInfos.add(forecastInfo);
        }
    }

    private void addForecastInfosOfTomorrow(JsonNode forecastOfTomorrow, List<ForecastInfo> forecastInfos, int end) {
        for (int hour = 24; hour < end; hour++) {
            ForecastInfo forecastInfo = new ForecastInfo(hour, forecastOfTomorrow);
            forecastInfos.add(forecastInfo);
        }
    }

    private void storeGif(URL url) throws IOException {
        ReadableByteChannel readableByteChannel = Channels.newChannel(url.openStream());
        FileOutputStream fileOutputStream = new FileOutputStream("src/main/resources/static/forecast.gif");
        FileChannel fileChannel = fileOutputStream.getChannel();
        fileChannel.transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
    }

    private void convertGifToPng() throws IOException {
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command("convert", "-coalesce",
                "src/main/resources/static/forecast.gif",
                "src/main/resources/static/forecast.png");
        processBuilder.start();
    }

    private void buildClient() {
        DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory("http://openapi.airkorea.or.kr");
        factory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.NONE);
        client = WebClient
                .builder()
                .uriBuilderFactory(factory)
                .build();
    }

    private void buildDefaultQueryParams() {
        defaultQueryParams = UriComponentsBuilder
                .newInstance()
                .queryParam("serviceKey", "Sqkl%2BZxF5Bk%2FcPZMRIeBZqnUN%2BKxjShCEDlNr%2BgW3bZdNylMWwNDzPt21NuqDJcP1D4h7GxPrCT10W1mIRxapA%3D%3D")
                .queryParam("_returnType", "json")
                .build()
                .getQueryParams();
    }

    private String getJsonOfNearestStationsFrom(Coordinates tmCoordinates) {
        double tmX = tmCoordinates.getX();
        double tmY = tmCoordinates.getY();
        return client
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/openapi/services/rest/MsrstnInfoInqireSvc/getNearbyMsrstnList")
                        .queryParams(defaultQueryParams)
                        .queryParam("tmX", tmX)
                        .queryParam("tmY", tmY)
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    private String getJsonOfAirQualityInfosOf(String stationName) {
        return client
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/openapi/services/rest/ArpltnInforInqireSvc/getMsrstnAcctoRltmMesureDnsty")
                        .queryParams(defaultQueryParams)
                        .queryParam("numOfRows", "24")
                        .queryParam("pageNo", "1")
                        .queryParam("stationName", stationName)
                        .queryParam("dataTerm", "DAILY")
                        .queryParam("ver", "1.3")
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    private String getJsonOfForecastInfos() {
        return client
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/openapi/services/rest/ArpltnInforInqireSvc/getMinuDustFrcstDspth")
                        .queryParams(defaultQueryParams)
                        .queryParam("searchDate", LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                        .queryParam("informCode", "PM10")
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    private String extractStationNameFrom(String jsonOfNearestStations) throws JsonProcessingException {
        JsonNode root = mapper.readTree(jsonOfNearestStations);
        JsonNode nearestStations = root.required("list");
        JsonNode nearestStation = nearestStations.required(0); // sorted
        return nearestStation.required("stationName").asText();
    }

    private AirQualityInfos extractAirQualityInfosFrom(String jsonOfAirQualityInfos,
                                                       String stationName) throws JsonProcessingException {
        JsonNode root = mapper.readTree(jsonOfAirQualityInfos);
        JsonNode nodeOfAirQualityInfos = root.required("list");

        List<AirQualityInfo> airQualityInfos = new ArrayList<>();
        for (JsonNode nodeOfAirQualityInfo : nodeOfAirQualityInfos) {
            AirQualityInfo airQualityInfo = extractAirQualityInfoFrom(nodeOfAirQualityInfo, stationName);
            airQualityInfos.add(airQualityInfo);
        }
        return new AirQualityInfos(airQualityInfos);
    }

    private AirQualityInfo extractAirQualityInfoFrom(JsonNode nodeOfAirQualityInfo, String stationName) {
        AirQualityInfo airQualityInfo = new AirQualityInfo();
        int pm10 = parsePM10Value(nodeOfAirQualityInfo);
        String airQualityIndex = determineAirQualityIndex(pm10, nodeOfAirQualityInfo);
        if (airQualityIndex.equals("노답")) {
            pm10 = 0;
        }
        airQualityInfo.setPM10(pm10);
        airQualityInfo.setAirQualityIndex(airQualityIndex);
        airQualityInfo.setCurrentTime(nodeOfAirQualityInfo.required("dataTime").asText());
        airQualityInfo.setLocation(stationName);
        return airQualityInfo;
    }

    private int parsePM10Value(JsonNode jsonNode) {
        int pm10 = jsonNode.required("pm10Value").asInt(-1);
        if (pm10 == -1) { // if pm10Value is not present, try again with pm10Value24
            pm10 = jsonNode.required("pm10Value24").asInt(-1);
        }
        return pm10;
    }

    private String determineAirQualityIndex(int pm10, JsonNode nodeOfAirQualityInfo) {
        String airQualityIndex;
        if (pm10 != -1) {
            airQualityIndex = determineAirQualityIndexWith(pm10);
        } else {
            airQualityIndex = determineAirQualityIndexWith(nodeOfAirQualityInfo);
        }
        return airQualityIndex;
    }

    private String determineAirQualityIndexWith(int pm10) {
        String airQualityIndex;
        if (0 <= pm10 && pm10 < 30) {
            airQualityIndex = "좋음";
        } else if (30 <= pm10 && pm10 < 80) {
            airQualityIndex = "보통";
        } else if (80 <= pm10 && pm10 < 150) {
            airQualityIndex = "나쁨";
        } else { // 150 <= pm10
            airQualityIndex = "매우 나쁨";
        }
        return airQualityIndex;
    }

    private String determineAirQualityIndexWith(JsonNode nodeOfAirQualityInfo) {
        String airQualityIndex;
        String pm10grade1h = nodeOfAirQualityInfo.required("pm10Grade1h").asText();
        airQualityIndex = pm10grade1h;
        if (pm10grade1h.isEmpty()) {
            String pm10grade = nodeOfAirQualityInfo.required("pm10Grade").asText();
            airQualityIndex = pm10grade;
            if (pm10grade.isEmpty()) {
                airQualityIndex = "노답";
            }
        }
        return airQualityIndex;
    }
}
