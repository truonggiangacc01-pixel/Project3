package com.horseracing.project3.dto.request;

import java.time.LocalDateTime;

public class OpenRegistrationRequestDto {
    private LocalDateTime registrationStartDate;
    private LocalDateTime registrationEndDate;

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
}
