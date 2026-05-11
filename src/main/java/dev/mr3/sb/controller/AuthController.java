package dev.mr3.sb.controller;


import dev.mr3.sb.model.Person;
import dev.mr3.sb.model.Doctor;
import dev.mr3.sb.model.Patient;
import dev.mr3.sb.repository.DoctorRepository;
import dev.mr3.sb.repository.PatientRepository;
import dev.mr3.sb.service.AuthService;
import dev.mr3.sb.service.DoctorService;
import dev.mr3.sb.service.PatientService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;
    @Autowired
    private PatientService patientService;
    @Autowired
    private DoctorService doctorService;

    @GetMapping("/login")
    public String RedirectTologinPage() {
        return "Login";
    }

    @PostMapping("/login")
    public String ValidateCredential(@RequestParam String username, @RequestParam String password, HttpSession session, Model model) {
        //do I need to set session here or in service layer?
        //do I need to set cookie here or in service layer?
        try {
            Person person = authService.validateLogin(username, password);
            if (person.getRole().equals("DOCTOR")) {
                session.setAttribute("doctor", person);
                return "redirect:/doctor/dashboard";
            }else if (person.getRole().equals("PATIENT")) {
                session.setAttribute("patient", person);
                return "redirect:/patient/dashboard";
            }else
                return "index";

        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return "Login";
        }
    }

    @GetMapping("/register")
    public String RedirectSignupPage() {
        return "Signup";
    }

    @PostMapping("/register/patient")
    public String RegisterPatient(@ModelAttribute Patient patient, Model model) {

        try {
            patientService.SignupPatient(patient);
            // After successful registration.
            return "redirect:/auth/login";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "Signup";
        }
    }

    @PostMapping("/register/doctor")
    public String RegisterDoctor(@ModelAttribute Doctor doctor, Model model) {
        try {
            doctorService.SignupDoctor(doctor);
            // After successful registration.
            return "redirect:/auth/login";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "Signup";
        }

    }
        @GetMapping("/logout")
        public String Logout(HttpSession session) {
            session.invalidate();
            return "redirect:/auth/login";
        }

}