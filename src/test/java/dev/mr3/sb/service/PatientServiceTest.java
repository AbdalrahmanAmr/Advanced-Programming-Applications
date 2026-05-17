package dev.mr3.sb.service;

import dev.mr3.sb.exception.PatientAlreadyExistsException;
import dev.mr3.sb.model.Patient;
import dev.mr3.sb.repository.PatientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class PatientServiceTest {

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private PatientService patientService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void signupPatient_UsernameExists_ThrowsPatientAlreadyExistsException() {
        Patient existingPatient = new Patient();
        existingPatient.setUsername("testuser");

        Patient newPatient = new Patient();
        newPatient.setUsername("testuser");

        when(patientRepository.findByUsername("testuser")).thenReturn(Optional.of(existingPatient));

        assertThrows(PatientAlreadyExistsException.class, () -> {
            patientService.SignupPatient(newPatient);
        });
    }
}
