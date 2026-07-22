package com.horseracing.project3.dto.request;

import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;

public class LicenseUpdateRequest {

    @NotBlank(message = "License number cannot be blank")
    private String licenseNumber;

    private LocalDate licenseExpiryDate;

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
}
