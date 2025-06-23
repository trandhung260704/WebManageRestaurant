    package com.example.demo.Entity;

    import jakarta.persistence.*;
    import lombok.Getter;
    import lombok.Setter;

    @Setter
    @Getter
    @Entity
    @Table(name = "\"food\"")
public class Food {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idfood;
    private String namefood;
    private int pricefood;
    private Boolean available;
    private String type;
    private String foodimage;
    private String description;

}

