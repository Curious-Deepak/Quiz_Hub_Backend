package com.quizApplication.dto;

import lombok.Data;


@Data
public class ProfileDTO {

    private Integer userId;
    private String fullName;
    private String email;
    private Integer totalPoints;
    private Integer totalQuizzes;

    private String badgeLevel;
}