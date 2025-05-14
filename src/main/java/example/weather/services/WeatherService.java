package example.weather.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import example.weather.WeatherApplication;
import example.weather.models.Weather;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;

@Service
public class WeatherService {
    private String getAPIkey(String pathToFile) {
        String APIkey = null;
        try {
            File APIfile = new File(pathToFile);
            Scanner myReader = new Scanner(APIfile);
            APIkey = myReader.nextLine();
            System.out.println(APIkey);
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        return APIkey;
    }

    private String getUrl(String city){
        String APIkey = getAPIkey("src/main/API-key.txt");
        String days = "4"; // current day + next 3 daysŻmigrodzka, Wrocław
        String baseUrl = "http://api.weatherapi.com/v1/forecast.json?";
        String url = UriComponentsBuilder.fromHttpUrl(baseUrl)
                .queryParam("key", APIkey)
                .queryParam("q", city)
                .queryParam("days", days)
                .toUriString();

        return url;
    }
    public Map<String, Weather> setParameters(String city){
        Map<String, Weather> days_m = new HashMap<>();

        // URL API
        String apiUrl = getUrl(city);

        // HTTP client
        HttpClient client = HttpClient.newHttpClient();

        // HTTP task
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(apiUrl))
                .build();

        // Get a response
        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenAccept(response -> {
                    try {
                        // Convert to JSON
                        ObjectMapper objectMapper = new ObjectMapper();
                        JsonNode rootNode = objectMapper.readTree(response);

                        JsonNode locationNode = rootNode.path("location");
                        String locationName = locationNode.path("name").asText();

                        // Go to "forecast" -> "forecastday"
                        JsonNode forecastDays = rootNode.path("forecast").path("forecastday");

                        // get data for each day
                        for (JsonNode dayNode : forecastDays) {
                            Weather weather = new Weather();

                            String date = dayNode.path("date").asText();
                            double maxTemp = dayNode.path("day").path("maxtemp_c").asDouble();
                            double minTemp = dayNode.path("day").path("mintemp_c").asDouble();
                            double avgTemp = dayNode.path("day").path("avgtemp_c").asDouble();
                            double maxWindSpeed = dayNode.path("day").path("maxwind_kph").asDouble();
                            int precipitation = dayNode.path("day").path("totalprecip_mm").asInt();
                            int snowfall = dayNode.path("day").path("totalsnow_cm").asInt();
                            double avgHumidity = dayNode.path("day").path("avghumidity").asDouble();
                            int avgVisibility = dayNode.path("day").path("avgvis_km").asInt();
                            double UVindex = dayNode.path("day").path("uv").asDouble();

                            weather.setCity(locationName);
                            weather.setDate(date);
                            weather.setMaxTemp(maxTemp);
                            weather.setMinTemp(minTemp);
                            weather.setAvgTemp(avgTemp);
                            weather.setMaxWindSpeed(maxWindSpeed);
                            weather.setPrecipitation(precipitation);
                            weather.setSnowfall(snowfall);
                            weather.setAvgHumidity(avgHumidity);
                            weather.setAvgVisibility(avgVisibility);
                            weather.setUVindex(UVindex);

                            days_m.put(date, weather);

                            System.out.println("Date: " + date);
                            System.out.println("City: " + locationName);
                            System.out.println("Max Temp (C): " + maxTemp);
                            System.out.println("Min Temp (C): " + minTemp);
                            System.out.println("----------------------------");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                })
                .join(); // wait for the end of the task

        return days_m;
    }
}
