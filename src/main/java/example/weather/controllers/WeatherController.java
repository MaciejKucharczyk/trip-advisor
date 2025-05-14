package example.weather.controllers;

import example.weather.models.Weather;
import example.weather.services.ApiKeyService;
import example.weather.services.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.springframework.stereotype.Controller;

@Controller
public class WeatherController {

    @Autowired
    private WeatherService weatherService;

    private String selectCity(String city){
        if(city.equals("Breslau")){
            return "Wrocław";
        }

        if(city.equals("Warsaw")){
            return "Warszawa";
        }

        if(city.equals("Poznan")){
            return "Poznań";
        }

        if(city.equals("Cracow")){
            return "Kraków";
        }

        if(city.equals("Lodz")){
            return "Łódź";
        }

        return "0";
    }

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

    @GetMapping("/data")
    public ModelAndView getAPIdata(@RequestParam String city){

        Map<String, Weather> weatherForecast = new HashMap<>();
        weatherForecast = weatherService.setParameters(city);

        String cityPl = selectCity(city);
        if(!cityPl.equals("0")){
            System.out.println(city);
            city = cityPl;
        }

        ModelAndView modelAndVIew = new ModelAndView("weather");

        int day_num = 0;
        for (String key: weatherForecast.keySet()){
            String day = "day"+day_num;
            modelAndVIew.addObject(day, key);
            Weather weather = weatherForecast.get(key);
            modelAndVIew.addObject("city", weather.getCity());
            modelAndVIew.addObject("maxTemp"+day_num, weather.getMaxTemp());
            modelAndVIew.addObject("minTemp"+day_num, weather.getMinTemp());
            modelAndVIew.addObject("avgTemp"+day_num, weather.getAvgTemp());
            modelAndVIew.addObject("maxWindSpeed"+day_num, weather.getMaxWindSpeed());
            modelAndVIew.addObject("precipitation"+day_num, weather.getPrecipitation());
            modelAndVIew.addObject("avgHumidity"+day_num, weather.getAvgHumidity());
            modelAndVIew.addObject("avgVisibility"+day_num, weather.getAvgVisibility());
            modelAndVIew.addObject("UVindex"+day_num, weather.getUVindex());
            day_num++;

        }

        return modelAndVIew;
    }
}
