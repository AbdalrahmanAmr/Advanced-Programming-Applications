package dev.mr3.sb.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity
@PrimaryKeyJoinColumn(name = "person_id")
public class Patient extends Person {

    @OneToMany(mappedBy = "patient")
    private List<Appointment> appointmentsAsPatient;

    public Patient() {}

    public Patient(List<Appointment> appointmentsAsPatient) {
        this.appointmentsAsPatient = appointmentsAsPatient;
    }

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