package com.quizApplication.controller;


import com.quizApplication.dto.LeaderboardDTO;
import com.quizApplication.service.LeaderboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;



@RestController
@RequestMapping("/leaderboard")
@CrossOrigin
public class LeaderboardController {


    @Autowired
    private LeaderboardService leaderboardService;

    @GetMapping
    public List<LeaderboardDTO> getLeaderboard(
            @RequestParam(defaultValue = "overall") String filter) {
                return leaderboardService.getLeaderboardByFilter(filter);
    }


}
