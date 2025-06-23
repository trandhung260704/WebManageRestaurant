package com.example.demo.repository;

import com.example.demo.Entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface LoginRepository extends JpaRepository<Person, Integer> {
    Person findByUsernameAndPassword(String username, String password);
}
