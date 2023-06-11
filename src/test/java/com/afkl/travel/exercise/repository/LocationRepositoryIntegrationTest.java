package com.afkl.travel.exercise.repository;

import org.hibernate.cfg.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("it")
@TestPropertySource(locations = "classpath:application-it.properties")
public class LocationRepositoryIntegrationTest {

    @Autowired
    LocationEntityRepository locationEntityRepository;


    @Test
    void testRetriveAllLocationsOfEnglights() {
        var locationEntities = locationEntityRepository.findAllByTranslationsLanguage("EN");
        assertEquals(6,locationEntities.size());
    }
}
