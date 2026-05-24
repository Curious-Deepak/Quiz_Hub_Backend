package com.quizApplication.controller;


import com.quizApplication.dto.ResultDTO;
import com.quizApplication.entity.Result;
import com.quizApplication.repository.UserRepository;
import com.quizApplication.security.JwtUtil;
import com.quizApplication.service.ResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;



@RestController
@RequestMapping("/result")
@CrossOrigin
public class ResultController {

    @Autowired
    private ResultService resultService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    private String extractToken(String token) {
        if (token != null && token.startsWith("Bearer ")) {
            return token.substring(7);
        }
        throw new RuntimeException("Invalid token");
    }

    @PostMapping("/submit")
    public Result submit(
            @RequestBody ResultDTO dto,
            @RequestHeader("Authorization") String token) {

        String email = jwtUtil.extractEmail(extractToken(token));
        Integer userId = userRepository.findByEmail(email)
                .orElseThrow()
                .getUserId();

        dto.setUserId(userId);

        return resultService.evaluateQuiz(dto);
    }

    @GetMapping("/status")
    public Map<String, Object> getStatus(
            @RequestParam Integer quizId,
            @RequestHeader("Authorization") String token) {

        Map<String, Object> response = new HashMap<>();

        String email = jwtUtil.extractEmail(extractToken(token));
        Integer userId = userRepository.findByEmail(email)
                .orElseThrow()
                .getUserId();

        Optional<Result> result = resultService.getResult(userId, quizId);

        response.put("submitted", result.isPresent());

        result.ifPresent(value -> response.put("result", value));

        return response;
    }


}