package com.horseracing.project3.entity;

import com.horseracing.project3.enums.TicketStatus;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "Ticket")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "price", columnDefinition = "DECIMAL(15, 0)", nullable = false)
    private BigDecimal price;

    @Column(name = "purchased_date", nullable = false)
    private LocalDateTime purchaseDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private TicketStatus status;

    /*___________________________________________________________________________________________________________ */

    //MAPPING MỐI QUAN HỆ GIỮA Ticket - Tournament
    //1, N Ticket bất kỳ phải thuộc về 1 Tournament
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

    //MAPPING MỐI QUAN HỆ GIỮA Ticket - Spectator
    //1, N Ticket bất kỳ phải thuộc về 1 Spectator
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
    //                                                  CONSTRUCTOR RỖNG

    public Ticket() {
    }

    /*___________________________________________________________________________________________________________ */
    //                                                  CONSTRUCTOR FULL

    public Ticket(BigDecimal price, LocalDateTime purchaseDate, TicketStatus status) {
        this.price = price;
        this.purchaseDate = purchaseDate;
        this.status = status;
    }

    /*___________________________________________________________________________________________________________ */
    //                                                  GETTER, SETTER

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public LocalDateTime getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDateTime purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public TicketStatus getStatus() {
        return status;
    }

    public void setStatus(TicketStatus status) {
        this.status = status;
    }

    /*___________________________________________________________________________________________________________ */
    //                                                  TO STRING

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", price=" + price +
                ", purchaseDate=" + purchaseDate +
                ", status=" + status +
                '}';
    }
}
