package com.quizApplication.dto;


import lombok.*;
import java.util.List;



@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class AdminCreateQuizDTO {

    private Integer quizId;
    private String questionText;
    private List<AdminOptionDTO> options;

}