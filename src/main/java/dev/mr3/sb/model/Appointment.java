package dev.mr3.sb.model;

import jakarta.persistence.*;


@Entity
// Stores a patient's appointment slot (weekday and time).
public class Appointment {
    @Id
    @GeneratedValue
    private Long id;
    @Column
    @Enumerated(EnumType.STRING)
    private Weekday weekday;
    @Column
    private String time;

    @ManyToOne
//    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    @ManyToOne
//    @JoinColumn(name = "patient_id")
    private Patient patient;

    public Appointment() {
    }
    public Appointment(Weekday weekday, String time) {
        this.weekday = weekday;
        this.time = time;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Weekday getWeekday() {
        return weekday;
    }
    public void setWeekday(Weekday weekday) {
        this.weekday = weekday;
    }
    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }
}