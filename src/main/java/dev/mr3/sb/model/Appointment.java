package dev.mr3.sb.model;
import java.util.List;
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
    @ManyToOne
    @JoinColumn(name = "patient_id")
    private User patient; // User where isDoctor = false

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private User doctor;
    @OneToOne(mappedBy = "appointment")
    private Report report;

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