package dev.mr3.sb.model;

import dev.mr3.sb.model.Appointment;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

import java.util.List;

@Entity
@DiscriminatorValue("PATIENT")
public class Patient extends Person {
    @Column
    private String medicalNumber;

    @Column
    private String insuranceProvider;

    @OneToMany(mappedBy = "patient")
    private List<Appointment> appointmentsAsPatient;

    @Override
    public String getRole() {
        return "PATIENT";
    }

    // Patient-specific methods and getters/setters...
}