package dev.mr3.sb.service;

import dev.mr3.sb.exception.PatientAlreadyExistsException;
import dev.mr3.sb.model.Patient;
import dev.mr3.sb.repository.PatientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PatientService {
    private static final Logger logger = LoggerFactory.getLogger(PatientService.class);
    private final PatientRepository patientRepository;
    private final PasswordEncoder passwordEncoder;

    public PatientService(PatientRepository patientRepository, PasswordEncoder passwordEncoder) {
        this.patientRepository = patientRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void SignupPatient(Patient patient) {
        Optional<Patient> existing = patientRepository.findByUsername(patient.getUsername());
        if (existing.isPresent()) {
            logger.warn("Patient signup failure: username already exists, username={}", patient.getUsername());
            throw new PatientAlreadyExistsException("Username already exists");
        }
        patient.setPassword(passwordEncoder.encode(patient.getPassword()));
        patientRepository.save(patient);
        logger.info("Patient signup success: username={}", patient.getUsername());
    }

    public Patient updatePatientProfile(Long patientId, Patient patientUpdate) {
        Patient existingPatient = patientRepository.findById(patientId).orElse(null);
        if (existingPatient != null) {
            existingPatient.setFirstName(patientUpdate.getFirstName());
            existingPatient.setLastName(patientUpdate.getLastName());
            existingPatient.setEmail(patientUpdate.getEmail());
            existingPatient.setPhone(patientUpdate.getPhone());
            existingPatient.setAge(patientUpdate.getAge());
            Patient updatedPatient = patientRepository.save(existingPatient);
            logger.info("Patient profile updated: patientId={}", patientId);
            return updatedPatient;
        }
        logger.warn("Patient profile update failed: not found, patientId={}", patientId);
        return null;
    }

    public java.util.List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }
}
