package dev.mr3.sb.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@DiscriminatorValue("DOCTOR")
public class Doctor extends Person {

    @OneToMany(mappedBy = "doctor")
    private List<Appointment> appointmentsAsDoctor;

    @OneToMany(mappedBy = "doctor")
    private List<Slot> slots;

    @OneToMany(mappedBy = "doctor")
    private List<Report> reports;

    public Doctor() {}

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
}