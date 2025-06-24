package com.example.demo.controller;

import com.example.demo.Entity.Person;
import com.example.demo.google.LoginGoogle;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/auth/google")
public class GoogleAuthController {

    @GetMapping("/callback")
    public ResponseEntity<?> googleCallback(@RequestParam String code) {
        try {
            String accessToken = LoginGoogle.getAccessToken(code);
            Person user = LoginGoogle.getUserInfo(accessToken);
            return ResponseEntity.ok(user);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login Failed");
        }
    }
}
