package dev.mr3.sb.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class User {
    @Id
    @GeneratedValue
    private Long UserId;
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
    @Column
    private String email;
    @Column
    private String firstName;
    @Column
    private String lastName;


    //need to check if we can use
    // @OneToMany for the appointments and injuries,
    // or if we need to create a
    // separate table for them
    //and if we can use @OneToMany,
    // we need to check if we can use it
    // with a List or if we need to use a Set


    public User() {}
    public User(Long userId, String username, String password, int age, boolean gender, String phone, boolean isDoctor, String email, String firstName, String lastName) {
        UserId = userId;
        this.username = username;
        this.password = password;
        this.age = age;
        this.gender = gender;
        this.phone = phone;
        this.isDoctor = isDoctor;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Long getUserId() {
        return UserId;
    }
    public void setUserId(Long userId) {
        UserId = userId;
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
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public boolean isGender() {
        return gender;
    }
    public void setGender(boolean gender) {
        this.gender = gender;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public boolean isDoctor() {
        return isDoctor;
    }
    public void setDoctor(boolean doctor) {
        isDoctor = doctor;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}