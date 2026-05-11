package dev.mr3.sb.service;

import dev.mr3.sb.model.Appointment;
import dev.mr3.sb.model.Doctor;
import dev.mr3.sb.model.Report;
import dev.mr3.sb.repository.DoctorRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class DoctorService {
    private final DoctorRepository doctorRepository;
    
    public DoctorService(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    public boolean SignupDoctor(Doctor doctor) {
        try {
            if (doctorRepository.findByUsername(doctor.getUsername()).isPresent()) {
                throw new RuntimeException("Username already exists");
            }
            doctorRepository.save(doctor);
            return true;
        } catch (RuntimeException e) {
            return false;
        }
    }

    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }
    
    public Doctor getDoctorById(Long doctorId) {
        return doctorRepository.findById(doctorId).orElse(null);
    }

    public Doctor updateDoctorProfile(Long doctorId, Doctor doctorUpdate) {
        Doctor existingDoctor = doctorRepository.findById(doctorId).orElse(null);
        if (existingDoctor != null) {
            existingDoctor.setFirstName(doctorUpdate.getFirstName());
            existingDoctor.setLastName(doctorUpdate.getLastName());
            existingDoctor.setEmail(doctorUpdate.getEmail());
            existingDoctor.setPhone(doctorUpdate.getPhone());
            existingDoctor.setAge(doctorUpdate.getAge());
            return doctorRepository.save(existingDoctor);
        }
        return null;
    }

    public List<Appointment> getDoctorAppointments(Long doctorId) {
        Doctor doctor = doctorRepository.findById(doctorId).orElse(null);
        if (doctor != null) {
            return doctor.getAppointmentsAsDoctor();
        }
        return Collections.emptyList();
    }

    public List<Report> getDoctorReports(Long doctorId) {
        Doctor doctor = doctorRepository.findById(doctorId).orElse(null);
        if (doctor != null) {
            return doctor.getReports();
        }
        return Collections.emptyList();
    }
}
