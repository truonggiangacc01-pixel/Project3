package com.horseracing.project3.entity;

import com.horseracing.project3.enums.PredictionStatus;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "Prediction")
public class Prediction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "number", nullable = false)
    private Integer number;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private PredictionStatus status;

    @Column(name = "stake_amount", columnDefinition = "DECIMAL(15, 0)", nullable = false)
    private BigDecimal stakeAmount = BigDecimal.ZERO;

    @Column(name = "payout_amount", columnDefinition = "DECIMAL(15, 0)")
    private BigDecimal payoutAmount;

    /*___________________________________________________________________________________________________________ */

    //MAPPING MỐI QUAN HỆ GIỮA Prediction - Spectator
    //1, N Prediction bất kỳ phải thuộc về 1 Spectator
    @ManyToOne
    @JoinColumn(name = "SpectatorId")
    private Spectator spectator;

    public Spectator getSpectator() {
        return spectator;
    }

    public void setSpectator(Spectator spectator) {
        this.spectator = spectator;
    }

    /*___________________________________________________________________________________________________________ */

    //MAPPING MỐI QUAN HỆ GIỮA Prediction - RaceSchedule
    //1, N Prediction bất kỳ phải thuộc về 1 RaceSchedule
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

    //MAPPING MỐI QUAN HỆ GIỮA Prediction - Horse
    //1, N Prediction bất kỳ phải thuộc về 1 Horse
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
    //                                                  CONSTRUCTOR RỖNG

    public Prediction() {
    }

    /*___________________________________________________________________________________________________________ */
    //                                                  CONSTRUCTOR FULL

    public Prediction(Integer number, LocalDateTime createdAt, PredictionStatus status) {
        this.number = number;
        this.createdAt = createdAt;
        this.status = status;
        this.stakeAmount = BigDecimal.ZERO;
    }

    /*___________________________________________________________________________________________________________ */
    //                                                  GETTER, SETTER

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public PredictionStatus getStatus() {
        return status;
    }

    public void setStatus(PredictionStatus status) {
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public BigDecimal getStakeAmount() {
        return stakeAmount == null ? BigDecimal.ZERO : stakeAmount;
    }

    public void setStakeAmount(BigDecimal stakeAmount) {
        this.stakeAmount = stakeAmount == null ? BigDecimal.ZERO : stakeAmount;
    }

    public BigDecimal getPayoutAmount() {
        return payoutAmount;
    }

    public void setPayoutAmount(BigDecimal payoutAmount) {
        this.payoutAmount = payoutAmount;
    }

    /*___________________________________________________________________________________________________________ */
    //                                                  TO STRING

    @Override
    public String toString() {
        return "Prediction{" +
                "id=" + id +
                ", number=" + number +
                ", createdAt=" + createdAt +
                ", status=" + status +
                '}';
    }
}
