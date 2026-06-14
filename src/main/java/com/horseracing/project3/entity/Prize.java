package com.horseracing.project3.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "Prize")
public class Prize {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name", columnDefinition = "NVARCHAR(255)", nullable = false)
    private String name;

    @Column(name = "amount", columnDefinition = "Decimal(12, 2)", nullable = false)
    private BigDecimal amount;

    @Column(name = "ranking_required")
    private Integer rankingRequired;

    /*___________________________________________________________________________________________________________ */

    //MAPPING MỐI QUAN HỆ GIỮA RaceSchedule - Tournament
    //1, N RaceSchedule bất kỳ phải thuộc về 1 Tournament
    @ManyToOne
    @JoinColumn(name = "TournamentId")
    private Tournament tournament;

    public Tournament getTournament() {
        return tournament;
    }

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
    }

    /*___________________________________________________________________________________________________________ */
    //                                                  CONSTRUCTOR RỖNG

    public Prize() {
    }

    /*___________________________________________________________________________________________________________ */
    //                                                  CONSTRUCTOR FULL

    public Prize(String name, BigDecimal amount, Integer rankingRequired) {
        this.name = name;
        this.amount = amount;
        this.rankingRequired = rankingRequired;
    }

    /*___________________________________________________________________________________________________________ */
    //                                                  GETTER, SETTER

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Integer getRankingRequired() {
        return rankingRequired;
    }

    public void setRankingRequired(Integer rankingRequired) {
        this.rankingRequired = rankingRequired;
    }

    /*___________________________________________________________________________________________________________ */
    //                                                  TO STRING

    @Override
    public String toString() {
        return "Prize{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", amount=" + amount +
                ", rankingRequired=" + rankingRequired +
                '}';
    }
}
