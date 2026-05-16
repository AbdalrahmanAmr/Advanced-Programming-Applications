package dev.mr3.sb.controller;

import dev.mr3.sb.model.Patient;
import dev.mr3.sb.security.JwtUtil;
import dev.mr3.sb.service.AppointmentService;
import dev.mr3.sb.service.DoctorService;
import dev.mr3.sb.service.PatientService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
class PatientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DoctorService doctorService;

    @MockBean
    private AppointmentService appointmentService;

    @MockBean
    private PatientService patientService;

    @MockBean
    private JwtUtil jwtUtil;

    @Test
    void invalidDoctorIdReturnsBookingFormWithError() throws Exception {
        Patient patient = new Patient();
        patient.setPersonId(1L);
        patient.setPatientUsername("patient1");

        when(doctorService.getDoctorById(999L)).thenReturn(null);
        when(doctorService.getAllDoctors()).thenReturn(List.of());

        mockMvc.perform(post("/patient/appointment/book")
                .with(user("patient1").roles("PATIENT"))
                .with(csrf())
                .sessionAttr("patient", patient)
                .param("doctorId", "999")
                .param("appointmentTime", "2030-01-01T10:00"))
            .andExpect(status().isOk())
            .andExpect(view().name("BookVisit"))
            .andExpect(model().attributeExists("error"))
            .andExpect(model().attribute("error", "Selected doctor was not found"));

        verify(appointmentService, never()).bookAppointment(org.mockito.ArgumentMatchers.any(), org.mockito.ArgumentMatchers.any(), org.mockito.ArgumentMatchers.any());
    }
}
