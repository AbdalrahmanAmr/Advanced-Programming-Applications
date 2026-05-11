package dev.mr3.sb.service;

import dev.mr3.sb.model.Person;
import dev.mr3.sb.repository.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);
    private final PersonRepository personRepository;

    public AuthService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public Person validateLogin(String username, String password) {
        Optional<Person> person = personRepository.findByUsernameAndPassword(username, password);
        if (person.isPresent()) {
            Person user = person.get();
            logger.info("Login successful: username={}, role={}", username, user.getRole());
            return user;
        } else {
            logger.warn("Login failed: username={}", username);
            throw new RuntimeException("Invalid username or password");
        }
    }
}
