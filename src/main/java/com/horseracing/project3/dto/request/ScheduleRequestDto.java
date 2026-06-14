package com.horseracing.project3.dto.request;

import java.time.LocalDateTime;
import java.util.List;

public class ScheduleRequestDto {

    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private List<Integer> participationIds;

    public ScheduleRequestDto() {
    }

    public ScheduleRequestDto(LocalDateTime startTime, LocalDateTime endTime, List<Integer> participationIds) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.participationIds = participationIds;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public List<Integer> getParticipationIds() {
        return participationIds;
    }

    public void setParticipationIds(List<Integer> participationIds) {
        this.participationIds = participationIds;
    }
}
