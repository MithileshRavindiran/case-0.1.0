package com.afkl.travel.exercise.mapper;

import antlr.StringUtils;
import com.afkl.travel.excercise.model.Location;
import com.afkl.travel.excercise.model.LocationType;
import com.afkl.travel.exercise.entities.LocationEntity;
import com.afkl.travel.exercise.entities.TranslationEntity;
import com.afkl.travel.exercise.exception.LocationApplicationBadRequestException;
import com.afkl.travel.exercise.exception.LocationApplicationNotFoundException;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;


@Mapper(componentModel = "spring")
public interface LocationMapper {
    LocationMapper INSTANCE = Mappers.getMapper(LocationMapper.class);

    @Mapping(target="code",source ="code")
    @Mapping(target="latitude",source ="latitude")
    @Mapping(target="longitude",source ="longitude")
    @Mapping(target="name", expression="java(getName(locationEntity.getTranslations()))")
    @Mapping(target="type", expression="java(convertToEnum(locationEntity.getType()))")
    @Mapping(target = "description", expression = "java(getDescription(locationEntity.getTranslations()))")
    @Mapping(target="parentCode", expression="java(getCode(locationEntity.getParent()))")
    @Mapping(target="parentType", expression="java(getType(locationEntity.getParent()))")
    Location locationEntityToLocationModel(LocationEntity locationEntity);

    default String getName(List<TranslationEntity> translationEntityList) {
        if (translationEntityList == null || translationEntityList.isEmpty()) {
            throw new LocationApplicationNotFoundException("Translation Object for the requested Language is empty or null.");
        }
        return translationEntityList.get(0).getName();
    }

    default String getCode(LocationEntity parentLocationEntity) {
        if (parentLocationEntity == null) {
           return null;
        }
        return parentLocationEntity.getCode();
    }

    default LocationType getType(LocationEntity parentLocationEntity) {
        if (parentLocationEntity == null) {
            return null;
        }
        return LocationType.fromValue(parentLocationEntity.getType());
    }

    default String getDescription(List<TranslationEntity> translationEntityList) {
        if (translationEntityList == null || translationEntityList.isEmpty()) {
            throw new LocationApplicationNotFoundException("Translation Object for the requested Language is empty or null.");
        }
        return translationEntityList.get(0).getDescription();
    }

    default LocationType convertToEnum(String value) {
        if (value == null) {
            throw new LocationApplicationNotFoundException("Illegal/Non Matching Location Type");
        }

        // Convert the string to the corresponding enum value
        return LocationType.fromValue(value);
    }
}
