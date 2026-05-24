package com.quizApplication.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminLeaderboardDTO {

    private int rank;
    private String playerName;
    private String quizTitle;
    private Integer points;
    private Double accuracy;

}
