package com.afkl.travel.exercise.controller;

import com.afkl.travel.exercise.exception.LocationApplicationNotFoundException;
import com.afkl.travel.exercise.service.LocationService;
import com.afkl.travel.exercise.util.TestHelperUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@WebMvcTest(controllers = LocationsController.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class})
public class LocationsControllerTest {

    @MockBean
    private LocationService locationService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetAllLocations() throws Exception {
        when(locationService.getAllLocations(anyString())).thenReturn(TestHelperUtil.createLocation());
        mockMvc.perform(MockMvcRequestBuilders.get("/travel/locations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.ACCEPT_LANGUAGE, "EN"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].code").isNotEmpty());
    }

    @Test
    public void testGetAllLocations_ThrowsNotFoundException() throws Exception {
        doThrow(new LocationApplicationNotFoundException("Record not found"))
                .when(locationService).getAllLocations(anyString());
        mockMvc.perform(MockMvcRequestBuilders.get("/travel/locations")
                .header(HttpHeaders.ACCEPT_LANGUAGE, "FR")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testGetAllLocationsByCodeType() throws Exception {
        when(locationService.getLocationsByCodeAndType(anyString(), anyString(), anyString())).thenReturn(TestHelperUtil.createLocation().get(0));
        mockMvc.perform(MockMvcRequestBuilders.get("/travel/locations/country/bve")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.ACCEPT_LANGUAGE, "EN"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").isNotEmpty());
    }

    @Test
    public void testGetAllLocationsByCodeTypeThrowsBadRequestExceptionWhenLocationTypeIsNotMatched() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/travel/locations/country1/bve")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.ACCEPT_LANGUAGE, "EN"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void testGetAllLocationsByCodeTypeThrowsNotFoundExceptionWhenNoLocationBeingReturned() throws Exception {
        doThrow(new LocationApplicationNotFoundException("Record Not Found")).when(locationService).getLocationsByCodeAndType(anyString(), anyString(), anyString());
        mockMvc.perform(MockMvcRequestBuilders.get("/travel/locations/country/bve")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.ACCEPT_LANGUAGE, "EN"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

}
