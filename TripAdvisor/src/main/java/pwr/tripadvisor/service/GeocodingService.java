package pwr.tripadvisor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import pwr.tripadvisor.model.GeocodingResponse;
import pwr.tripadvisor.model.LocationInfo;
import pwr.tripadvisor.model.LocationRequest;

import java.util.List;
import java.util.Optional;

@Service
public class GeocodingService {
    private final RestTemplate restTemplate;
    private final String geocodeUrl;
    private final String geocodeKey;

    public GeocodingService(RestTemplate restTemplate,
                            @Value("${geocoding.api.url}") String geocodeUrl,
                            @Value("${geocoding.api.key}") String geocodeKey) {
        this.restTemplate = restTemplate;
        this.geocodeUrl   = geocodeUrl;
        this.geocodeKey   = geocodeKey;
    }

    public LocationInfo geocode(LocationRequest req) {
        // 1) zbuduj pełny URL
        String address = req.getStreet()
                + (req.getHouseNumber() != null && !req.getHouseNumber().isEmpty()
                ? " " + req.getHouseNumber() : "")
                + ", " + req.getCity();

        String uri = UriComponentsBuilder
                .fromHttpUrl(geocodeUrl)
                .queryParam("address", address)
                .queryParam("key", geocodeKey)
                .toUriString();

        // 2) pobierz odpowiedź z API
        GeocodingResponse resp = restTemplate
                .getForObject(uri, GeocodingResponse.class);

        // 3) zweryfikuj i zmapuj na DTO
        if (resp == null || !"OK".equals(resp.getStatus()) || resp.getResults().isEmpty()) {
            throw new RuntimeException("Geocoding failed: " + (resp != null ? resp.getStatus() : "null response"));
        }
        GeocodingResponse.Result r = resp.getResults().get(0);

        // 4) stwórz DTO LocationInfo
        LocationInfo info = new LocationInfo();
        info.setCity(req.getCity());
        info.setStreet(req.getStreet());
        info.setHouseNumber(req.getHouseNumber());

        // wyciągnięcie kodu pocztowego
        String postal = Optional.ofNullable(r.getAddressComponents()).orElseGet(List::of)
                .stream()
                .filter(c -> c.getTypes().contains("postal_code"))
                .findFirst()
                .map(GeocodingResponse.AddressComponent::getLongName)
                .orElse("");
        info.setPostalCode(postal);

        // współrzędne
        info.setLat(r.getGeometry().getLocation().getLat());
        info.setLon(r.getGeometry().getLocation().getLng());

        return info;
    }
}
