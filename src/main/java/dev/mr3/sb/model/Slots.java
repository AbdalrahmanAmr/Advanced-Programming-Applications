package dev.mr3.sb.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Slots {
    @Id
    @GeneratedValue
    private Long SlotsId;
    private Weekday weekday;
    private String timeRange; // e.g., "09:00-10:00"
    private Status status;

    public Slots() {
    }
    public Slots(Long SlotsId, Weekday weekday, String timeRange, Status status) {
        this.SlotsId = SlotsId;
        this.weekday = weekday;
        this.timeRange = timeRange;
        this.status = status;
    }

    public Long getSlotsId() {
        return SlotsId;
    }
    public void setSlotsId(Long slotsId) {
        this.SlotsId = slotsId;
    }
    public Weekday getWeekday() {
        return weekday;
    }
    public void setWeekday(Weekday weekday) {
        this.weekday = weekday;
    }
    public String getTimeRange() {
        return timeRange;
    }
    public void setTimeRange(String timeRange) {
        this.timeRange = timeRange;
    }
    public Status getStatus() {
        return status;
    }
    public void setStatus(Status status) {
        this.status = status;
    }
}
