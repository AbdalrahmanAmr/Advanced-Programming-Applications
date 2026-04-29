package dev.mr3.sb.model;

import dev.mr3.sb.model.Appointment;
import dev.mr3.sb.model.Person;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

import java.util.List;

@Entity
@DiscriminatorValue("DOCTOR")
public class Doctor extends Person {
    @Column
    private String specialty;

    @Column
    private String licenseNumber;

    @OneToMany(mappedBy = "doctor")
    private List<Appointment> appointmentsAsDoctor;

    @OneToMany(mappedBy = "doctor")
    private List<Slot> slots;

    @Override
    public String getRole() {
        return "DOCTOR";
    }

    // Doctor-specific methods and getters/setters...
}