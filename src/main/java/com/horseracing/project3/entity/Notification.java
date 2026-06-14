package com.horseracing.project3.entity;

import com.horseracing.project3.enums.NotificationStatus;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "Notification")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "message", columnDefinition = "NVARCHAR(100)", nullable = false)
    private String message;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "notification_status", nullable = false)
    private NotificationStatus status;

    /*___________________________________________________________________________________________________________ */

    //MAPPING MỐI QUAN HỆ GIỮA Notification - Jockey
    //1, N Notification bất kỳ phải thuộc về 1 Jockey
    @ManyToOne
    @JoinColumn(name = "JockeyId")
    private Jockey jockey;

    public Jockey getJockey() {
        return jockey;
    }

    public void setJockey(Jockey jockey) {
        this.jockey = jockey;
    }

    /*___________________________________________________________________________________________________________ */

    //MAPPING MỐI QUAN HỆ GIỮA Notification - RaceReferee
    //1, N Notification bất kỳ phải thuộc về 1 RaceReferee
    @ManyToOne
    @JoinColumn(name = "RaceRefereeId")
    private RaceReferee raceReferee;

    public RaceReferee getRaceReferee() {
        return raceReferee;
    }

    public void setRaceReferee(RaceReferee raceReferee) {
        this.raceReferee = raceReferee;
    }

    /*___________________________________________________________________________________________________________ */

    //MAPPING MỐI QUAN HỆ GIỮA Notification - HorseOwner
    //1, N Notification bất kỳ phải thuộc về 1 HorseOwner
    @ManyToOne
    @JoinColumn(name = "HorseOwnerId")
    private HorseOwner horseOwner;

    public HorseOwner getHorseOwner() {
        return horseOwner;
    }

    public void setHorseOwner(HorseOwner horseOwner) {
        this.horseOwner = horseOwner;
    }

    /*___________________________________________________________________________________________________________ */
    //                                                  CONSTRUCTOR RỖNG

    public Notification() {
    }

    /*___________________________________________________________________________________________________________ */
    //                                                  CONSTRUCTOR FULL

    public Notification(String message, LocalDateTime createdAt, NotificationStatus status) {
        this.message = message;
        this.createdAt = createdAt;
        this.status = status;
    }

    /*___________________________________________________________________________________________________________ */
    //                                                  GETTER, SETTER

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public NotificationStatus getStatus() {
        return status;
    }

    public void setStatus(NotificationStatus status) {
        this.status = status;
    }

    /*___________________________________________________________________________________________________________ */
    //                                                  TO STRING

    @Override
    public String toString() {
        return "Notification{" +
                "id=" + id +
                ", message='" + message + '\'' +
                ", createdAt=" + createdAt +
                ", status=" + status +
                '}';
    }
}
