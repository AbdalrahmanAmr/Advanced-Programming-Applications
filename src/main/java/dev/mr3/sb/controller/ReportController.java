package dev.mr3.sb.controller;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import dev.mr3.sb.model.Appointment;
import dev.mr3.sb.repository.AppointmentSpecification;
import dev.mr3.sb.service.AppointmentService;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    private final AppointmentService appointmentService;

    public ReportController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @GetMapping("/daily-schedule")
    public ResponseEntity<byte[]> getDailySchedulePdf(@RequestParam(required = false) String date) {
        try {
            LocalDate targetDate = date != null ? LocalDate.parse(date) : LocalDate.now();

            Specification<Appointment> spec = AppointmentSpecification.onDate(
                targetDate.atStartOfDay(),
                targetDate.plusDays(1).atStartOfDay().minusNanos(1)
            );

            List<Appointment> dailyAppointments = appointmentService.searchAppointments(spec);

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            Document document = new Document();
            PdfWriter.getInstance(document, out);

            document.open();
            document.add(new Paragraph("Daily Schedule: " + targetDate.toString()));
            document.add(new Paragraph(" "));

            if (dailyAppointments.isEmpty()) {
                document.add(new Paragraph("No appointments scheduled for this day."));
            } else {
                for (Appointment appt : dailyAppointments) {
                    String time = appt.getAppointmentTime().format(DateTimeFormatter.ofPattern("HH:mm"));
                    String doctorName = appt.getDoctor().getFirstName() + " " + appt.getDoctor().getLastName();
                    String patientName = appt.getPatient().getFirstName() + " " + appt.getPatient().getLastName();
                    document.add(new Paragraph(time + " - Doctor: " + doctorName + " | Patient: " + patientName + " | Status: " + appt.getStatus()));
                }
            }

            document.close();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("filename", "schedule_" + targetDate + ".pdf");

            return new ResponseEntity<>(out.toByteArray(), headers, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
