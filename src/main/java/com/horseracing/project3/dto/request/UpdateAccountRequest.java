package com.horseracing.project3.dto.request;

import java.time.LocalDate;

public class UpdateAccountRequest {
    private String fullName;
    private String phone;
    private LocalDate birthDate;
    
    // Status can be updated too
    private String status; 

    // Fields for specific roles
    private String address; // For HorseOwner
    private Integer experienceYears; // For Jockey, RaceReferee
    private String licenseNumber; // For Jockey
    private String certificateLevel; // For RaceReferee

    public UpdateAccountRequest() {}

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public LocalDate getBirthDate() { return birthDate; }
    public void setBirthDate(LocalDate birthDate) { this.birthDate = birthDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public Integer getExperienceYears() { return experienceYears; }
    public void setExperienceYears(Integer experienceYears) { this.experienceYears = experienceYears; }

    public String getLicenseNumber() { return licenseNumber; }
    public void setLicenseNumber(String licenseNumber) { this.licenseNumber = licenseNumber; }

    public String getCertificateLevel() { return certificateLevel; }
    public void setCertificateLevel(String certificateLevel) { this.certificateLevel = certificateLevel; }
}
