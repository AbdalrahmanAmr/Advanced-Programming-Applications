package dev.mr3.sb.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@DiscriminatorValue("PATIENT")
public class Patient extends Person {

    @OneToMany(mappedBy = "patient")
    private List<Appointment> appointmentsAsPatient;

    public Patient() {}

    @Override
    public String getRole() {
        return "PATIENT";
    }

    public List<Appointment> getAppointmentsAsPatient() {
        return appointmentsAsPatient;
    }
    public void setAppointmentsAsPatient(List<Appointment> appointmentsAsPatient) {
        this.appointmentsAsPatient = appointmentsAsPatient;
    }
}