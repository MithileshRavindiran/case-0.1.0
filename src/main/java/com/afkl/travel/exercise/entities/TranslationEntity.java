package com.afkl.travel.exercise.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Objects;

@Getter
@Setter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "translation")
public class TranslationEntity {

    @Id
    @Column(name = "id")
    private Integer id;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location", referencedColumnName = "id")
    private LocationEntity locationEntity;

    @Column(name = "language", nullable = false)
    private String language;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @Override
    public String toString() {
        return "Translation{" +
                "id=" + id +
                ", location=" + locationEntity.getId() +
                ", language='" + language + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TranslationEntity that = (TranslationEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(locationEntity, that.locationEntity) &&
                Objects.equals(language, that.language) &&
                Objects.equals(name, that.name) &&
                Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, locationEntity, language, name, description);
    }

}