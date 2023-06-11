package com.afkl.travel.exercise.controller;

import com.afkl.travel.excercise.api.LocationsApi;
import com.afkl.travel.excercise.model.Location;
import com.afkl.travel.excercise.model.LocationType;
import com.afkl.travel.exercise.service.LocationService;
import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.afkl.travel.exercise.controller.LocationsController.BASE_PATH;

@RestController
@Slf4j
@RequestMapping(BASE_PATH)
public class LocationsController implements LocationsApi {

    public static final String BASE_PATH = "/travel/locations";
    public static final String FETCH_TYPE_CODE = "{type}/{code}";

    private final LocationService locationService;


    @Autowired
    public LocationsController(LocationService locationService) {
        this.locationService = locationService;
    }

    @Override
    @GetMapping(path=FETCH_TYPE_CODE, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Location> getLocationByTypeAndCode(@PathVariable("type") LocationType type, @PathVariable("code") String code, String acceptLanguage) {
        log.info("Inside fetch locations by code and type");
        return ResponseEntity.ok(locationService.getLocationsByCodeAndType(code.toUpperCase(), type.getValue(), acceptLanguage.toUpperCase()));
    }

    @SneakyThrows
    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Location>> getLocations(String acceptLanguage) {
        log.info("Inside fetch locations");
        return ResponseEntity.ok(locationService.getAllLocations(acceptLanguage.toUpperCase()));

    }
}
