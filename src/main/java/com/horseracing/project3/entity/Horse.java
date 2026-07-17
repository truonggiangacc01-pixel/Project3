package com.horseracing.project3.entity;

import com.horseracing.project3.enums.HorseHealthStatus;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Horse")
public class Horse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name", columnDefinition = "NVARCHAR(255)", nullable = false)
    private String name;

    @Column(name = "age", nullable = false)
    private Integer age;

    @Column(name = "breed", columnDefinition = "NVARCHAR(50)", nullable = false)
    private String breed;

    @Enumerated(EnumType.STRING)
    @Column(name = "health_status", nullable = false)
    private HorseHealthStatus healthStatus;

    /*___________________________________________________________________________________________________________ */

    //MAPPING MỐI QUAN HỆ 1-N (Horse - RaceParticipation)
    //1 HORSE --< có nhiều RaceParticipation - List<RaceParticipation>
    @com.fasterxml.jackson.annotation.JsonIgnore
    @OneToMany(mappedBy = "horse", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<RaceParticipation> raceParticipationList = new ArrayList<>();

    public void addRaceParticipation(RaceParticipation o){
        raceParticipationList.add(o);
        o.setHorse(this);
    }

    public void removeRaceParticipation(RaceParticipation o){
        raceParticipationList.remove(o);
        o.setHorse(null);
    }

    /*___________________________________________________________________________________________________________ */

    //MAPPING MỐI QUAN HỆ 1-N (Horse - Prediction)
    //1 HORSE --< có nhiều Prediction - List<Prediction>
    @com.fasterxml.jackson.annotation.JsonIgnore
    @OneToMany(mappedBy = "horse", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Prediction> predictionList = new ArrayList<>();

    public void addPrediction(Prediction o){
        predictionList.add(o);
        o.setHorse(this);
    }

    public void removePrediction(Prediction o){
        predictionList.remove(o);
        o.setHorse(null);
    }

    /*___________________________________________________________________________________________________________ */

    //MAPPING MỐI QUAN HỆ GIỮA HorswOwner - Horse
    //1, N HorswOwner bất kỳ phải thuộc về 1 Horse
    @ManyToOne
    @JoinColumn(name = "HorseOwnerId")
    @com.fasterxml.jackson.annotation.JsonIgnoreProperties({"horseList", "notificationList", "password"})
    private HorseOwner horseOwner;

    public HorseOwner getHorseOwner() {
        return horseOwner;
    }

    public void setHorseOwner(HorseOwner horseOwner) {
        this.horseOwner = horseOwner;
    }

    /*___________________________________________________________________________________________________________ */
    //                                                  CONSTRUCTOR RỖNG

    public Horse() {
    }

    /*___________________________________________________________________________________________________________ */
    //                                                  CONSTRUCTOR FULL

    public Horse(String name, Integer age, String breed, HorseHealthStatus healthStatus) {
        this.name = name;
        this.age = age;
        this.breed = breed;
        this.healthStatus = healthStatus;
    }

    /*___________________________________________________________________________________________________________ */
    //                                                  GETTER, SETTER

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public HorseHealthStatus getHealthStatus() {
        return healthStatus;
    }

    public void setHealthStatus(HorseHealthStatus healthStatus) {
        this.healthStatus = healthStatus;
    }

    /*___________________________________________________________________________________________________________ */
    //                                                  TO STRING

    @Override
    public String toString() {
        return "Horse{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", breed='" + breed + '\'' +
                ", healthStatus=" + healthStatus +
                '}';
    }
}
