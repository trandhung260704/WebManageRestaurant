package com.example.demo.controller;

import com.example.demo.Entity.Person;
import com.example.demo.google.LoginGoogle;
import com.example.demo.repository.PersonRepository;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RestController
@RequestMapping("/api/auth/google")
public class GoogleAuthController {
    private final PersonRepository personRepository;

    public GoogleAuthController(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @PostMapping("/callback")
    public ResponseEntity<?> googleCallback(@RequestBody Map<String, String> body) {
        String token = body.get("token");

        try {
            GoogleIdToken.Payload payload = LoginGoogle.verifyToken(token);

            if (payload == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid ID token");
            }

            String email = payload.getEmail();
            String name = (String) payload.get("name");

            Person person = personRepository.findByEmail(email)
                    .orElseGet(() -> personRepository.save(new Person(name, email, false)));

            return ResponseEntity.ok(person);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Token verification failed");
        }
    }

}
