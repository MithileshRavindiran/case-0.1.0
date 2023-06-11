package com.afkl.travel.exercise.repository;

import com.afkl.travel.exercise.entities.LocationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LocationEntityRepository extends JpaRepository<LocationEntity, Integer> {

    List<LocationEntity> findAllByTranslationsLanguage(@Param("language") String language);

    LocationEntity findLocationsByCodeAndTypeAndTranslationsLanguage(@Param("code") String code, @Param("type") String type, @Param("language") String language);
}
