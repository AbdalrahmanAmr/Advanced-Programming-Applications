package dev.mr3.sb.model;

import jakarta.persistence.*;

@Entity
// Stores a medical report that links user, injury, and appointment.
public class Report {

    @Id
    @GeneratedValue
    private Long ReportId;

    @ManyToOne
//    @JoinColumn(name = "patient_id", nullable = false)
    private User user;

    @Column
    private String treatmentSuggestion;

    @OneToOne
//    @JoinColumn(name = "appointment_id", nullable = false)
    private Appointment appointment;

    // No-arg constructor
    public Report() {
    }

    // Full constructor
    public Report(User user, String treatmentSuggestion, Appointment appointment) {
        this.user = user;
        this.treatmentSuggestion = treatmentSuggestion;
        this.appointment = appointment;
    }

    public Long getReportId() {
        return ReportId;
    }
    public User getPatient() {
        return user;
    }
    public void setPatient(User user) {
        this.user = user;
    }
    public String getTreatmentSuggestion() {
        return treatmentSuggestion;
    }
    public void setTreatmentSuggestion(String treatmentSuggestion) {
        this.treatmentSuggestion = treatmentSuggestion;
    }
    public Appointment getAppointment() {
        return appointment;
    }
    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }
}