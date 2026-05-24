package com.quizApplication.service;


import com.quizApplication.dto.AdminLeaderboardDTO;
import com.quizApplication.entity.Result;
import com.quizApplication.repository.ResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;



@Service
public class AdminLeaderboardService {

    @Autowired
    private ResultRepository resultRepository;

    public List<AdminLeaderboardDTO> getAdminLeaderboard() {

        List<Result> results = resultRepository.findAll();

        results.sort((a, b) -> b.getPoints().compareTo(a.getPoints()));

        List<AdminLeaderboardDTO> leaderboard = new ArrayList<>();

        int rank = 1;

        for (Result result : results) {

            AdminLeaderboardDTO dto = new AdminLeaderboardDTO();

            dto.setRank(rank++);

            dto.setPlayerName(
                    result.getUser().getFirstName() + " " +
                            result.getUser().getLastName()
            );

            dto.setQuizTitle(
                    result.getQuiz().getTitle());

            dto.setPoints(
                    result.getPoints());

            dto.setAccuracy(
                    result.getAccuracy());

            leaderboard.add(dto);
        }

        return leaderboard;
    }
}