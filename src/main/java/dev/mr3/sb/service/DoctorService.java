package dev.mr3.sb.service;

import dev.mr3.sb.model.Appointment;
import dev.mr3.sb.model.Doctor;
import dev.mr3.sb.model.Report;
import dev.mr3.sb.repository.DoctorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class DoctorService {
    private static final Logger logger = LoggerFactory.getLogger(DoctorService.class);
    private final DoctorRepository doctorRepository;
    private final PasswordEncoder passwordEncoder;
    
    public DoctorService(DoctorRepository doctorRepository, PasswordEncoder passwordEncoder) {
        this.doctorRepository = doctorRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean SignupDoctor(Doctor doctor) {
        try {
            if (doctorRepository.findByUsername(doctor.getUsername()).isPresent()) {
                logger.warn("Doctor signup failure: username already exists, username={}", doctor.getUsername());
                throw new RuntimeException("Username already exists");
            }
            doctor.setPassword(passwordEncoder.encode(doctor.getPassword()));
            doctorRepository.save(doctor);
            logger.info("Doctor signup success: username={}", doctor.getUsername());
            return true;
        } catch (RuntimeException e) {
            logger.warn("Doctor signup failure: {}", e.getMessage());
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
            Doctor updatedDoctor = doctorRepository.save(existingDoctor);
            logger.info("Doctor profile updated: doctorId={}", doctorId);
            return updatedDoctor;
        }
        logger.warn("Doctor profile update failed: not found, doctorId={}", doctorId);
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
