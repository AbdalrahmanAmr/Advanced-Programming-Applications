package dev.mr3.sb.controller;

import dev.mr3.sb.model.Patient;
import dev.mr3.sb.service.AuthService;
import dev.mr3.sb.service.DoctorService;
import dev.mr3.sb.service.PatientService;
import dev.mr3.sb.security.JwtUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthService authService;

    @MockBean
    private PatientService patientService;

    @MockBean
    private DoctorService doctorService;

    @MockBean
    private JwtUtil jwtUtil;

    @Test
    public void testLoginSuccessRedirectsToDashboardAndSetsCookie() throws Exception {
        Patient mockPatient = new Patient();
        mockPatient.setUsername("testuser");
        mockPatient.setPatientUsername("testuser");

        when(authService.validateLogin("testuser", "password123")).thenReturn(mockPatient);
        when(jwtUtil.generateToken("testuser", "PATIENT")).thenReturn("mock-jwt-token");

        mockMvc.perform(post("/auth/login")
                .param("username", "testuser")
                .param("password", "password123")
                .with(org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/patient/dashboard"))
                .andExpect(cookie().exists("JWT"))
                .andExpect(cookie().value("JWT", "mock-jwt-token"));
    }

    @Test
    public void testLoginFailureReturnsToLoginWithError() throws Exception {
        when(authService.validateLogin("wronguser", "wrongpass"))
            .thenThrow(new RuntimeException("Invalid username or password"));

        mockMvc.perform(post("/auth/login")
                .param("username", "wronguser")
                .param("password", "wrongpass")
                .with(org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("Login"))
                .andExpect(model().attributeExists("error"));
    }
}
