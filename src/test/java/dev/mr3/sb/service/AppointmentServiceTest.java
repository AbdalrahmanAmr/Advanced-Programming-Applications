package dev.mr3.sb.service;

import dev.mr3.sb.model.Appointment;
import dev.mr3.sb.model.Doctor;
import dev.mr3.sb.model.Patient;
import dev.mr3.sb.model.Status;
import dev.mr3.sb.repository.AppointmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AppointmentServiceTest {

    @Mock
    private AppointmentRepository appointmentRepository;

    @InjectMocks
    private AppointmentService appointmentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void bookAppointment_Success() {
        Doctor doctor = new Doctor();
        doctor.setPersonId(1L);

        Patient patient = new Patient();
        patient.setPersonId(2L);

        Appointment appointment = new Appointment();
        appointment.setAppointmentTime(LocalDateTime.of(2025, 1, 1, 10, 0));

        when(appointmentRepository.findByPatientPersonId(2L)).thenReturn(Collections.emptyList());
        when(appointmentRepository.findByDoctorPersonId(1L)).thenReturn(Collections.emptyList());
        when(appointmentRepository.save(any(Appointment.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Appointment saved = appointmentService.bookAppointment(appointment, doctor, patient);

        assertNotNull(saved);
        assertEquals(Status.PENDING, saved.getStatus());
        assertEquals(doctor, saved.getDoctor());
        assertEquals(patient, saved.getPatient());
    }

    @Test
    void bookAppointment_PatientConflict_ThrowsException() {
        Doctor doctor = new Doctor();
        doctor.setPersonId(1L);

        Patient patient = new Patient();
        patient.setPersonId(2L);

        LocalDateTime time = LocalDateTime.of(2025, 1, 1, 10, 0);

        Appointment newAppt = new Appointment();
        newAppt.setAppointmentTime(time);

        Appointment existingAppt = new Appointment();
        existingAppt.setAppointmentTime(time);
        existingAppt.setStatus(Status.PENDING);

        when(appointmentRepository.findByPatientPersonId(2L)).thenReturn(List.of(existingAppt));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            appointmentService.bookAppointment(newAppt, doctor, patient);
        });

        assertEquals("Patient already has an appointment at this time", exception.getMessage());
    }

    @Test
    void bookAppointment_DoctorConflict_ThrowsException() {
        Doctor doctor = new Doctor();
        doctor.setPersonId(1L);

        Patient patient = new Patient();
        patient.setPersonId(2L);

        LocalDateTime time = LocalDateTime.of(2025, 1, 1, 10, 0);

        Appointment newAppt = new Appointment();
        newAppt.setAppointmentTime(time);

        Appointment existingAppt = new Appointment();
        existingAppt.setAppointmentTime(time);
        existingAppt.setStatus(Status.PENDING);

        when(appointmentRepository.findByPatientPersonId(2L)).thenReturn(Collections.emptyList());
        when(appointmentRepository.findByDoctorPersonId(1L)).thenReturn(List.of(existingAppt));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            appointmentService.bookAppointment(newAppt, doctor, patient);
        });

        assertEquals("Doctor is already booked at this time", exception.getMessage());
    }
}
