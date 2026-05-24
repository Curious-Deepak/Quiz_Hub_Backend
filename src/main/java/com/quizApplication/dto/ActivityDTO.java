package com.quizApplication.dto;


import lombok.Data;
import java.time.LocalDateTime;



@Data
public class ActivityDTO {

    private String quizName;
    private LocalDateTime participatedOn;
    private Double accuracy;

}