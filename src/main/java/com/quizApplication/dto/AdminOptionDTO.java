package com.quizApplication.dto;


import lombok.*;



@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class AdminOptionDTO {

    private String optionText;
    private Boolean isCorrect;

}