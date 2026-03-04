package org.jdbc_trial_spring_boot.dao;

import org.jdbc_trial_spring_boot.model.User;
import org.jdbc_trial_spring_boot.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {
    public boolean loginUser(User user) {
        String query = "SELECT * FROM users WHERE username = ? AND password = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next(); // Returns true if a user is found
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}