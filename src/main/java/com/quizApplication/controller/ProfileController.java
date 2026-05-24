package com.quizApplication.controller;


import com.quizApplication.dto.ProfileDTO;
import com.quizApplication.dto.ActivityDTO;
import com.quizApplication.entity.User;
import com.quizApplication.repository.UserRepository;
import com.quizApplication.security.JwtUtil;
import com.quizApplication.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;



@RestController
@RequestMapping("/profile")
@CrossOrigin
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;


    @GetMapping
    public ProfileDTO getProfile(@RequestHeader("Authorization") String token) {

        String email = jwtUtil.extractEmail(token.substring(7));
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return profileService.getProfile(user.getUserId());
    }

    @GetMapping("/activities")
    public List<ActivityDTO> getActivities(
            @RequestHeader("Authorization") String token,
            @RequestParam String type
    ) {

        String email = jwtUtil.extractEmail(token.substring(7));
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return profileService.getActivities(user.getUserId(), type);
    }


}