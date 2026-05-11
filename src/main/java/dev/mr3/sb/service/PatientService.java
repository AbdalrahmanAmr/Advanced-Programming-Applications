package dev.mr3.sb.service;

import dev.mr3.sb.model.Patient;
import dev.mr3.sb.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PatientService {
    private final PatientRepository patientRepository;
    
    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public void SignupPatient(Patient patient) {
        Optional<Patient> existing = patientRepository.findByUsername(patient.getUsername());
        if (existing.isPresent()) {
            throw new RuntimeException("Username already exists");
        }
        patientRepository.save(patient);
    }

    public Patient updatePatientProfile(Long patientId, Patient patientUpdate) {
        Patient existingPatient = patientRepository.findById(patientId).orElse(null);
        if (existingPatient != null) {
            existingPatient.setFirstName(patientUpdate.getFirstName());
            existingPatient.setLastName(patientUpdate.getLastName());
            existingPatient.setEmail(patientUpdate.getEmail());
            existingPatient.setPhone(patientUpdate.getPhone());
            existingPatient.setAge(patientUpdate.getAge());
            return patientRepository.save(existingPatient);
        }
        return null;
    }

    public java.util.List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }
    
    public Patient getPatientById(Long patientId) {
        return patientRepository.findById(patientId).orElse(null);
    }
}
