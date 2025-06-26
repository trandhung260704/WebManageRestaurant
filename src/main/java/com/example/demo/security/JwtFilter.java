package com.example.demo.security;

import com.example.demo.Entity.Person;
import com.example.demo.repository.PersonRepository;
import com.example.demo.util.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final PersonRepository personRepo;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String jwt = authHeader.substring(7);
            try {
                String userId = jwtUtil.extractClaim(jwt, Claims::getSubject);
                Person person = personRepo.findById(Integer.parseInt(userId)).orElse(null);

                if (person != null && jwtUtil.isTokenValid(jwt, userId)) {
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            person, null, List.of() // bạn có thể thêm quyền nếu dùng @PreAuthorize
                    );
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } catch (Exception e) {
                // Token lỗi hoặc hết hạn
                System.out.println("JWT lỗi: " + e.getMessage());
            }
        }

        chain.doFilter(request, response);
    }
}
