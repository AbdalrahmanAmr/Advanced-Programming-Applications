package dev.mr3.sb.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("dev")
public class SecurityTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void unauthenticatedAccessToPublicEndpoint_ShouldBeOk() throws Exception {
        mockMvc.perform(get("/auth/login"))
               .andExpect(status().isOk());
    }

    @Test
    public void unauthenticatedAccessToProtectedEndpoint_ShouldBeForbiddenOrUnauthorized() throws Exception {
        mockMvc.perform(get("/patient/dashboard"))
               .andExpect(status().is(anyOf(is(401), is(403))));
    }

    @Test
    public void unauthenticatedAccessToH2Console_ShouldNotBeBlockedBySecurityInDev() throws Exception {
        mockMvc.perform(get("/h2-console/"))
               .andExpect(status().is(anyOf(is(200), is(404))))
               .andExpect(header().string("X-Frame-Options", "SAMEORIGIN"));
    }

    @Test
    @WithMockUser(roles = "PATIENT")
    public void patientAccessToDailyScheduleReport_ShouldBeForbidden() throws Exception {
        mockMvc.perform(get("/api/reports/daily-schedule"))
               .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "DOCTOR")
    public void doctorAccessToDailyScheduleReport_ShouldReturnPdf() throws Exception {
        mockMvc.perform(get("/api/reports/daily-schedule").param("date", "2026-05-16"))
               .andExpect(status().isOk())
               .andExpect(header().string(HttpHeaders.CONTENT_DISPOSITION, containsString("attachment; filename=\"schedule_2026-05-16.pdf\"")))
               .andExpect(content().contentType(MediaType.APPLICATION_PDF));
    }

    @Test
    @WithMockUser(roles = "DOCTOR")
    public void invalidReportDate_ShouldReturnBadRequest() throws Exception {
        mockMvc.perform(get("/api/reports/daily-schedule").param("date", "invalid-date"))
               .andExpect(status().isBadRequest())
               .andExpect(content().string("Invalid date format. Use YYYY-MM-DD."));
    }
}
