package dev.mr3.sb.model;

import jakarta.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorValue("PERSON")
public abstract class Person {
    @Id
    @GeneratedValue
    private Long personId;

    @Column(unique = true)
    private String username;

    @Column
    private String password;

    @Column(unique = true)
    private String email;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column
    private int age;

    @Column
    private boolean gender;

    @Column
    private String phone;


    // Abstract method
    public abstract String getRole();

    // Common getters/setters...
}