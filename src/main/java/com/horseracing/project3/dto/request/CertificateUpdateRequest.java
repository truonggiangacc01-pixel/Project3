package com.horseracing.project3.dto.request;

import jakarta.validation.constraints.NotBlank;

public class CertificateUpdateRequest {

    @NotBlank(message = "Certificate level cannot be blank")
    private String certificateLevel;

    public String getCertificateLevel() {
        return certificateLevel;
    }

    public void setCertificateLevel(String certificateLevel) {
        this.certificateLevel = certificateLevel;
    }
}
