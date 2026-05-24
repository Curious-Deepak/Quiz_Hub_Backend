package com.quizApplication.controller;


import com.quizApplication.dto.AuthResponseDTO;
import com.quizApplication.dto.LoginDTO;
import com.quizApplication.dto.SignupDTO;
import com.quizApplication.entity.User;
import com.quizApplication.repository.UserRepository;
import com.quizApplication.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.Map;



@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {

    @Autowired
    private UserRepository repo;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private JwtUtil jwtUtil;


    // SIGNUP
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupDTO dto) {

        if (repo.findByEmail(dto.getEmail()).isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(Map.of("message", "Email already exists"));
        }

        User user = User.builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .email(dto.getEmail())
                .password(encoder.encode(dto.getPassword()))
                .role("USER")
                .build();

        repo.save(user);

        return ResponseEntity.ok(
                Map.of("message", "User registered successfully")
        );
    }

    // LOGIN
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO dto) {

        try {

            manager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            dto.getEmail(),
                            dto.getPassword()
                    )
            );

            User user = repo.findByEmail(dto.getEmail())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            String token = jwtUtil.generateToken(
                    user.getEmail(),
                    user.getRole(),
                    dto.isRememberMe()
            );

            return ResponseEntity.ok(
                    new AuthResponseDTO(token, "Login successful")
            );

        } catch (AuthenticationException e) {

            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "Invalid username or password"));
        }
    }
}