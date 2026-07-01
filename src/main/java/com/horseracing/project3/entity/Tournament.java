package com.horseracing.project3.entity;

import jakarta.persistence.*;

import com.horseracing.project3.enums.TournamentStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Tournament")
public class Tournament {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name", columnDefinition = "NVARCHAR(255)", unique = true,
            nullable = false)
    private String name;

    @Column(name = "location", columnDefinition = "NVARCHAR(50)", nullable = false)
    private String location;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private TournamentStatus status = TournamentStatus.DRAFT;

    @Column(name = "cancel_reason", columnDefinition = "NVARCHAR(MAX)")
    private String cancelReason;

    @Column(name = "registration_start_date")
    private LocalDateTime registrationStartDate;

    @Column(name = "registration_end_date")
    private LocalDateTime registrationEndDate;

    @Column(name = "registration_open", nullable = false)
    private Boolean registrationOpen = false;

    /*___________________________________________________________________________________________________________ */

    //MAPPING MỐI QUAN HỆ GIỮA Tournament - Admin
    //1, N Tournament bất kỳ phải thuộc về 1 Admin
    @ManyToOne
    @JoinColumn(name = "AdminId")
    private Admin admin;

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    /*___________________________________________________________________________________________________________ */

    //MAPPING MỐI QUAN HỆ 1-N (Tournament - Ticket)
    //1 Tournament --< có nhiều Ticket - List<Ticket>
    @OneToMany(mappedBy = "tournament", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Ticket> ticketList = new ArrayList<>();

    public void addTicket(Ticket o){
        ticketList.add(o);
        o.setTournament(this);
    }

    public void removeTicket(Ticket o){
        ticketList.remove(o);
        o.setTournament(null);
    }

    /*___________________________________________________________________________________________________________ */

    //MAPPING MỐI QUAN HỆ 1-N (Tournament - RaceSchedule)
    //1 Tournament --< có nhiều RaceSchedule - List<RaceSchedule>
    @OneToMany(mappedBy = "tournament", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<RaceSchedule> raceScheduleList = new ArrayList<>();

    public void addRaceSchedule(RaceSchedule o){
        raceScheduleList.add(o);
        o.setTournament(this);
    }

    public void removeRaceSchedule(RaceSchedule o){
        raceScheduleList.remove(o);
        o.setTournament(null);
    }

    public List<RaceSchedule> getRaceScheduleList() {
        return raceScheduleList;
    }

    /*___________________________________________________________________________________________________________ */

    //MAPPING MỐI QUAN HỆ 1-N (Tournament - Prize)
    //1 Prize --< có nhiều Prize - List<Prize>
    @OneToMany(mappedBy = "tournament", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Prize> prizeList = new ArrayList<>();

    public void addPrize(Prize o){
        prizeList.add(o);
        o.setTournament(this);
    }

    public void removePrize(Prize o){
        prizeList.remove(o);
        o.setTournament(null);
    }

    /*___________________________________________________________________________________________________________ */
    //                                                  CONSTRUCTOR RỖNG

    public Tournament() {
    }

    /*___________________________________________________________________________________________________________ */
    //                                                  CONSTRUCTOR FULL

    public Tournament(String name, String location, LocalDate startDate, LocalDate endDate, TournamentStatus status, String cancelReason, LocalDateTime registrationStartDate, LocalDateTime registrationEndDate, Boolean registrationOpen) {
        this.name = name;
        this.location = location;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.cancelReason = cancelReason;
        this.registrationStartDate = registrationStartDate;
        this.registrationEndDate = registrationEndDate;
        this.registrationOpen = registrationOpen;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public TournamentStatus getStatus() {
        return status;
    }

    public void setStatus(TournamentStatus status) {
        this.status = status;
    }

    public String getCancelReason() {
        return cancelReason;
    }

    public void setCancelReason(String cancelReason) {
        this.cancelReason = cancelReason;
    }

    public LocalDateTime getRegistrationStartDate() {
        return registrationStartDate;
    }

    public void setRegistrationStartDate(LocalDateTime registrationStartDate) {
        this.registrationStartDate = registrationStartDate;
    }

    public LocalDateTime getRegistrationEndDate() {
        return registrationEndDate;
    }

    public void setRegistrationEndDate(LocalDateTime registrationEndDate) {
        this.registrationEndDate = registrationEndDate;
    }

    public Boolean getRegistrationOpen() {
        return registrationOpen;
    }

    public void setRegistrationOpen(Boolean registrationOpen) {
        this.registrationOpen = registrationOpen;
    }

    /*___________________________________________________________________________________________________________ */
    //                                                  TO STRING

    @Override
    public String toString() {
        return "Tournament{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", status=" + status +
                ", cancelReason='" + cancelReason + '\'' +
                ", registrationStartDate=" + registrationStartDate +
                ", registrationEndDate=" + registrationEndDate +
                ", registrationOpen=" + registrationOpen +
                '}';
    }
}
