package dev.mr3.sb.service;

import dev.mr3.sb.model.User;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    public boolean registerUser(User user){
        return false;
    };
    public boolean authenticateUser(User user) {
        return false;
    };
    public boolean getUserById(User user){
        return false;
    };
    public boolean updateUserProfile(User user){
        return false;
    };
    public boolean deleteUser(User user){
        return false;
    };


    public boolean validateLogin(User user) {
        System.out.println("User object: " + user);
        if (user == null) {
            System.out.println("User is null");
            return false;
        }

        System.out.println("Username: '" + user.getUsername() + "'");
        System.out.println("Password: '" + user.getPassword() + "'");

        if (user.getUsername() == null || user.getPassword() == null) {
            System.out.println("Username or password is null");
            return false;
        }

        if (user.getUsername().isEmpty() || user.getPassword().isEmpty()) {
            System.out.println("Username or password is empty");
            return false;
        }

        if (user.getUsername().length() < 3 || user.getPassword().length() < 6) {
            System.out.println("Length too short. Username length: " + user.getUsername().length() + ", Password length: " + user.getPassword().length());
            return false;
        }

        if (user.getUsername().contains(" ") || user.getPassword().contains(" ")) {
            System.out.println("Contains spaces");
            return false;
        }

        System.out.println("Validation passed!");
        // if user are stored in database,
        return true;
    }
}
