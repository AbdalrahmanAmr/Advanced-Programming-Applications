ADA-Project: Jakarta EE 10 Login System
A standardized, team-ready Java Web Application built with Jakarta EE 10, Maven, and the DAO pattern.

                               Quick Start Guide

1) Clone the Repository
   git clone https://github.com/AbdalrahmanAmr/Advanced-Programming-Applications.git
   cd ADA-Project
2) Database Setup (MySQL Required)
   Run the following commands inside MySQL:

CREATE DATABASE ada_db;
USE ada_db;

CREATE TABLE users (
id INT AUTO_INCREMENT PRIMARY KEY,
username VARCHAR(50) NOT NULL UNIQUE,
password VARCHAR(50) NOT NULL
);

INSERT INTO users (username, password) VALUES ('admin', '1234');


Open DBConnection.java and update the PASS variable with your local MySQL password.
   Project Architecture

*   util/DBConnection.java  → Database connection handler
*   model/User.java         → User entity (POJO)
*   dao/UserDAO.java        → SQL logic layer
*   servlet/LoginServlet.java → Controller
*   webapp/index.jsp        → Login UI

* Team Workflow Rules
- Do not push directly to main.
- Use your own branch (dev/your-name).
- Do not commit .idea/ or target/ folders.

- Update this file if database schema changes.
  Tech Stack
  Java 21
  Jakarta EE 10 (Servlet 6.1.0)
  Maven
  Apache Tomcat 10.1.x
  MySQL 9+
