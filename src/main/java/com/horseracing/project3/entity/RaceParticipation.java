package com.horseracing.project3.entity;

import com.horseracing.project3.enums.JockeyInvitationStatus;
import com.horseracing.project3.enums.RaceParticipationStatus;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "RaceParticipation")
public class RaceParticipation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private RaceParticipationStatus status;

    @Column(name = "lane_number")
    private Integer laneNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "jockey_invitation_status")
    private JockeyInvitationStatus jockeyInvitationStatus;

    /*___________________________________________________________________________________________________________ */

    //MAPPING MỐI QUAN HỆ GIỮA RaceParticipation - RaceSchedule
    //1, N RaceParticipation bất kỳ phải thuộc về 1 RaceSchedule
    @ManyToOne
    @JoinColumn(name = "RaceScheduleId")
    private RaceSchedule raceSchedule;

    public RaceSchedule getRaceSchedule() {
        return raceSchedule;
    }

    public void setRaceSchedule(RaceSchedule raceSchedule) {
        this.raceSchedule = raceSchedule;
    }

    /*___________________________________________________________________________________________________________ */

    //MAPPING MỐI QUAN HỆ GIỮA RaceParticipation - Horse
    //1, N RaceParticipation bất kỳ phải thuộc về 1 Horse
    @ManyToOne
    @JoinColumn(name = "HorseId")
    private Horse horse;

    public Horse getHorse() {
        return horse;
    }

    public void setHorse(Horse horse) {
        this.horse = horse;
    }

    /*___________________________________________________________________________________________________________ */

    //MAPPING MỐI QUAN HỆ 1-N (RaceParticipation - RaceResult)
    //1 RaceParticipation --< có nhiều RaceResult - List<RaceResult>
    @OneToMany(mappedBy = "raceParticipation", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<RaceResult> raceResultList = new ArrayList<>();

    public void addRaceResult(RaceResult o){
        raceResultList.add(o);
        o.setRaceParticipation(this);
    }

    public void removeRaceResult(RaceResult o){
        raceResultList.remove(o);
        o.setRaceParticipation(null);
    }

    /*___________________________________________________________________________________________________________ */

    //MAPPING MỐI QUAN HỆ GIỮA RaceParticipation - Jockey
    //1, N RaceParticipation bất kỳ phải thuộc về 1 Jockey
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
    //                                                  CONSTRUCTOR RỖNG

    public RaceParticipation() {
    }

    /*___________________________________________________________________________________________________________ */
    //                                                  CONSTRUCTOR FULL

    public RaceParticipation(RaceParticipationStatus status, Integer laneNumber) {
        this.status = status;
        this.laneNumber = laneNumber;
    }

    /*___________________________________________________________________________________________________________ */
    //                                                  GETTER, SETTER

    public Integer getId() {
        return id;
    }

    public RaceParticipationStatus getStatus() {
        return status;
    }

    public void setStatus(RaceParticipationStatus status) {
        this.status = status;
    }

    public Integer getLaneNumber() {
        return laneNumber;
    }

    public void setLaneNumber(Integer laneNumber) {
        this.laneNumber = laneNumber;
    }

    public JockeyInvitationStatus getJockeyInvitationStatus() {
        return jockeyInvitationStatus;
    }

    public void setJockeyInvitationStatus(JockeyInvitationStatus jockeyInvitationStatus) {
        this.jockeyInvitationStatus = jockeyInvitationStatus;
    }

    /*___________________________________________________________________________________________________________ */
    //                                                  TO STRING

    @Override
    public String toString() {
        return "RaceParticipation{" +
                "id=" + id +
                ", status=" + status +
                ", laneNumber=" + laneNumber +
                '}';
    }
}
