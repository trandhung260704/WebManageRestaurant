package com.example.demo.Entity;

import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "\"person\"")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idperson;

    private String username;
    private String password;
    private boolean role;
    private String name;
    private LocalDate birthday;
    private String phonenumber;
    private String email;
    private String gender;

    public Person() {
    }

    public Person(String name, String email, boolean role) {
        this.name = name;
        this.email = email;
        this.role = role;
        this.username = email;
        this.password = "google";
    }
}
