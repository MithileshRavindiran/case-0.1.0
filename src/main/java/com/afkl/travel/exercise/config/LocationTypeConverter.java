package com.afkl.travel.exercise.config;

import com.afkl.travel.excercise.model.LocationType;
import com.afkl.travel.exercise.exception.LocationApplicationBadRequestException;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class LocationTypeConverter implements Converter<String, LocationType> {
    @Override
    public LocationType convert(String source) {
        try {
            return LocationType.valueOf(source.toUpperCase());
        } catch (IllegalArgumentException ex) {
            // Handle invalid or unsupported roles
            throw new LocationApplicationBadRequestException("Invalid Location Type: " + source);
        }
    }
}
