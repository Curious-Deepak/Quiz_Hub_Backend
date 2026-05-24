package com.quizApplication.service;


import com.quizApplication.dto.ProfileDTO;
import com.quizApplication.dto.ActivityDTO;
import com.quizApplication.entity.Result;
import com.quizApplication.entity.User;
import com.quizApplication.entity.Quiz;
import com.quizApplication.repository.ResultRepository;
import com.quizApplication.repository.UserRepository;
import com.quizApplication.repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.*;



@Service
public class ProfileService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ResultRepository resultRepository;

    @Autowired
    private QuizRepository quizRepository;


    // Profile
    public ProfileDTO getProfile(Integer userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Result> results = resultRepository.findAllByUserId(userId);

        int totalPoints = results.stream()
                .mapToInt(Result::getPoints)
                .sum();

        int totalQuizzes = results.size();

        ProfileDTO dto = new ProfileDTO();
        dto.setUserId(userId);
        dto.setFullName(user.getFirstName() + " " + user.getLastName());
        dto.setEmail(user.getEmail());
        dto.setTotalPoints(totalPoints);
        dto.setTotalQuizzes(totalQuizzes);

        // simple badge logic
        if (totalPoints >= 10000) {
            dto.setBadgeLevel("Game Changer");
        }
        else if (totalPoints >= 7000) {
            dto.setBadgeLevel("High Performer");
        }
        else if (totalPoints >= 4000) {
            dto.setBadgeLevel("Skill Builder");
        }
        else if (totalPoints >= 2000) {
            dto.setBadgeLevel("Active Player");
        }
        else if (totalPoints >= 500) {
            dto.setBadgeLevel("Casual Player");
        }
        else {
            dto.setBadgeLevel("Fresh Start");
        }

        return dto;
    }

    // User Activity
    public List<ActivityDTO> getActivities(Integer userId, String type) {

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startOfToday = now.toLocalDate().atStartOfDay();

        List<Result> results;

        if ("active".equalsIgnoreCase(type)) {

            results = resultRepository
                    .findByUser_UserIdAndSubmittedAtBetween(userId, startOfToday, now);

        } else if ("past".equalsIgnoreCase(type)) {

            results = resultRepository
                    .findByUser_UserIdAndSubmittedAtBefore(userId, startOfToday);

        } else {
            throw new RuntimeException("Invalid type: active or past only");
        }

        return results.stream().map(r -> {

            ActivityDTO dto = new ActivityDTO();

            // fetch quiz name using quizId
            String quizName = quizRepository.findById(r.getQuiz().getQuizId())
                    .map(Quiz::getTitle)   // adjust field name if different
                    .orElse("Unknown Quiz");

            dto.setQuizName(quizName);
            dto.setParticipatedOn(r.getSubmittedAt());
            dto.setAccuracy(r.getAccuracy());

            return dto;
        }).toList();
    }

}