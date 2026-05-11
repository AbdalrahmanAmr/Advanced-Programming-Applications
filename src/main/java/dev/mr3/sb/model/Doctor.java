package dev.mr3.sb.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@PrimaryKeyJoinColumn(name = "person_id") // FK to person table
public class Doctor extends Person {

    @Column(unique = true)
    private String doctorUsername;

    @OneToMany(mappedBy = "doctor", fetch = FetchType.EAGER)
    private List<Appointment> appointmentsAsDoctor;

    @OneToMany(mappedBy = "doctor", fetch = FetchType.EAGER)
    private List<Slot> slots;

    @OneToMany(mappedBy = "doctor", fetch = FetchType.EAGER)
    private List<Report> reports;

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

    public String getDoctorUsername() {
        return doctorUsername;
    }

    public void setDoctorUsername(String doctorUsername) {
        this.doctorUsername = doctorUsername;
        super.setUsername(doctorUsername);
    }
}