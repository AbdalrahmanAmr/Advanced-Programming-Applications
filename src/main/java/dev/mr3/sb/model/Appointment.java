package dev.mr3.sb.model;

import jakarta.persistence.*;


@Entity
public class Appointment {
    @Id
    @GeneratedValue
    private Long AppointmentId;
    @Column
    @Enumerated(EnumType.STRING)
    private Weekday weekday;
    @Column
    private String Appointment;
    @Column
    @Enumerated(EnumType.STRING)
    private Status status;

    //One-to-One with Report (nullable)


    public Appointment() {
    }
    public Appointment(Weekday weekday, String Appointment) {
        this.weekday = weekday;
        this.Appointment = Appointment;
    }
    public Long getId() {
        return AppointmentId;
    }
    public void setId(Long AppointmentId) {
        this.AppointmentId = AppointmentId;
    }
    public Weekday getWeekday() {
        return weekday;
    }
    public void setWeekday(Weekday weekday) {
        this.weekday = weekday;
    }
}