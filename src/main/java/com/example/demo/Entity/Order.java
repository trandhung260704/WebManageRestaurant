package com.example.demo.Entity;

import java.sql.Timestamp;
import java.time.LocalDate;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "\"orders\"")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idorder;
    private int idperson;
    private Timestamp orderdate;
    private String status;
    private String paymentstatus;
}
