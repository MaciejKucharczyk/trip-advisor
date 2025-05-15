package pwr.tripadvisor.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class WeatherApiResponse {

    private LocationData location;
    private Forecast forecast;

    public LocationData getLocation() { return location; }
    public void setLocation(LocationData location) { this.location = location; }

    public Forecast getForecast() { return forecast; }
    public void setForecast(Forecast forecast) { this.forecast = forecast; }

    public static class LocationData {
        private String name;
        private String country;
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public String getCountry() { return country; }
        public void setCountry(String country) { this.country = country; }
    }

    public static class Forecast {
        /**
         * Pole nazywamy forecastDay, ale dzięki tej adnotacji
         * Jackson przypisze JSON-owy klucz "forecastday"
         */
        @JsonProperty("forecastday")
        private List<Day> forecastDay;

        public List<Day> getForecastDay() { return forecastDay; }
        public void setForecastDay(List<Day> forecastDay) { this.forecastDay = forecastDay; }

        public static class Day {
            private DayInfo day;
            public DayInfo getDay() { return day; }
            public void setDay(DayInfo day) { this.day = day; }

            public static class DayInfo {
                /** avgtemp_c → avgTempC */
                @JsonProperty("avgtemp_c")
                private double avgTempC;
                /** wilgotność */
                @JsonProperty("avghumidity")
                private double humidity;
                /** cała sekcja "condition" */
                @JsonProperty("condition")
                private Condition condition;

                public double getAvgTempC() { return avgTempC; }
                public void setAvgTempC(double avgTempC) { this.avgTempC = avgTempC; }

                public double getHumidity() { return humidity; }
                public void setHumidity(double humidity) { this.humidity = humidity; }

                public Condition getCondition() { return condition; }
                public void setCondition(Condition condition) { this.condition = condition; }

                public static class Condition {
                    private String text;
                    private String icon;
                    public String getText() { return text; }
                    public void setText(String text) { this.text = text; }
                    public String getIcon() { return icon; }
                    public void setIcon(String icon) { this.icon = icon; }
                }
            }
        }
    }
}
