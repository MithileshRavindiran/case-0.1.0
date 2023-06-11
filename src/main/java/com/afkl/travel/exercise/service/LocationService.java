package com.afkl.travel.exercise.service;

import com.afkl.travel.excercise.model.Location;

import java.util.List;

public interface LocationService {

    List<Location> getAllLocations(String language);

    Location getLocationsByCodeAndType(String code, String type, String language);

}
