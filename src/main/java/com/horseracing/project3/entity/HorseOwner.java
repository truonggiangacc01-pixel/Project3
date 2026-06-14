package com.horseracing.project3.entity;

import com.horseracing.project3.enums.AccountStatus;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "HorseOwner")
public class HorseOwner {

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

    @Column(name = "address", columnDefinition = "NVARCHAR(255)", nullable = false)
    private String address;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private AccountStatus accountStatus;

    /*___________________________________________________________________________________________________________ */

    //MAPPING MỐI QUAN HỆ 1-N (HorseOwner - Notification)
    //1 HorseOwner --< có nhiều Notification - List<Notification>
    @OneToMany(mappedBy = "horseOwner", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Notification> notificationList = new ArrayList<>();;

    public void addNotification(Notification o){
        notificationList.add(o);
        o.setHorseOwner(this);
    }

    public void removeNotification(Notification o){
        notificationList.remove(o);
        o.setHorseOwner(null);
    }

    /*___________________________________________________________________________________________________________ */

    //MAPPING MỐI QUAN HỆ 1-N (HorseOwner - Horse)
    //1 HorseOwner --< có nhiều Horse - List<Horse>
    @OneToMany(mappedBy = "horseOwner", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Horse> horseList = new ArrayList<>();

    public void addHorse(Horse o){
        horseList.add(o);
        o.setHorseOwner(this);
    }

    public void removeHorse(Horse o){
        horseList.remove(o);
        o.setHorseOwner(null);
    }

    /*___________________________________________________________________________________________________________ */
    //                                                  CONSTRUCTOR RỖNG

    public HorseOwner() {
    }

    /*___________________________________________________________________________________________________________ */
    //                                                  CONSTRUCTOR FULL

    public HorseOwner(String fullName, String userName, String email, String phone, String password, LocalDate birthDate, String address) {
        this.fullName = fullName;
        this.userName = userName;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.birthDate = birthDate;
        this.address = address;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public AccountStatus getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(AccountStatus accountStatus) {
        this.accountStatus = accountStatus;
    }

    /*___________________________________________________________________________________________________________ */
    //                                                 TO STRING


    @Override
    public String toString() {
        return "HorseOwner{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", password='" + password + '\'' +
                ", birthDate=" + birthDate +
                ", address='" + address + '\'' +
                '}';
    }
}
