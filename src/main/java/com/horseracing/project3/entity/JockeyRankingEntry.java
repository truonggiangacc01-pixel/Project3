package com.horseracing.project3.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "JockeyRankingEntry")
public class JockeyRankingEntry {

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
    @JoinColumn(name = "JockeyId", nullable = false)
    private Jockey jockey;

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

    public Jockey getJockey() {
        return jockey;
    }

    public void setJockey(Jockey jockey) {
        this.jockey = jockey;
    }
}
