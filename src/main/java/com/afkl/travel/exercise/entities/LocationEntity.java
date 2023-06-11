package com.afkl.travel.exercise.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "location")
public class LocationEntity {

    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "latitude")
    private Double latitude;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    @ToString.Exclude
    private LocationEntity parent;

//    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY)
//    private List<LocationEntity> children;

    @OneToMany(mappedBy = "locationEntity", fetch = FetchType.EAGER)
    private List<TranslationEntity> translations;

    @Override
    public String toString() {
        return "Location{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", type='" + type + '\'' +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, code, type, longitude, latitude, parent, translations);
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        LocationEntity that = (LocationEntity) o;
        return id != null && Objects.equals(id, that.id);
    }


}


