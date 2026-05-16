package dev.mr3.sb.controller;


import dev.mr3.sb.model.Person;
import dev.mr3.sb.model.Doctor;
import dev.mr3.sb.model.Patient;
import dev.mr3.sb.service.AuthService;
import dev.mr3.sb.service.DoctorService;
import dev.mr3.sb.service.PatientService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import dev.mr3.sb.security.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
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
    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("/login")
    public String RedirectTologinPage() {
        return "Login";
    }

    @PostMapping("/login")
    public String ValidateCredential(@RequestParam String username, @RequestParam String password, HttpSession session, HttpServletRequest request, HttpServletResponse response, Model model) {
        try {
            Person person = authService.validateLogin(username, password);

            // Generate JWT and set in Cookie
            String token = jwtUtil.generateToken(person.getUsername(), person.getRole());
            ResponseCookie jwtCookie = ResponseCookie.from("JWT", token)
                    .httpOnly(true)
                    .secure(request.isSecure())
                    .sameSite("Lax")
                    .path("/")
                    .maxAge(jwtUtil.getExpirationTimeSeconds())
                    .build();
            response.addHeader(HttpHeaders.SET_COOKIE, jwtCookie.toString());

            if (person.getRole().equals("DOCTOR")) {
                session.setAttribute("doctor", person);
                return "redirect:/doctor/dashboard";
            } else if (person.getRole().equals("PATIENT")) {
                session.setAttribute("patient", person);
                return "redirect:/patient/dashboard";
            } else
                return "index";

        } catch (RuntimeException e) {
            System.out.println("Login failed: " + e.getMessage());
            model.addAttribute("error", e.getMessage());
            return "Login";
        }
    }

    @GetMapping("/register")
    public String RedirectSignupPage(Model model) {
        model.addAttribute("registrationDto", new dev.mr3.sb.dto.RegistrationDto());
        return "Signup";
    }

    @PostMapping("/register")
    public String Register(@Valid @ModelAttribute("registrationDto") dev.mr3.sb.dto.RegistrationDto registrationDto, BindingResult result, Model model ) {
        if (result.hasErrors()) {
            if (result.hasFieldErrors("role")) {
                model.addAttribute("error", "Please select a valid account type");
            }
            return "Signup";
        }
        try {
            if ("PATIENT".equalsIgnoreCase(registrationDto.getRole())) {
                Patient patient = new Patient();
                patient.setFirstName(registrationDto.getFirstName());
                patient.setLastName(registrationDto.getLastName());
                patient.setPatientUsername(registrationDto.getUsername());
                patient.setPassword(registrationDto.getPassword());
                patient.setEmail(registrationDto.getEmail());
                patient.setAge(registrationDto.getAge());
                patient.setPhone(registrationDto.getPhone());
                patient.setGender(registrationDto.isGender());
                patientService.SignupPatient(patient);
            } else if ("DOCTOR".equalsIgnoreCase(registrationDto.getRole())) {
                Doctor doctor = new Doctor();
                doctor.setFirstName(registrationDto.getFirstName());
                doctor.setLastName(registrationDto.getLastName());
                doctor.setDoctorUsername(registrationDto.getUsername());
                doctor.setPassword(registrationDto.getPassword());
                doctor.setEmail(registrationDto.getEmail());
                doctor.setAge(registrationDto.getAge());
                doctor.setPhone(registrationDto.getPhone());
                doctor.setGender(registrationDto.isGender());
                if (!doctorService.SignupDoctor(doctor)) {
                    model.addAttribute("error", "Registration failed");
                    return "Signup";
                }
            } else {
                model.addAttribute("error", "Please select a valid account type");
                return "Signup";
            }
            return "redirect:/auth/login";
        } catch (Exception e) {
            System.out.println("Registration failed: " + e.getMessage());
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
