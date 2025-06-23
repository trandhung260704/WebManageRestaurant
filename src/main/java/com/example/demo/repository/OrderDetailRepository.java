package com.example.demo.repository;

import com.example.demo.Entity.*;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailRepository extends JpaRepository<Orders_Detail, Integer>{
}
