package dev.mr3.sb.model;

import jakarta.persistence.*;

@Entity
public class Appointment {
    @Id
    @GeneratedValue
    private Long appointmentId;

    @Column
    @Enumerated(EnumType.STRING)
    private Weekday weekday;

    @Column
    private String appointmenttime;

    @Column
    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @OneToOne(mappedBy = "appointment")
    private Report report;

    public Appointment() {}

    public Appointment(Weekday weekday, String appointmenttime, Status status, Doctor doctor, Patient patient) {
        this.weekday = weekday;
        this.appointmenttime = appointmenttime;
        this.status = status;
        this.doctor = doctor;
        this.patient = patient;
    }

    public Long getAppointmentId() {
        return appointmentId;
    }
    public void setAppointmentId(Long appointmentId) {
        this.appointmentId = appointmentId;
    }
    public Weekday getWeekday() {
        return weekday;
    }
    public void setWeekday(Weekday weekday) {
        this.weekday = weekday;
    }
    public String getAppointmenttime() {
        return appointmenttime;
    }
    public void setAppointmenttime(String appointmenttime) {
        this.appointmenttime = appointmenttime;
    }
    public Status getStatus() {
        return status;
    }
    public void setStatus(Status status) {
        this.status = status;
    }
    public Doctor getDoctor() {
        return doctor;
    }
    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }
    public Patient getPatient() {
        return patient;
    }
    public void setPatient(Patient patient) {
        this.patient = patient;
    }
    public Report getReport() {
        return report;
    }
    public void setReport(Report report) {
        this.report = report;
    }
}