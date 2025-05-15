package pwr.tripadvisor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import pwr.tripadvisor.model.WeatherApiResponse;
import pwr.tripadvisor.model.WeatherInfo;

@Service
public class WeatherService {
    private final RestTemplate restTemplate;
    private final String weatherUrl;
    private final String weatherKey;

    // konstruktorowa forma wstrzykiwania
    public WeatherService(RestTemplate restTemplate,
                          @Value("${weather.api.url}") String weatherUrl,
                          @Value("${weather.api.key}") String weatherKey) {
        this.restTemplate = restTemplate;
        this.weatherUrl    = weatherUrl;
        this.weatherKey    = weatherKey;
    }

    public WeatherInfo getCurrentWeather(double lat, double lon) {
        String uri = UriComponentsBuilder
                .fromHttpUrl(weatherUrl)
                .queryParam("key", weatherKey)
                .queryParam("q", lat + "," + lon)  // <— tu lat,lon zamiast nazwy
                .queryParam("days", 1)
                .queryParam("aqi", "no")
                .toUriString();

        // tu używamy restTemplate, nie "rest"
        WeatherApiResponse resp = restTemplate
                .getForObject(uri, WeatherApiResponse.class);

        // mapujemy na nasz DTO
        return mapToWeatherInfo(resp);
    }

    // helper który wyciąga interesujące nas pola i tworzy WeatherInfo
    private WeatherInfo mapToWeatherInfo(WeatherApiResponse resp) {
        if (resp == null || resp.getForecast() == null
                || resp.getForecast().getForecastDay().isEmpty()) {
            throw new RuntimeException("Weather API returned no data");
        }

        var dayInfo = resp.getForecast()
                .getForecastDay()
                .get(0)
                .getDay();

        WeatherInfo weatherInfo = new WeatherInfo();
        weatherInfo.setTemperature(dayInfo.getAvgTempC());
        weatherInfo.setDescription(dayInfo.getCondition().getText());
        weatherInfo.setHumidity(dayInfo.getHumidity());
        weatherInfo.setIconUrl("https:" + dayInfo.getCondition().getIcon());
        return weatherInfo;
    }
}
