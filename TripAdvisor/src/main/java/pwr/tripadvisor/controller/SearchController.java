package pwr.tripadvisor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.HttpClientErrorException;
import pwr.tripadvisor.model.LocationInfo;
import pwr.tripadvisor.model.LocationRequest;
import pwr.tripadvisor.model.WeatherInfo;
import pwr.tripadvisor.service.GeocodingService;
import pwr.tripadvisor.service.WeatherService;

@Controller
public class SearchController {
    @Autowired
    private GeocodingService geo;
    @Autowired private WeatherService weather;

    @GetMapping("/")
    public String form(Model model) {
        model.addAttribute("request", new LocationRequest());
        return "search";
    }

    @PostMapping("/search")
    public String search(@ModelAttribute LocationRequest request, Model model) {
        LocationInfo info = geo.geocode(request);
        model.addAttribute("locationInfo", info);
        try{
            WeatherInfo weatherInfo = weather.getCurrentWeather(info.getLat(),
                    info.getLon());

            model.addAttribute("weatherInfo", weatherInfo);
        } catch (HttpClientErrorException ex) {
            // wyciągamy błąd z response body lub sam kod
            String msg = "Brak danych pogodowych dla: " + info.getCity();
            model.addAttribute("weatherError", msg);
        }

        return "result";
    }
}
