package com.quizApplication.dto;



import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class LeaderboardDTO {

    private int rank;
    private String playerName;
    private Double accuracy;
    private Integer points;

    private Integer userId;
    private Integer totalCorrect;
    private Integer totalWrong;
    private Integer totalQuestions;

    private Integer qcount;

}