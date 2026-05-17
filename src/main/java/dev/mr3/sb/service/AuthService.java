package dev.mr3.sb.service;

import dev.mr3.sb.model.Person;
import dev.mr3.sb.repository.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);
    private final PersonRepository personRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(PersonRepository personRepository, PasswordEncoder passwordEncoder) {
        this.personRepository = personRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Person validateLogin(String username, String password) {
        Optional<Person> personOpt = personRepository.findByUsername(username);
        if (personOpt.isPresent()) {
            Person user = personOpt.get();
            if (passwordEncoder.matches(password, user.getPassword())) {
                logger.info("Login successful: username={}, role={}", username, user.getRole());
                return user;
            }
        }
        logger.warn("Login failed: username={}", username);
        throw new RuntimeException("Invalid username or password");
    }
}
