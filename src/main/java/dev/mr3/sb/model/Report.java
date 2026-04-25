package dev.mr3.sb.model;

import jakarta.persistence.*;

@Entity
// Stores a medical report that links patient, injury, and appointment.
public class Report {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
//    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @ManyToOne
//    @JoinColumn(name = "injury_id", nullable = false)
    private Injury injury;

    @Column(columnDefinition = "TEXT")
    private String treatmentSuggestion;

    @ManyToOne
//    @JoinColumn(name = "appointment_id", nullable = false)
    private Appointment appointment;

    // No-arg constructor
    public Report() {
    }

    // Full constructor
    public Report(Patient patient, Injury injury, String treatmentSuggestion, Appointment appointment) {
        this.patient = patient;
        this.injury = injury;
        this.treatmentSuggestion = treatmentSuggestion;
        this.appointment = appointment;
    }

    public Long getId() {
        return id;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Injury getInjury() {
        return injury;
    }

    public void setInjury(Injury injury) {
        this.injury = injury;
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