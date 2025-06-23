package com.example.demo.Entity;

import java.time.LocalDate;
import java.util.Date;

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
}