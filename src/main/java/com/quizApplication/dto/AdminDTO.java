package com.quizApplication.dto;


import lombok.Data;



@Data
public class AdminDTO {

    private long totalUsers;
    private long totalQuizzes;
    private long closedQuizzes;
    private long leaderboardEntries;

}
