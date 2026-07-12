package com.horseracing.project3.entity;

import com.horseracing.project3.enums.JockeyInvitationStatus;
import com.horseracing.project3.enums.RaceParticipationStatus;
import jakarta.persistence.*;

import java.time.LocalDateTime;
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

    @Column(name = "horse_ready")
    private Boolean horseReady;

    @Column(name = "jockey_ready")
    private Boolean jockeyReady;

    @Column(name = "inspection_note", columnDefinition = "NVARCHAR(MAX)")
    private String inspectionNote;

    @Column(name = "inspected_at")
    private LocalDateTime inspectedAt;

    /*___________________________________________________________________________________________________________ */

    //MAPPING MỐI QUAN HỆ GIỮA RaceParticipation - RaceSchedule
    //1, N RaceParticipation bất kỳ phải thuộc về 1 RaceSchedule
    @ManyToOne
    @JoinColumn(name = "RaceScheduleId")
    @com.fasterxml.jackson.annotation.JsonIgnoreProperties({"raceParticipationList", "raceResultList", "predictionList", "tournament"})
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
    @com.fasterxml.jackson.annotation.JsonIgnoreProperties({"raceParticipationList", "predictionList", "horseOwner"})
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
    @com.fasterxml.jackson.annotation.JsonIgnore
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
    @com.fasterxml.jackson.annotation.JsonIgnoreProperties({"raceParticipationList"})
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

    public Boolean getHorseReady() {
        return horseReady;
    }

    public void setHorseReady(Boolean horseReady) {
        this.horseReady = horseReady;
    }

    public Boolean getJockeyReady() {
        return jockeyReady;
    }

    public void setJockeyReady(Boolean jockeyReady) {
        this.jockeyReady = jockeyReady;
    }

    public String getInspectionNote() {
        return inspectionNote;
    }

    public void setInspectionNote(String inspectionNote) {
        this.inspectionNote = inspectionNote;
    }

    public LocalDateTime getInspectedAt() {
        return inspectedAt;
    }

    public void setInspectedAt(LocalDateTime inspectedAt) {
        this.inspectedAt = inspectedAt;
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
