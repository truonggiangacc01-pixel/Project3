package com.horseracing.project3.dto.request;

import com.horseracing.project3.enums.UserRole;
import java.time.LocalDate;

public class CreateAccountRequest {
    private String fullName;
    private String userName;
    private String email;
    private String phone;
    private String password;
    private LocalDate birthDate;
    private UserRole role;

    // Fields for specific roles
    private String address; // For HorseOwner
    private Integer experienceYears; // For Jockey, RaceReferee
    private String licenseNumber; // For Jockey
    private String certificateLevel; // For RaceReferee

    public CreateAccountRequest() {}

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public LocalDate getBirthDate() { return birthDate; }
    public void setBirthDate(LocalDate birthDate) { this.birthDate = birthDate; }

    public UserRole getRole() { return role; }
    public void setRole(UserRole role) { this.role = role; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public Integer getExperienceYears() { return experienceYears; }
    public void setExperienceYears(Integer experienceYears) { this.experienceYears = experienceYears; }

    public String getLicenseNumber() { return licenseNumber; }
    public void setLicenseNumber(String licenseNumber) { this.licenseNumber = licenseNumber; }

    public String getCertificateLevel() { return certificateLevel; }
    public void setCertificateLevel(String certificateLevel) { this.certificateLevel = certificateLevel; }
}
