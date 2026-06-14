package com.horseracing.project3.dto.request;

public class RegisterJockeyRequest {
    private Integer jockeyId;

    public RegisterJockeyRequest() {}

    public RegisterJockeyRequest(Integer jockeyId) {
        this.jockeyId = jockeyId;
    }

    public Integer getJockeyId() {
        return jockeyId;
    }

    public void setJockeyId(Integer jockeyId) {
        this.jockeyId = jockeyId;
    }
}
