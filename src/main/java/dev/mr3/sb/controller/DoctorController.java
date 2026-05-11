package dev.mr3.sb.controller;

import dev.mr3.sb.model.Person;
import dev.mr3.sb.service.DoctorService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/doctor")
public class DoctorController {
    private final DoctorService doctorService;
    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @GetMapping("/dashboard")
    public String doctorDashboard(HttpSession session, Model model) {
        Person doctor = (Person) session.getAttribute("doctor");
        if (doctor == null || !doctor.getRole().equals("DOCTOR")) {
            return "redirect:/auth/login";
        }
        model.addAttribute("doctor", doctor);
        return "DoctorDashboard";
    }
}
