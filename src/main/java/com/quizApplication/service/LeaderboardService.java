package com.quizApplication.service;


import com.quizApplication.dto.LeaderboardDTO;
import com.quizApplication.repository.LeaderboardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.*;



@Service
public class LeaderboardService {

    @Autowired
    private LeaderboardRepository leaderboardRepository;


    public List<LeaderboardDTO> getLeaderboardByFilter(String filter) {

        List<Object[]> results;

        switch (filter.toLowerCase()) {

            case "today": {
                LocalDateTime start = LocalDate.now().atStartOfDay();
                LocalDateTime end = start.plusDays(1);
                results = leaderboardRepository.getTodayLeaderboard(start, end);
                break;
            }

            case "previous": {
                LocalDateTime start = LocalDate.now().minusDays(1).atStartOfDay();
                LocalDateTime end = LocalDate.now().atStartOfDay();
                results = leaderboardRepository.getLeaderboardBetween(start, end);
                break;
            }

            case "week": {
                LocalDateTime start = LocalDate.now().minusDays(7).atStartOfDay();
                LocalDateTime end = LocalDateTime.now();
                results = leaderboardRepository.getWeeklyLeaderboard(start);
                break;
            }

            default:
                results = leaderboardRepository.getOverallLeaderboard();
        }

        // Group by user
        List<LeaderboardDTO> leaderboard = new ArrayList<>();

        for (Object[] row : results) {

            Integer userId = (Integer) row[0];
            String firstName = (String) row[1];
            String lastName = (String) row[2];
            Integer points = ((Number) row[3]).intValue();
            Integer correct = ((Number) row[4]).intValue();
            Integer wrong = ((Number) row[5]).intValue();
            Integer qcount = row[6] != null ? ((Number) row[6]).intValue() : 0;

            int totalQuestions = correct + wrong;

            double accuracy = 0.0;
            if (totalQuestions > 0) {
                accuracy = (correct * 100.0) / totalQuestions;
            }

            LeaderboardDTO dto = new LeaderboardDTO();
            dto.setUserId(userId);
            dto.setPlayerName(firstName + " " + lastName);
            dto.setPoints(points);
            dto.setTotalCorrect(correct);
            dto.setTotalWrong(wrong);
            dto.setTotalQuestions(totalQuestions);
            dto.setAccuracy(Double.parseDouble(String.format("%.2f", accuracy)));
            dto.setQcount(qcount);

            leaderboard.add(dto);
        }

        // Sorting data
        leaderboard.sort((a, b) -> {

            int cmp = b.getPoints().compareTo(a.getPoints());
            if (cmp != 0) return cmp;

            cmp = b.getAccuracy().compareTo(a.getAccuracy());
            if (cmp != 0) return cmp;

            return b.getQcount().compareTo(a.getQcount());
        });

        // Ranking
        if (!leaderboard.isEmpty()) {

            int rank = 1;
            leaderboard.get(0).setRank(rank);

            for (int i = 1; i < leaderboard.size(); i++) {

                LeaderboardDTO prev = leaderboard.get(i - 1);
                LeaderboardDTO curr = leaderboard.get(i);

                boolean sameRank =
                        prev.getPoints().equals(curr.getPoints()) &&
                                prev.getAccuracy().equals(curr.getAccuracy()) &&
                                prev.getQcount().equals(curr.getQcount());

                if (sameRank) {
                    curr.setRank(rank);
                } else {
                    rank = i + 1;
                    curr.setRank(rank);
                }
            }
        }

        return leaderboard;
    }


}