package dev.mr3.sb.controller;

import dev.mr3.sb.model.Person;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/patient")
public class PatientController {

        @GetMapping ("/dashboard")
        public String patientDashboard(HttpSession session , Model model) {
            Person doctor = (Person) session.getAttribute("patient");
            if (doctor == null || !doctor.getRole().equals("PATIENT")) {
                return "redirect:/auth/login";
            }
            model.addAttribute("patient", doctor);
            return "PatientDashboard";
        }
}
