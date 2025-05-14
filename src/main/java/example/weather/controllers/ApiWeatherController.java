package example.weather.controllers;

import example.weather.services.ApiKeyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

@RestController
public class ApiWeatherController {

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Autowired
    private ApiKeyService apiKeyService;

    @GetMapping("/api/data")
    public Mono<String> getAPIdata(@RequestParam String city){
        String apiKey = apiKeyService.getAPIkey("src/main/API-key.txt");
        String days = "3";
        String baseUrl = "http://api.weatherapi.com/v1/forecast.json?";

        String url = UriComponentsBuilder.fromHttpUrl(baseUrl)
                .queryParam("key", apiKey)
                .queryParam("q", city)
                .queryParam("days", days)
                .toUriString();

        Mono<String> response = webClientBuilder.build()
                .get()
                .uri(url)
                .retrieve()
                .bodyToMono(String.class);
        

        return webClientBuilder.build()
                .get()
                .uri(url)
                .retrieve()
                .bodyToMono(String.class);
    }


}
