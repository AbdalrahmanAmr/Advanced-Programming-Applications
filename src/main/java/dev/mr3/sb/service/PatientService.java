package dev.mr3.sb.service;

import dev.mr3.sb.model.Patient;
import dev.mr3.sb.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PatientService {
    private final PatientRepository patientRepository;
    public PatientService(PatientRepository patientRepository) {this.patientRepository = patientRepository;}

    public void SignupPatient(Patient patient) {
            Optional<Patient> existing = patientRepository.findByUsername(patient.getUsername());
            if (existing.isPresent()) {
                throw new RuntimeException("Username already exists");
            }
            patientRepository.save(patient);



    }
}
