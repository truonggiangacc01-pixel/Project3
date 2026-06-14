package com.horseracing.project3.dto.request;

public class CancelTournamentRequestDto {
    private String reason;
    private boolean forceCancel;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public boolean isForceCancel() {
        return forceCancel;
    }

    public void setForceCancel(boolean forceCancel) {
        this.forceCancel = forceCancel;
    }
}
