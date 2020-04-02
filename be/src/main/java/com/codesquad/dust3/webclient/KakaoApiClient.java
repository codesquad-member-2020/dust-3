package com.codesquad.dust3.webclient;

import com.codesquad.dust3.airquality.Coordinates;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;

public class KakaoApiClient {
    private static WebClient client;
    private static ObjectMapper mapper;

    public KakaoApiClient() {
        buildClient();
        mapper = new ObjectMapper();
    }

    public Coordinates getTmCoordinatesFrom(Coordinates wgsCoordinates) throws JsonProcessingException {
        String responseBody = callApiWith(wgsCoordinates);
        return extractTmCoordinatesFrom(responseBody);
    }

    private void buildClient() {
        DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory("https://dapi.kakao.com");
        factory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.NONE);
        client = WebClient
                .builder()
                .uriBuilderFactory(factory)
                .build();
    }

    private String callApiWith(Coordinates wgsCoordinates) {
        Double wgsX = wgsCoordinates.getX();
        Double wgsY = wgsCoordinates.getY();
        return client
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/v2/local/geo/transcoord.json")
                        .queryParam("x", wgsX)
                        .queryParam("y", wgsY)
                        .queryParam("output_coord", "TM")
                        .build())
                .header(HttpHeaders.AUTHORIZATION, "KakaoAK bd2d07cd58b8c12a1234578196b7fda6")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    private Coordinates extractTmCoordinatesFrom(String responseBody) throws JsonProcessingException {
        JsonNode root = mapper.readTree(responseBody);
        JsonNode documents = root.required("documents");
        JsonNode coordinates = documents.required(0); // single element array
        double x = coordinates.required("x").asDouble();
        double y = coordinates.required("y").asDouble();
        return new Coordinates(x, y);
    }
}
