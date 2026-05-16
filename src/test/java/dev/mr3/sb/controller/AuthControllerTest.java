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
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.hamcrest.Matchers.containsString;
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
        when(jwtUtil.getExpirationTimeSeconds()).thenReturn(36000L);

        mockMvc.perform(post("/auth/login")
                .param("username", "testuser")
                .param("password", "password123")
                .secure(true)
                .with(org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/patient/dashboard"))
                .andExpect(header().string("Set-Cookie", containsString("JWT=mock-jwt-token")))
                .andExpect(header().string("Set-Cookie", containsString("Max-Age=36000")))
                .andExpect(header().string("Set-Cookie", containsString("HttpOnly")))
                .andExpect(header().string("Set-Cookie", containsString("Secure")))
                .andExpect(header().string("Set-Cookie", containsString("SameSite=Lax")));
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

    @Test
    public void testRegisterWithInvalidRoleReturnsSignupWithError() throws Exception {
        mockMvc.perform(post("/auth/register")
                .param("role", "ADMIN")
                .param("firstName", "John")
                .param("lastName", "Doe")
                .param("username", "johndoe")
                .param("email", "john@example.com")
                .param("password", "password123")
                .param("age", "25")
                .param("phone", "+1234567890")
                .param("gender", "true")
                .with(org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("Signup"))
                .andExpect(model().attributeExists("error"))
                .andExpect(model().attribute("error", "Please select a valid account type"));
    }
}
