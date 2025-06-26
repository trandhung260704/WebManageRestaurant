package com.example.demo.controller;

import com.example.demo.Entity.Person;
import com.example.demo.repository.PersonRepository;
import com.example.demo.util.JwtUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RequiredArgsConstructor
public class LoginController {

    private final PersonRepository personRepo;
    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginData) {
        String username = loginData.get("username");
        String password = loginData.get("password");

        Person person = personRepo.findByUsernameAndPassword(username, password);
        if (person != null) {
            Map<String, Object> claims = Map.of(
                    "idPerson", person.getIdperson(),
                    "username", person.getUsername(),
                    "role", person.isRole()
            );
            String token = jwtUtil.generateToken(claims, String.valueOf(person.getIdperson()));

            return ResponseEntity.ok(Map.of(
                    "token", token,
                    "username", person.getUsername(),
                    "role", person.isRole()
            ));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Tài khoản hoặc mật khẩu sai");
        }
    }

    @GetMapping("/me")
    public ResponseEntity<?> getUserInfo() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !(auth.getPrincipal() instanceof Person person)) {
            return ResponseEntity.status(401).body("Chưa đăng nhập");
        }

        return ResponseEntity.ok(Map.of(
                "idPerson", person.getIdperson(),
                "username", person.getUsername(),
                "role", person.isRole()
        ));
    }

//    @PostMapping("/logout")
//    public ResponseEntity<?> logout(HttpServletResponse response, HttpSession session) {
//        session.invalidate();
//
//        Cookie userIdCookie = new Cookie("userId", null);
//        userIdCookie.setHttpOnly(true);
//        userIdCookie.setPath("/");
//        userIdCookie.setMaxAge(0); // Xoá cookie
//        userIdCookie.setSecure(false);
//        userIdCookie.setDomain("localhost");
//        response.addCookie(userIdCookie);
//
//        Cookie tokenCookie = new Cookie("token", null);
//        tokenCookie.setHttpOnly(true);
//        tokenCookie.setPath("/");
//        tokenCookie.setMaxAge(0);
//        tokenCookie.setSecure(false);
//        tokenCookie.setDomain("localhost");
//        response.addCookie(tokenCookie);
//
//        return ResponseEntity.ok("Đăng xuất thành công");
//    }
}
