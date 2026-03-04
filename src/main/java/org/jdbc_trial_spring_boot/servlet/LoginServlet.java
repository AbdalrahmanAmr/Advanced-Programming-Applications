package org.jdbc_trial_spring_boot.servlet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jdbc_trial_spring_boot.dao.UserDAO;
import org.jdbc_trial_spring_boot.model.User;

import java.io.IOException;

@WebServlet(name = "loginServlet", value = "/login")
public class LoginServlet extends HttpServlet {
    private final UserDAO userDAO = new UserDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String user = request.getParameter("username");
        String pass = request.getParameter("password");

        User loginAttempt = new User(user, pass);

        response.setContentType("text/html");
        if (userDAO.loginUser(loginAttempt)) {
            response.getWriter().println("<h1>Login Successful! Welcome " + user + "</h1>");
        } else {
            response.getWriter().println("<h1>Invalid Credentials. Try again.</h1>");
        }
    }
}