package dev.mr3.sb.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class User {
    @Id
    @GeneratedValue
    private Long id;
    @Column
    private String username;
    @Column
    private String password;
    @Column
    private int age;
    @Column
    private boolean gender;
    @Column
    private String phone;
    @Column
    private boolean isDoctor = false;

    //need to check if we can use
    // @OneToMany for the appointments and injuries,
    // or if we need to create a
    // separate table for them
    //and if we can use @OneToMany,
    // we need to check if we can use it
    // with a List or if we need to use a Set

    public User() {
    }
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}