package com.horseracing.project3.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "RaceTrack")
public class RaceTrack {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name", columnDefinition = "NVARCHAR(255)", nullable = false)
    private String name;

    @Column(name = "location", columnDefinition = "NVARCHAR(255)", nullable = false)
    private String location;

    @Column(name = "surface_type", columnDefinition = "NVARCHAR(100)")
    private String surfaceType;

    @Column(name = "length_meters")
    private Integer lengthMeters;

    @Column(name = "description", columnDefinition = "NVARCHAR(MAX)")
    private String description;

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getSurfaceType() {
        return surfaceType;
    }

    public void setSurfaceType(String surfaceType) {
        this.surfaceType = surfaceType;
    }

    public Integer getLengthMeters() {
        return lengthMeters;
    }

    public void setLengthMeters(Integer lengthMeters) {
        this.lengthMeters = lengthMeters;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
