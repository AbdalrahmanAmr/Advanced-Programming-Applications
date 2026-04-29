package dev.mr3.sb.model;

import jakarta.persistence.*;

@Entity
public class Slot {
    @Id
    @GeneratedValue
    private Long slotId;

    @ManyToOne  // NEW: Reference to Doctor
//    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    @Column
    @Enumerated(EnumType.STRING)
    private Weekday weekday;

    @Column
    private int availableCount;

    // Getters/setters...
}