package com.horseracing.project3.dto.request;

import com.horseracing.project3.enums.UserRole;

public class AssignRoleRequest {
    private UserRole newRole;
    
    // Fields for specific roles
    private String address; // For HorseOwner
    private Integer experienceYears; // For Jockey, RaceReferee
    private String licenseNumber; // For Jockey
    private String certificateLevel; // For RaceReferee

    public AssignRoleRequest() {}

    public UserRole getNewRole() { return newRole; }
    public void setNewRole(UserRole newRole) { this.newRole = newRole; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public Integer getExperienceYears() { return experienceYears; }
    public void setExperienceYears(Integer experienceYears) { this.experienceYears = experienceYears; }

    public String getLicenseNumber() { return licenseNumber; }
    public void setLicenseNumber(String licenseNumber) { this.licenseNumber = licenseNumber; }

    public String getCertificateLevel() { return certificateLevel; }
    public void setCertificateLevel(String certificateLevel) { this.certificateLevel = certificateLevel; }
}
