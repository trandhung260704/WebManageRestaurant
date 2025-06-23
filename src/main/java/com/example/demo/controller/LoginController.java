package com.example.demo.controller;

import com.example.demo.Entity.Person;
import com.example.demo.repository.PersonRepository;
import com.example.demo.util.JwtUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RestController
@RequestMapping("/api")
public class LoginController {

    private final PersonRepository personRepo;

    public LoginController(PersonRepository personRepo) {
        this.personRepo = personRepo;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginData,
                                   HttpServletResponse response,
                                   HttpSession session) {

        String username = loginData.get("username");
        String password = loginData.get("password");

        Person person = personRepo.findByUsernameAndPassword(username, password);
        if (person != null) {

            // Lưu thông tin vào session
            session.setAttribute("idPerson", person.getIdperson());
            session.setAttribute("username", person.getUsername());
            session.setAttribute("role", person.isRole());

            // Tạo JWT token
            Map<String, Object> claims = Map.of(
                    "idPerson", person.getIdperson(),
                    "username", person.getUsername(),
                    "role", person.isRole()
            );
            String token = JwtUtil.generateToken(claims);

            // Tạo cookie
            Cookie userIdCookie = new Cookie("userId", String.valueOf(person.getIdperson()));
            userIdCookie.setHttpOnly(true);
            userIdCookie.setPath("/");
            userIdCookie.setMaxAge(24 * 60 * 60);
            userIdCookie.setSecure(false);
            userIdCookie.setDomain("localhost");
            response.addCookie(userIdCookie);

            return ResponseEntity.ok(Map.of(
                    "message", "Login success",
                    "username", person.getUsername(),
                    "role", person.isRole(),
                    "token", token
            ));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }

    @GetMapping("/me")
    public ResponseEntity<?> getSessionInfo(HttpSession session) {
        Object username = session.getAttribute("username");
        if (username != null) {
            return ResponseEntity.ok(Map.of(
                    "username", username,
                    "idPerson", session.getAttribute("idPerson"),
                    "role", session.getAttribute("role")
            ));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Chưa đăng nhập");
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse response, HttpSession session) {
        // Huỷ session
        session.invalidate();

        // Xoá cookie userId
        Cookie userIdCookie = new Cookie("userId", null);
        userIdCookie.setHttpOnly(true);
        userIdCookie.setPath("/");
        userIdCookie.setMaxAge(0); // Xoá cookie
        userIdCookie.setSecure(false);
        userIdCookie.setDomain("localhost");
        response.addCookie(userIdCookie);

        // Xoá cookie token
        Cookie tokenCookie = new Cookie("token", null);
        tokenCookie.setHttpOnly(true);
        tokenCookie.setPath("/");
        tokenCookie.setMaxAge(0);
        tokenCookie.setSecure(false);
        tokenCookie.setDomain("localhost");
        response.addCookie(tokenCookie);

        return ResponseEntity.ok("Đăng xuất thành công");
    }

}
