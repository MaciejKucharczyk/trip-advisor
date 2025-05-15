package pwr.tripadvisor.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class GeocodingResponse {
    private String status;
    private List<Result> results;
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }


    public static class Result {
        @JsonProperty("formatted_address")
        private String formattedAddress;
        @JsonProperty("address_components")
        private List<AddressComponent> addressComponents;

        private Geometry geometry;

        public String getFormattedAddress() {
            return formattedAddress;
        }

        public void setFormattedAddress(String formattedAddress) {
            this.formattedAddress = formattedAddress;
        }

        public List<AddressComponent> getAddressComponents() {
            return addressComponents;
        }

        public void setAddressComponents(List<AddressComponent> addressComponents) {
            this.addressComponents = addressComponents;
        }

        public Geometry getGeometry() {
            return geometry;
        }

        public void setGeometry(Geometry geometry) {
            this.geometry = geometry;
        }


    }
    public static class AddressComponent {
        @JsonProperty("long_name")
        private String longName;

        private List<String> types;

        public String getLongName() {
            return longName;
        }

        public void setLongName(String longName) {
            this.longName = longName;
        }

        public List<String> getTypes() {
            return types;
        }

        public void setTypes(List<String> types) {
            this.types = types;
        }
    }
    public static class Geometry {

        private Location location;
        public Location getLocation() {
            return location;
        }

        public void setLocation(Location location) {
            this.location = location;
        }


    }
    public static class Location {
        private double lat;
        @JsonProperty("lng")
        private double lng;

        public double getLat() {
            return lat;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }

        public double getLng() {
            return lng;
        }

        public void setLng(double lng) {
            this.lng = lng;
        }

    }
}
