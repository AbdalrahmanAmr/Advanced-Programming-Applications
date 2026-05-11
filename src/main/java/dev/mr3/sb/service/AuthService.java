package dev.mr3.sb.service;

import dev.mr3.sb.model.Person;
import dev.mr3.sb.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    private final PersonRepository personRepository;

    public AuthService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public Person validateLogin(String username, String password) {
        Optional<Person> person = personRepository.findByUsernameAndPassword(username, password);
        if (person.isPresent()) {
            return person.get();
        } else {
            throw new RuntimeException("Invalid username or password");
        }
    }
}
