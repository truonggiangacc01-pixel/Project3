package com.horseracing.project3.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "RankingEntry")
public class RankingEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "points", nullable = false)
    private Integer points;

    @Column(name = "rank_position", nullable = false)
    private Integer rankPosition;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "TournamentId", nullable = false)
    private Tournament tournament;

    @ManyToOne
    @JoinColumn(name = "HorseId", nullable = false)
    private Horse horse;

    public Integer getId() {
        return id;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public Integer getRankPosition() {
        return rankPosition;
    }

    public void setRankPosition(Integer rankPosition) {
        this.rankPosition = rankPosition;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Tournament getTournament() {
        return tournament;
    }

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
    }

    public Horse getHorse() {
        return horse;
    }

    public void setHorse(Horse horse) {
        this.horse = horse;
    }
}
