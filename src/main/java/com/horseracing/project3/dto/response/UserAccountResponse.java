package com.horseracing.project3.dto.response;

import com.horseracing.project3.enums.UserRole;
import java.time.LocalDate;

public class UserAccountResponse {
    private Integer id;
    private String fullName;
    private String userName;
    private String email;
    private String phone;
    private UserRole role;
    private String status;
    private LocalDate birthDate;
    private String address;
    private Integer experienceYears;
    private String licenseNumber;
    private String certificateLevel;

    public UserAccountResponse() {}

    public UserAccountResponse(Integer id, String fullName, String userName, String email, String phone, UserRole role, String status, LocalDate birthDate, String address, Integer experienceYears, String licenseNumber, String certificateLevel) {
        this.id = id;
        this.fullName = fullName;
        this.userName = userName;
        this.email = email;
        this.phone = phone;
        this.role = role;
        this.status = status;
        this.birthDate = birthDate;
        this.address = address;
        this.experienceYears = experienceYears;
        this.licenseNumber = licenseNumber;
        this.certificateLevel = certificateLevel;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getCertificateLevel() {
        return certificateLevel;
    }

    public void setCertificateLevel(String certificateLevel) {
        this.certificateLevel = certificateLevel;
    }
}
