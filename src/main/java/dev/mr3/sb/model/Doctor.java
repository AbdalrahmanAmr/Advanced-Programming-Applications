package dev.mr3.sb.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity
@PrimaryKeyJoinColumn(name = "person_id") // FK to person table
public class Doctor extends Person {

    @OneToMany(mappedBy = "doctor", fetch = FetchType.EAGER)
    private List<Appointment> appointmentsAsDoctor;

    @OneToMany(mappedBy = "doctor", fetch = FetchType.EAGER)
    private List<Slot> slots;

    @OneToMany(mappedBy = "doctor", fetch = FetchType.EAGER)
    private List<Report> reports;

    @Column(length = 1000)
    private String summary;

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Certificate> certificates;

    public Doctor() {}

    public Doctor(List<Appointment> appointmentsAsDoctor, List<Slot> slots, List<Report> reports)
    {
        this.appointmentsAsDoctor = appointmentsAsDoctor;
        this.slots = slots;
        this.reports = reports;
    }

    @Override
    public String getRole() {
        return "DOCTOR";
    }

    public List<Appointment> getAppointmentsAsDoctor() {
        return appointmentsAsDoctor;
    }
    public void setAppointmentsAsDoctor(List<Appointment> appointmentsAsDoctor) {
        this.appointmentsAsDoctor = appointmentsAsDoctor;
    }
    public List<Slot> getSlots() {
        return slots;
    }
    public void setSlots(List<Slot> slots) {
        this.slots = slots;
    }
    public List<Report> getReports() {
        return reports;
    }
    public void setReports(List<Report> reports) {
        this.reports = reports;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public List<Certificate> getCertificates() {
        return certificates;
    }

    public void setCertificates(List<Certificate> certificates) {
        this.certificates = certificates;
    }

}