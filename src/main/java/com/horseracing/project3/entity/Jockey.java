package com.horseracing.project3.entity;

import com.horseracing.project3.enums.AccountStatus;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Jockey")
public class Jockey {

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


    @Column(name = "experience_years", nullable = false)
    private Integer experienceYears;

    @Column(name = "license_number", nullable = false)
    private String licenseNumber;

    @Column(name = "license_expiry_date")
    private LocalDate licenseExpiryDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private AccountStatus accountStatus;

    /*___________________________________________________________________________________________________________ */

    //MAPPING MỐI QUAN HỆ 1-N (Jockey - Notification)
    //1 Jockey --< có nhiều Notification - List<Notification>
    @OneToMany(mappedBy = "jockey", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Notification> notificationList = new ArrayList<>();;

    public void addNotification(Notification o){
        notificationList.add(o);
        o.setJockey(this);
    }

    public void removeNotification(Notification o){
        notificationList.remove(o);
        o.setJockey(null);
    }

    /*___________________________________________________________________________________________________________ */

    //MAPPING MỐI QUAN HỆ 1-N (Jockey - RaceParticipation)
    //1 Jockey--< có nhiều RaceParticipation - List<RaceParticipation>
    @OneToMany(mappedBy = "jockey" , cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<RaceParticipation> raceParticipationList = new ArrayList<>();

    public void addRaceParticipation(RaceParticipation o){
        raceParticipationList.add(o);
        o.setJockey(this);
    }

    public void removeRaceParticipation(RaceParticipation o){
        raceParticipationList.remove(o);
        o.setJockey(null);
    }

    /*___________________________________________________________________________________________________________ */
    //                                                  CONSTRUCTOR RỖNG

    public Jockey() {
    }

    /*___________________________________________________________________________________________________________ */
    //                                                  CONSTRUCTOR FULL

    public Jockey(String fullName, String userName, String email, String phone, String password, LocalDate birthDate, Integer experienceYears, String licenseNumber) {
        this.fullName = fullName;
        this.userName = userName;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.birthDate = birthDate;
        this.experienceYears = experienceYears;
        this.licenseNumber = licenseNumber;
    }

    public Jockey(String fullName, String userName, String email, String phone, String password, LocalDate birthDate, Integer experienceYears, String licenseNumber, LocalDate licenseExpiryDate) {
        this.fullName = fullName;
        this.userName = userName;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.birthDate = birthDate;
        this.experienceYears = experienceYears;
        this.licenseNumber = licenseNumber;
        this.licenseExpiryDate = licenseExpiryDate;
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

    public Integer getExperienceYears() {
        return experienceYears;
    }

    public void setExperienceYears(Integer experienceYears) {
        this.experienceYears = experienceYears;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public LocalDate getLicenseExpiryDate() {
        return licenseExpiryDate;
    }

    public void setLicenseExpiryDate(LocalDate licenseExpiryDate) {
        this.licenseExpiryDate = licenseExpiryDate;
    }

    public AccountStatus getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(AccountStatus accountStatus) {
        this.accountStatus = accountStatus;
    }

    /*___________________________________________________________________________________________________________ */
    //                                                  TO STRING


    @Override
    public String toString() {
        return "Jockey{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", password='" + password + '\'' +
                ", birthDate=" + birthDate +
                ", experienceYears=" + experienceYears +
                ", licenseNumber='" + licenseNumber + '\'' +
                ", licenseExpiryDate=" + licenseExpiryDate +
                '}';
    }
}
