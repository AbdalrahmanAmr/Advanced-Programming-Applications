package dev.mr3.sb.service;

import dev.mr3.sb.model.Doctor;
import dev.mr3.sb.repository.DoctorRepository;
import org.springframework.stereotype.Service;

@Service
public class DoctorService {
    private final DoctorRepository doctorRepository;
    public DoctorService(DoctorRepository doctorRepository) {this.doctorRepository = doctorRepository;}

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
}