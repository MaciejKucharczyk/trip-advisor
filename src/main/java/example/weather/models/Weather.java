package example.weather.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Weather {

    private String city;

    private String date;

    private double maxTemp;

    private double minTemp;

    private double avgTemp;

    private double maxWindSpeed;

    private int precipitation;

    private int snowfall;

    private double avgHumidity;

    private int avgVisibility;

    private double UVindex;

    public Weather(){}

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) { this.date = date; }



    public double getMaxTemp() {
        return maxTemp;
    }

    public void setMaxTemp(double maxTemp) {
        this.maxTemp = maxTemp;
    }

    public double getMinTemp() {
        return minTemp;
    }

    public void setMinTemp(double minTemp) {
        this.minTemp = minTemp;
    }

    public double getAvgTemp() {
        return avgTemp;
    }

    public void setAvgTemp(double avgTemp) {
        this.avgTemp = avgTemp;
    }

    public double getMaxWindSpeed() {
        return maxWindSpeed;
    }

    public void setMaxWindSpeed(double maxWindSpeed) {
        this.maxWindSpeed = maxWindSpeed;
    }

    public int getPrecipitation() {
        return precipitation;
    }

    public void setPrecipitation(int precipitation) {
        this.precipitation = precipitation;
    }

    public int getSnowfall() {
        return snowfall;
    }

    public void setSnowfall(int snowfall) {
        this.snowfall = snowfall;
    }

    public double getAvgHumidity() {
        return avgHumidity;
    }

    public void setAvgHumidity(double avgHumidity) {
        this.avgHumidity = avgHumidity;
    }

    public int getAvgVisibility() {
        return avgVisibility;
    }

    public void setAvgVisibility(int avgVisibility) {
        this.avgVisibility = avgVisibility;
    }

    public double getUVindex() {
        return UVindex;
    }

    public void setUVindex(double UVindex) {
        this.UVindex = UVindex;
    }

}

/*    private String city;

    private ArrayList<String> days;

    Weather(String city, ArrayList<String> days){
        this.city = city;
        this.days = days;
    }

    public String getCity() {
        return city;
    }

    public ArrayList<String> getDays() {
        return days;
    }*/


