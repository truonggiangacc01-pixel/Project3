package com.horseracing.project3.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Spectator")
public class Spectator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "full_name", columnDefinition = "NVARCHAR(255)", nullable = false)
    private String fullName;

    @Column(name = "user_name", columnDefinition = "NVARCHAR(255)", nullable = false, unique = true)
    private String userName;

    @Column(name = "email", length = 255, nullable = false)
    private String email;

    @Column(name = "phone", length = 11, nullable = false)
    private String phone;

    @Column(name = "password", length = 255, nullable = false)
    private String password;

    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    /*___________________________________________________________________________________________________________ */

    //MAPPING MỐI QUAN HỆ 1-N (Spectator - Ticket)
    //1 Spectator --< có nhiều Ticket - List<Ticket>
    @OneToMany(mappedBy = "spectator",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Ticket> ticketList = new ArrayList<>();

    public void addTicket(Ticket o){
        ticketList.add(o);
        o.setSpectator(this);
    }


    public void removeTicket(Ticket o){
        ticketList.remove(o);
        o.setSpectator(null);
    }

    /*___________________________________________________________________________________________________________ */

    //MAPPING MỐI QUAN HỆ 1-N (Spectator - Prediction)
    //1 Spectator --< có nhiều Prediction - List<Prediction>
    @OneToMany(mappedBy = "spectator",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Prediction> predictionList = new ArrayList<>();

    public void addPrediction(Prediction o){
        predictionList.add(o);
        o.setSpectator(this);
    }

    public void removePrediction(Prediction o){
        predictionList.remove(o);
        o.setSpectator(null);
    }


    /*___________________________________________________________________________________________________________ */
    //                                                  CONSTRUCTOR RỖNG

    public Spectator() {
    }

    /*___________________________________________________________________________________________________________ */
    //                                                  CONSTRUCTOR FULL

    public Spectator(String fullName, String userName, String email, String phone, String password, LocalDate birthDate) {
        this.fullName = fullName;
        this.userName = userName;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.birthDate = birthDate;
    }


    /*___________________________________________________________________________________________________________ */
    //                                                  GETTER, SETTER

    public Integer getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }



    /*___________________________________________________________________________________________________________ */
    //                                                  TO STRING


    @Override
    public String toString() {
        return "Spectator{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", password='" + password + '\'' +
                ", birthDate=" + birthDate +
                '}';
    }
}
