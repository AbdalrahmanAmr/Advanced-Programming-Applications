package dev.mr3.sb.controller;

import dev.mr3.sb.model.Appointment;
import dev.mr3.sb.model.Doctor;
import dev.mr3.sb.model.Patient;
import dev.mr3.sb.model.Person;
import dev.mr3.sb.model.Weekday;
import dev.mr3.sb.service.AppointmentService;
import dev.mr3.sb.service.DoctorService;
import dev.mr3.sb.service.PatientService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/patient")
public class PatientController {

    private final DoctorService doctorService;
    private final AppointmentService appointmentService;
    private final PatientService patientService;

    @Autowired
    public PatientController(DoctorService doctorService, AppointmentService appointmentService, PatientService patientService) {
        this.doctorService = doctorService;
        this.appointmentService = appointmentService;
        this.patientService = patientService;
    }

    private Person getAuthorizedPatient(HttpSession session) {
        Person patient = (Person) session.getAttribute("patient");
        if (patient == null || !patient.getRole().equals("PATIENT")) {
            return null;
        }
        return patient;
    }

    @GetMapping ("/dashboard")
    public String patientDashboard(HttpSession session, Model model) {
        Person patient = getAuthorizedPatient(session);
        if (patient == null) {
            return "redirect:/auth/login";
        }
        model.addAttribute("patient", patient);
        return "PatientDashboard";
    }

    @GetMapping("/profile")
    public String patientProfile(HttpSession session, Model model) {
        Person patient = getAuthorizedPatient(session);
        if (patient == null) {
            return "redirect:/auth/login";
        }
        
        model.addAttribute("person", patient);
        return "UserProfile";
    }

    @PostMapping("/profile")
    public String updatePatientProfile(@Valid @ModelAttribute("person") Patient patientUpdate,
                                       @RequestParam(value = "profilePictureFile", required = false) org.springframework.web.multipart.MultipartFile profilePictureFile,
                                       HttpSession session, Model model , BindingResult result) {
        Person patient = getAuthorizedPatient(session);
        if (patient == null) {
            return "redirect:/auth/login";
        }
        if (result.hasErrors()) {
            return "UserProfile";
        }
        try {
            if (profilePictureFile != null && !profilePictureFile.isEmpty()) {
                patientUpdate.setProfilePicture(profilePictureFile.getBytes());
                patientUpdate.setProfilePictureContentType(profilePictureFile.getContentType());
            }
        } catch (java.io.IOException e) {
            model.addAttribute("error", "Error uploading profile picture");
            return "UserProfile";
        }
        Patient updatedPatient = patientService.updatePatientProfile(patient.getPersonId(), patientUpdate);
        if (updatedPatient != null) {
            session.setAttribute("patient", updatedPatient);
        }
        
        return "redirect:/patient/profile";
    }

    @GetMapping("/appointments")
    public String patientAppointments(HttpSession session, Model model) {
        Person patient = getAuthorizedPatient(session);
        if (patient == null) {
            return "redirect:/auth/login";
        }
        List<Appointment> appointments = appointmentService.getAppointmentsByPatient(patient.getPersonId());
        model.addAttribute("patient", patient);
        model.addAttribute("appointments", appointments);
        return "PatientAppointment";
    }

    @GetMapping("/appointment/book")
    public String bookAppointment(HttpSession session, Model model) {
        Person patient = getAuthorizedPatient(session);
        if (patient == null) {
            return "redirect:/auth/login";
        }
        List<Doctor> doctors = doctorService.getAllDoctors();
        model.addAttribute("person", patient);
        model.addAttribute("doctors", doctors);
        model.addAttribute("weekdays", Weekday.values());
        model.addAttribute("appointment", new Appointment());
        return "BookVisit";
    }

    @PostMapping("/appointment/book")
    public String processBookAppointment(@ModelAttribute("appointment") Appointment appointment, 
                                         @RequestParam("doctorId") Long doctorId,
                                         BindingResult result, HttpSession session, Model model) {
        Person patient = getAuthorizedPatient(session);
        if (patient == null) {
            return "redirect:/auth/login";
        }
        if (result.hasErrors()) {
            List<Doctor> doctors = doctorService.getAllDoctors();
            model.addAttribute("person", patient);
            model.addAttribute("doctors", doctors);
            model.addAttribute("weekdays", Weekday.values());
            return "BookVisit";
        }
        
        Doctor doctor = doctorService.getDoctorById(doctorId);
        appointmentService.bookAppointment(appointment, doctor, (Patient) patient);
        
        return "redirect:/patient/appointments?success";
    }

    @PostMapping("/appointments/{id}/cancel")
    public String cancelAppointment(@PathVariable Long id, HttpSession session) {
        Person patient = getAuthorizedPatient(session);
        if (patient == null) {
            return "redirect:/auth/login";
        }
        Appointment appointment = appointmentService.getAppointmentById(id);
        if (appointment == null || !appointment.getPatient().getPersonId().equals(patient.getPersonId())) {
            return "redirect:/patient/appointments"; // Unauthorized
        }

        appointmentService.deleteAppointment(id);
        return "redirect:/patient/appointments?cancelled";
    }
}
