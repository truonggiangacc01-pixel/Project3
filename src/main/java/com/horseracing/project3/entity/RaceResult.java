package com.horseracing.project3.entity;

import com.horseracing.project3.enums.RaceResultStatus;
import jakarta.persistence.*;

import java.time.LocalTime;

@Entity
@Table(name = "RaceResult")
public class RaceResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "rank_position")
    private Integer rankPosition;

    @Column(name = "finish_time")
    private LocalTime finishTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private RaceResultStatus status;

    /*___________________________________________________________________________________________________________ */

    //MAPPING MỐI QUAN HỆ GIỮA RaceResult - RaceParticipation
    //1, N RaceResult bất kỳ phải thuộc về 1 RaceParticipation
    @ManyToOne
    @JoinColumn(name = "RaceParticipationId")
    private RaceParticipation raceParticipation;

    public RaceParticipation getRaceParticipation() {
        return raceParticipation;
    }

    public void setRaceParticipation(RaceParticipation raceParticipation) {
        this.raceParticipation = raceParticipation;
    }

    /*___________________________________________________________________________________________________________ */

    //MAPPING MỐI QUAN HỆ GIỮA RaceResult - RaceReferee
    //1, N RaceResult bất kỳ phải thuộc về 1 RaceReferee
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

    //MAPPING MỐI QUAN HỆ GIỮA RaceResult - RaceSchedule
    //1, N RaceResult bất kỳ phải thuộc về 1 RaceSchedule
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
    //                                                  CONSTRUCTOR RỖNG

    public RaceResult() {
    }

    /*___________________________________________________________________________________________________________ */
    //                                                  CONSTRUCTOR FULL

    public RaceResult(Integer rankPosition, LocalTime finishTime, RaceResultStatus status) {
        this.rankPosition = rankPosition;
        this.finishTime = finishTime;
        this.status = status;
    }

    /*___________________________________________________________________________________________________________ */
    //                                                  GETTER, SETTER

    public Integer getRankPosition() {
        return rankPosition;
    }

    public void setRankPosition(Integer rankPosition) {
        this.rankPosition = rankPosition;
    }

    public LocalTime getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(LocalTime finishTime) {
        this.finishTime = finishTime;
    }

    public RaceResultStatus getStatus() {
        return status;
    }

    public void setStatus(RaceResultStatus status) {
        this.status = status;
    }

    /*___________________________________________________________________________________________________________ */
    //                                                  TO STRING

    @Override
    public String toString() {
        return "RaceResult{" +
                "id=" + id +
                ", rankPosition=" + rankPosition +
                ", finishTime=" + finishTime +
                ", status=" + status +
                '}';
    }
}
