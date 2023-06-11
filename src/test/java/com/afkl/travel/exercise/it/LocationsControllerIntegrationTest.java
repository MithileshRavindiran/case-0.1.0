package com.afkl.travel.exercise.it;

import com.afkl.travel.excercise.model.Location;
import com.afkl.travel.exercise.Application;
import com.afkl.travel.exercise.service.LocationService;
import com.afkl.travel.exercise.util.TestHelperUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.context.WebApplicationContext;


@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Application.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("it")
@AutoConfigureMockMvc
public class LocationsControllerIntegrationTest {

    @Autowired
    private LocationService locationService;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {

    }


    @Test
    @WithMockUser(username="dummyUser", roles = "USER")
    void givenAuthHeaderSecretIsValid_whenApiControllerCalled_thenReturnOk() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/travel/locations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.ACCEPT_LANGUAGE, "EN"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].code").isNotEmpty());

    }

    @Test
    @WithMockUser(username="dummyUser", roles = "ADMIN")
    public void testGetLocationsByCodeTypeSuccess() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/travel/locations/airport/QNQ")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.ACCEPT_LANGUAGE, "NL"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").isNotEmpty());
    }

    @Test
    @WithMockUser(username="dummyUser", roles = {"USER","ADMIN"})
    public void testGetAllLocationsByCodeTypeNotFoundRequestWithNoLocationFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/travel/locations/airport/QNQ")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.ACCEPT_LANGUAGE, "EN"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @WithMockUser(username="dummyUser", roles = {"USER","ADMIN"})
    public void testGetAllLocationsByCodeTypeBadRequestWithInvalidRequestType() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/travel/locations/airport1/QNQ")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.ACCEPT_LANGUAGE, "EN"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }


}
