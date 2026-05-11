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
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
            System.out.println("Login failed: " + e.getMessage());
            model.addAttribute("error", e.getMessage());
            return "Login";
        }
    }

    @GetMapping("/register")
    public String RedirectSignupPage(Model model) {
        model.addAttribute("patient", new Patient());
        model.addAttribute("doctor", new Doctor());
        return "Signup";
    }

    @PostMapping("/register/patient")
    public String RegisterPatient(@Valid @ModelAttribute Patient patient, BindingResult result, Model model ) {
        if (result.hasErrors()) {
            model.addAttribute("doctor", new Doctor()); //
            return "Signup";}
        try {
            patientService.SignupPatient(patient);
            // After successful registration.
            return "redirect:/auth/login";
        } catch (Exception e) {
            System.out.println("Patient registration failed: " + e.getMessage());
            model.addAttribute("error", e.getMessage());
            model.addAttribute("doctor", new Doctor()); //
            return "Signup";
        }
    }

    @PostMapping("/register/doctor")
    public String RegisterDoctor(@Valid @ModelAttribute Doctor doctor, BindingResult result, Model model ) {
        if (result.hasErrors()) {
            model.addAttribute("patient", new Patient()); //
            return "Signup";}
        try {
            doctorService.SignupDoctor(doctor);
            // After successful registration.
            return "redirect:/auth/login";
        } catch (Exception e) {
            System.out.println("Doctor registration failed: " + e.getMessage());
            model.addAttribute("error", e.getMessage());
            model.addAttribute("patient", new Patient()); //
            return "Signup";
        }

    }
        @GetMapping("/logout")
        public String Logout(HttpSession session) {
            session.invalidate();
            return "redirect:/auth/login";
        }

}