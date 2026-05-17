package dev.mr3.sb.controller;

import dev.mr3.sb.model.Doctor;
import dev.mr3.sb.model.Patient;
import dev.mr3.sb.service.AppointmentService;
import dev.mr3.sb.service.DoctorService;
import dev.mr3.sb.service.PatientService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
public class PatientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DoctorService doctorService;

    @MockBean
    private AppointmentService appointmentService;

    @MockBean
    private PatientService patientService;

    @Test
    public void testBookAppointmentWithInvalidDoctorShowsError() throws Exception {
        Patient patient = new Patient();
        patient.setPersonId(1L);
        patient.setPatientUsername("patient1");

        Doctor availableDoctor = new Doctor();
        availableDoctor.setPersonId(2L);
        availableDoctor.setDoctorUsername("doctor1");

        when(doctorService.getDoctorById(999L)).thenReturn(null);
        when(doctorService.getAllDoctors()).thenReturn(List.of(availableDoctor));

        mockMvc.perform(post("/patient/appointment/book")
                .param("doctorId", "999")
                .param("appointmentTime", LocalDateTime.now().plusDays(1).withSecond(0).withNano(0).toString())
                .sessionAttr("patient", patient)
                .with(SecurityMockMvcRequestPostProcessors.user("patient1").roles("PATIENT"))
                .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("BookVisit"))
                .andExpect(model().attributeExists("error"))
                .andExpect(model().attribute("error", "Selected doctor was not found"))
                .andExpect(model().attributeExists("doctors"))
                .andExpect(model().attributeExists("person"));
    }
}
