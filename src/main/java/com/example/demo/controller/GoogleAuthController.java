package com.example.demo.controller;

import com.example.demo.Entity.Person;
import com.example.demo.google.LoginGoogle;
import com.example.demo.repository.PersonRepository;
import com.example.demo.util.JwtUtil;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Map;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RestController
@RequestMapping("/api/auth/google")
public class GoogleAuthController {

    private final PersonRepository personRepository;

    public GoogleAuthController(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @PostMapping("/callback")
    public ResponseEntity<?> googleCallback(@RequestBody Map<String, String> body,
                                            HttpServletResponse response,
                                            HttpSession session) {
        String token = body.get("token");

        if (token == null || token.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Token is missing"));
        }

        try {
            GoogleIdToken.Payload payload = LoginGoogle.verifyToken(token);
            if (payload == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("error", "Invalid token"));
            }

            String email = payload.getEmail();
            String name = (String) payload.get("name");

            Optional<Person> optionalPerson = personRepository.findByEmail(email);

            Person person = optionalPerson.orElseGet(() ->
                    personRepository.save(new Person(name, email, false))
            );

            session.setAttribute("idPerson", person.getIdperson());
            session.setAttribute("username", person.getUsername());
            session.setAttribute("role", person.isRole());

            Map<String, Object> claims = Map.of(
                    "idPerson", person.getIdperson(),
                    "username", person.getUsername(),
                    "role", person.isRole()
            );
            String jwtToken = JwtUtil.generateToken(claims);

            Cookie userIdCookie = new Cookie("userId", String.valueOf(person.getIdperson()));
            userIdCookie.setHttpOnly(true);
            userIdCookie.setPath("/");
            userIdCookie.setMaxAge(24 * 60 * 60); // 1 ngày
            userIdCookie.setSecure(false); // nếu dùng HTTPS thì set true
            userIdCookie.setDomain("localhost");
            response.addCookie(userIdCookie);

            Cookie tokenCookie = new Cookie("token", jwtToken);
            tokenCookie.setHttpOnly(true);
            tokenCookie.setPath("/");
            tokenCookie.setMaxAge(24 * 60 * 60);
            tokenCookie.setSecure(false);
            tokenCookie.setDomain("localhost");
            response.addCookie(tokenCookie);

            return ResponseEntity.ok(Map.of(
                    "message", "Google login success",
                    "username", person.getUsername(),
                    "role", person.isRole(),
                    "token", jwtToken
            ));

        } catch (GeneralSecurityException | IOException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Token verification failed"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Server error: " + e.getMessage()));
        }
    }
}
