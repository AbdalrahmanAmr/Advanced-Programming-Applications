package dev.mr3.sb.model;

import jakarta.persistence.*;

@Entity
public class Slots {
    @Id
    @GeneratedValue
    private Long slotsId;

    @Column
    @Enumerated(EnumType.STRING)
    private Weekday weekday;

    @Column
    private int availableCount;

    public Slots() {
    }

    public Slots(Weekday weekday, int availableCount) {
        this.weekday = weekday;
        this.availableCount = availableCount;
    }

    public Long getSlotsId() {
        return slotsId;
    }

    public void setSlotsId(Long slotsId) {
        this.slotsId = slotsId;
    }

    public Weekday getWeekday() {
        return weekday;
    }

    public void setWeekday(Weekday weekday) {
        this.weekday = weekday;
    }

    public int getAvailableCount() {
        return availableCount;
    }

    public void setAvailableCount(int availableCount) {
        this.availableCount = availableCount;
    }

    public void decrementSlot() {
        if (this.availableCount > 0) {
            this.availableCount--;
        }
    }

    public void incrementSlot() {
        this.availableCount++;
    }
}
