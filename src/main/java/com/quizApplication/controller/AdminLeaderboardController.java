package com.quizApplication.controller;


import com.quizApplication.dto.AdminLeaderboardDTO;
import com.quizApplication.service.AdminLeaderboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;



@RestController
@RequestMapping("/adminLeaderboard")
@CrossOrigin
public class AdminLeaderboardController {

    @Autowired
    private AdminLeaderboardService adminLeaderboardService;

    @GetMapping
    public List<AdminLeaderboardDTO> getAdminLeaderboard() {
        return adminLeaderboardService.getAdminLeaderboard();
    }

}