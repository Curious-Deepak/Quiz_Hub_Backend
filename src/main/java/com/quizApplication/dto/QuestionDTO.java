package com.quizApplication.dto;


import java.util.List;
import lombok.*;



@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class QuestionDTO {

    private Integer questionId;
    private String questionText;
    private List<OptionDTO> options;


}