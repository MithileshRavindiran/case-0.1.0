package com.afkl.travel.exercise.util;

import com.afkl.travel.excercise.model.Location;
import com.afkl.travel.excercise.model.LocationType;
import com.afkl.travel.exercise.entities.LocationEntity;
import com.afkl.travel.exercise.entities.TranslationEntity;
import com.afkl.travel.exercise.service.LocationService;

import java.util.Arrays;
import java.util.List;

public final class TestHelperUtil {

    public static List<LocationEntity> locationEntities() {
        var locationEntity = LocationEntity.builder()
                .id(1)
                .code("NL")
                .parent(null)
                .longitude(5.45000000000000018d)
                .latitude(52.2999999999999972d)
                .type("country")
                .translations(List.of(TranslationEntity.builder()
                                        .id(1)
                                        .description("Netherlands (NL)")
                                        .name("Netherlands")
                                        .language("EN")
                        .build(), TranslationEntity.builder()
                                .id(2)
                                .description("Nederland (NL)")
                                .name("Nederland")
                                .language("NL")
                                .build()
                        ))

                .build();
        var locationEntity1 = LocationEntity.builder()
                .id(2)
                .code("AMS")
                .parent(locationEntity)
                .longitude(4.78416999999999959d)
                .latitude(52.316670000000002d)
                .type("city")
                .translations(List.of(TranslationEntity.builder()
                        .id(1)
                        .description("Netherlands (NL)")
                        .name("Netherlands")
                        .language("EN")
                        .build(), TranslationEntity.builder()
                        .id(2)
                        .description("Nederland (NL)")
                        .name("Nederland")
                        .language("NL")
                        .build()
                ))
                .build();
        return List.of(locationEntity, locationEntity1);
    }

    public static List<Location> createLocation() {
        var location = new Location();
        location.code("NL");
        location.description("Netherlands (NL)");
        location.name("Netherlands");
        location.longitude(5.45000000000000018d);
        location.latitude(52.2999999999999972d);
        location.type(LocationType.COUNTRY);
        location.parentCode(null);
        location.parentType(null);
        var location1 = new Location();
        location1.code("AMS");
        location1.description("Netherlands (NL)");
        location1.name("Netherlands");
        location1.longitude(4.78416999999999959d);
        location1.latitude(52.316670000000002d);
        location1.type(LocationType.CITY);
        location1.parentCode("NL");
        location1.parentType(LocationType.COUNTRY);
        return List.of(location, location1);
    }
}
