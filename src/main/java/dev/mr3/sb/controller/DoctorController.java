package dev.mr3.sb.controller;

import dev.mr3.sb.model.Appointment;
import dev.mr3.sb.model.Doctor;
import dev.mr3.sb.model.Person;
import dev.mr3.sb.model.Report;
import dev.mr3.sb.model.Status;
import dev.mr3.sb.service.AppointmentService;
import dev.mr3.sb.service.DoctorService;
import dev.mr3.sb.service.ReportService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/doctor")
public class DoctorController {
    private final DoctorService doctorService;
    private final AppointmentService appointmentService;
    private final ReportService reportService;
    public DoctorController(DoctorService doctorService ,AppointmentService appointmentService , ReportService reportService) {
         this.reportService = reportService;
        this.doctorService = doctorService;
        this.appointmentService = appointmentService;
    }

    private Person getAuthorizeddoctor(HttpSession session) {
        Person doctor = (Person) session.getAttribute("doctor");
        if (doctor == null || !doctor.getRole().equals("DOCTOR")) {
            return null;
        }
        return doctor;
    }

    @GetMapping("/dashboard")
    public String doctorDashboard(HttpSession session, Model model) {
        Person doctor = getAuthorizeddoctor(session);
        if (doctor == null) {
            return "redirect:/auth/login";
        }
        model.addAttribute("doctor", doctor);
        return "DoctorDashboard";
    }

    @GetMapping("/profile")
    public String doctorProfile(HttpSession session, Model model) {
        Person doctor = getAuthorizeddoctor(session);
        if (doctor == null) {
            return "redirect:/auth/login";
        }
        model.addAttribute("person", doctor);
        return "UserProfile";
    }

    @PostMapping("/profile")
    public String updateDoctorProfile(@Valid @ModelAttribute("person") Doctor doctorUpdate, HttpSession session, Model model , BindingResult result) {
        Person doctor = getAuthorizeddoctor(session);
        if (doctor == null) {
            return "redirect:/auth/login";
        }
        if (result.hasErrors()) {
            return "UserProfile"; // Reload the profile page so the user sees the errors
        }
        Doctor updateddoctor = doctorService.updateDoctorProfile(doctor.getPersonId(), doctorUpdate);
        if (updateddoctor != null) {
            session.setAttribute("doctor", updateddoctor);
        }

        return "redirect:/doctor/profile";
    }

    @GetMapping("/appointments")
    public String doctorAppointments(HttpSession session, Model model) {
        Person doctor = getAuthorizeddoctor(session);
        if (doctor == null) {
            return "redirect:/auth/login";
        }
        model.addAttribute("appointments", doctorService.getDoctorAppointments(doctor.getPersonId()));
        return "DoctorAppointment";
    }

    @GetMapping("/reports")
    public String doctorReports(HttpSession session, Model model) {
        Person doctor = getAuthorizeddoctor(session);
        if (doctor == null) {
            return "redirect:/auth/login";
        }
        model.addAttribute("reports", doctorService.getDoctorReports(doctor.getPersonId()));
        return "DoctorReportsList";
    }

    @GetMapping("/reports/view/{id}")
    public String viewReport(@PathVariable Long id, HttpSession session, Model model) {
        Person doctor = getAuthorizeddoctor(session);
        if (doctor == null) {
            return "redirect:/auth/login";
        }
        Report report = reportService.getReportById(id);
        if (report == null || !report.getDoctor().getPersonId().equals(doctor.getPersonId())) {
            return "redirect:/doctor/reports";
        }
        model.addAttribute("report", report);
        return "ReportView";
    }

    @GetMapping("/reports/write/{id}")
    public String writeReport(@PathVariable Long id, HttpSession session, Model model) {
        Person doctor = getAuthorizeddoctor(session);
        if (doctor == null) {
            return "redirect:/auth/login";
        }
        Appointment appointment = appointmentService.getAppointmentById(id);
        
        // Check if a report already exists for this appointment
        Report existingReport = reportService.getReportByAppointmentId(id);
        if (existingReport != null) {
            model.addAttribute("alreadyWritten", true);
            model.addAttribute("message", "A report has already been written for this appointment");
            model.addAttribute("appointment", appointment);
            return "WriteReports";
        }
        
        model.addAttribute("alreadyWritten", false);
        model.addAttribute("appointment", appointment);
        model.addAttribute("report", new Report());
        return "WriteReports";
    }

    @PostMapping("/reports/write")
    public String saveReport(@ModelAttribute Report report,
                             @RequestParam Long appointmentId,
                             HttpSession session) {
        Person doctor = getAuthorizeddoctor(session);
        if (doctor == null) {
            return "redirect:/auth/login";
        }
        report.setDoctor((Doctor) doctor);
        reportService.createReport(report, appointmentId);
        return "redirect:/doctor/reports";
    }

    @PostMapping("/appointments/{id}/status")
    public String updateAppointmentStatus(@PathVariable Long id,
                                         @RequestParam String status,
                                         HttpSession session) {
        Person doctor = getAuthorizeddoctor(session);
        if (doctor == null) {
            return "redirect:/auth/login";
        }
        
        Appointment appointment = appointmentService.getAppointmentById(id);
        if (appointment == null || !appointment.getDoctor().getPersonId().equals(doctor.getPersonId())) {
            return "redirect:/doctor/appointments";
        }
        
        try {
            Status newStatus = Status.valueOf(status.toUpperCase());
            appointmentService.updateAppointmentStatus(id, newStatus);
        } catch (IllegalArgumentException e) {
            // Invalid status
        }
        
        return "redirect:/doctor/appointments";
    }
}
