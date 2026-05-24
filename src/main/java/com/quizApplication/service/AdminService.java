package com.quizApplication.service;


import com.quizApplication.dto.AdminDTO;
import com.quizApplication.dto.UserDTO;
import com.quizApplication.entity.Quiz;
import com.quizApplication.entity.User;
import com.quizApplication.repository.LeaderboardRepository;
import com.quizApplication.repository.QuizRepository;
import com.quizApplication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;



@Service
public class AdminService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private LeaderboardRepository leaderboardRepository;

    public AdminDTO getDashboardStats() {

        AdminDTO dto = new AdminDTO();

        // ONLY USERS (ROLE = USER)
        dto.setTotalUsers(userRepository.countNormalUsers());
        // TOTAL QUIZZES
        dto.setTotalQuizzes(quizRepository.count());
        // CLOSED QUIZZES
        dto.setClosedQuizzes(quizRepository.countClosedQuizzes(LocalDate.now()));
        // LEADERBOARD ENTRIES
        dto.setLeaderboardEntries(leaderboardRepository.count());
        return dto;
    }

    public List<Quiz> getActiveQuizzes() {
        return quizRepository.findActiveQuizzes(LocalDate.now());
    }

    public List<UserDTO> getAllUsers() {

        List<User> users =
                userRepository.findByRole("USER");

        return users.stream().map(user -> {

            UserDTO dto = new UserDTO();

            dto.setUserId(user.getUserId());
            dto.setName(
                    user.getFirstName() + " " + user.getLastName()
            );
            dto.setEmail(user.getEmail());
            dto.setCreatedAt(user.getCreatedAt());
            return dto;

        }).toList();
    }

}