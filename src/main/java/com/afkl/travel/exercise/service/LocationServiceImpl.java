package com.afkl.travel.exercise.service;

import com.afkl.travel.excercise.model.Location;
import com.afkl.travel.exercise.entities.LocationEntity;
import com.afkl.travel.exercise.exception.LocationApplicationBadRequestException;
import com.afkl.travel.exercise.exception.LocationApplicationNotFoundException;
import com.afkl.travel.exercise.mapper.LocationMapper;
import com.afkl.travel.exercise.repository.LocationEntityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.actuate.metrics.MetricsEndpoint;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {

    private final LocationEntityRepository locationEntityRepository;

    private final LocationMapper locationMapper;

    @Override
    public List<Location> getAllLocations(String language) {
        log.info("Inside fetch locations");
        var locationList = locationEntityRepository.findAllByTranslationsLanguage(language.toUpperCase());
        log.info("Fetched Locations Size {}", locationList.size());
        if (locationList.isEmpty()) {
            log.error("No Locations found for the requested language {}",language);
            throw new LocationApplicationBadRequestException("No Locations found for the requested language");
        }
        return locationList.stream()
                .map(locationMapper::locationEntityToLocationModel)
                .collect(Collectors.toList());
    }

    @Override
    public Location getLocationsByCodeAndType(String code, String type, String language) {
        var locationEntity = locationEntityRepository.findLocationsByCodeAndTypeAndTranslationsLanguage(code, type, language.toUpperCase());
        if (locationEntity == null) {
         log.error("No Matched Location for the Request Code: {} Type: {} and Language: {}", code, type, language);
         throw new LocationApplicationNotFoundException("No Matched Location for the Request Code, Type and Language");
        }
        return locationMapper.locationEntityToLocationModel(locationEntity);
    }
}
