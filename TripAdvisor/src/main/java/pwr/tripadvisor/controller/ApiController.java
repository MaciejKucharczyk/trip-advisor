package pwr.tripadvisor.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/api")
public class ApiController {

    private final RestTemplate restTemplate;
    private final String geocodeUrl;
    private final String geocodeKey;
    private final String weatherUrl;
    private final String weatherKey;

    public ApiController(RestTemplate restTemplate,
                         @Value("${geocoding.api.url}") String geocodeUrl,
                         @Value("${geocoding.api.key}") String geocodeKey,
                         @Value("${weather.api.url}") String weatherUrl,
                         @Value("${weather.api.key}") String weatherKey) {
        this.restTemplate = restTemplate;
        this.geocodeUrl   = geocodeUrl;
        this.geocodeKey   = geocodeKey;
        this.weatherUrl   = weatherUrl;
        this.weatherKey   = weatherKey;
    }

    /**
     * Zwraca surowy JSON z Geocoding API.
     * Przykład: /api/map?city=Warszawa&street=Krakowskie%20Przedmiescie&houseNumber=3
     */
    @GetMapping(value = "/map", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> mapJson(
            @RequestParam String city,
            @RequestParam String street,
            @RequestParam(required = false) String houseNumber) {

        String address = street
                + (houseNumber != null && !houseNumber.isEmpty() ? " " + houseNumber : "")
                + ", " + city;

        String uri = UriComponentsBuilder
                .fromHttpUrl(geocodeUrl)
                .queryParam("address", address)
                .queryParam("key", geocodeKey)
                .toUriString();

        String json = restTemplate.getForObject(uri, String.class);
        return ResponseEntity.ok(json);
    }

    /**
     * Zwraca surowy JSON z Weather API.
     * Przykład: /api/weather?city=Warszawa
     */
    @GetMapping(value = "/weather", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> weatherJson(@RequestParam String city) {
        String uri = UriComponentsBuilder
                .fromHttpUrl(weatherUrl)
                .queryParam("key", weatherKey)
                .queryParam("q", city)
                .queryParam("days", 1)
                .queryParam("aqi", "no")
                .toUriString();

        String json = restTemplate.getForObject(uri, String.class);
        return ResponseEntity.ok(json);
    }
}
