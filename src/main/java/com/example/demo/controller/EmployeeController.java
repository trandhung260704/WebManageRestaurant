package com.example.demo.controller;

import com.example.demo.Entity.*;
import com.example.demo.repository.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class EmployeeController {
    private final EmployeeRepository employeeRepo;
    
    public EmployeeController(EmployeeRepository employeeRepo) {
        this.employeeRepo = employeeRepo;
    }
    @GetMapping("/employees")
    public ResponseEntity<List<Employee>> getAllEmployees() {
        return ResponseEntity.ok(employeeRepo.findAll());
    }
    
    @GetMapping("/employees/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable int id) {
        return employeeRepo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping("/employees")
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
        return ResponseEntity.ok(employeeRepo.save(employee));
    }
    
    @DeleteMapping("/employees/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable int id) {
        if (employeeRepo.existsById(id)) {
            employeeRepo.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/employees/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable int id, @RequestBody Employee updatedEmployee) {
        return employeeRepo.findById(id)
                .map(employee -> {
                    employee.setIdemployee(updatedEmployee.getIdemployee());
                    employee.setIdperson(updatedEmployee.getIdperson());
                    employee.setSalary(updatedEmployee.getSalary());
                    employee.setPosition(updatedEmployee.getPosition());
                    return ResponseEntity.ok(employeeRepo.save(employee));
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
