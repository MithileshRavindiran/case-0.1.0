package com.afkl.travel.exercise.service;

import com.afkl.travel.excercise.model.Location;
import com.afkl.travel.exercise.entities.LocationEntity;
import com.afkl.travel.exercise.exception.LocationApplicationBadRequestException;
import com.afkl.travel.exercise.exception.LocationApplicationNotFoundException;
import com.afkl.travel.exercise.mapper.LocationMapper;
import com.afkl.travel.exercise.repository.LocationEntityRepository;
import com.afkl.travel.exercise.util.TestHelperUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

class LocationServiceImplTest {

    @InjectMocks
    private LocationServiceImpl locationService;

    @Mock
    private LocationEntityRepository locationEntityRepository;

    @Mock
    private LocationMapper locationMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllLocations() {

        var expectedLocations = TestHelperUtil.createLocation();
        // Mocking repository behavior
        when(locationEntityRepository.findAllByTranslationsLanguage(anyString()))
                .thenReturn(TestHelperUtil.locationEntities());
        when(locationMapper.locationEntityToLocationModel(any(LocationEntity.class)))
                .thenReturn(TestHelperUtil.createLocation().get(0), TestHelperUtil.createLocation().get(1));

        // Call the method
        List<Location> actualLocations = locationService.getAllLocations("en");

        // Verify the results
        assertEquals(expectedLocations, actualLocations);
        verify(locationEntityRepository).findAllByTranslationsLanguage("EN");
        verify(locationMapper, times(2)).locationEntityToLocationModel(any(LocationEntity.class));
    }

    @Test
    void testGetLocationsByCodeAndType() {

        // Mocking repository behavior
        when(locationEntityRepository.findLocationsByCodeAndTypeAndTranslationsLanguage("AMS", "city", "EN"))
                .thenReturn(TestHelperUtil.locationEntities().get(1));
        when(locationMapper.locationEntityToLocationModel(any(LocationEntity.class)))
                .thenReturn(TestHelperUtil.createLocation().get(1));

        // Call the method
        Location actualLocation = locationService.getLocationsByCodeAndType("AMS", "city", "en");

        // Verify the results
        assertEquals(TestHelperUtil.createLocation().get(1), actualLocation);
        verify(locationEntityRepository).findLocationsByCodeAndTypeAndTranslationsLanguage("AMS", "city", "EN");
        verify(locationMapper).locationEntityToLocationModel(TestHelperUtil.locationEntities().get(1));
    }

    @Test
    void testGetLocationsByCodeAndType_ThrowsNotFoundException() {
        // Mocking repository behavior
        when(locationEntityRepository.findLocationsByCodeAndTypeAndTranslationsLanguage(anyString(), anyString(), anyString()))
                .thenReturn(null);

        // Call the method and verify that it throws an exception
        assertThrows(LocationApplicationNotFoundException.class,
                () -> locationService.getLocationsByCodeAndType("AMS", "city", "en"));

        verify(locationEntityRepository).findLocationsByCodeAndTypeAndTranslationsLanguage("AMS", "city", "EN");
        verifyNoInteractions(locationMapper);
    }
}
