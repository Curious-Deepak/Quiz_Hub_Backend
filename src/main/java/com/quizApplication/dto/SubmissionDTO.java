package com.quizApplication.dto;



import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubmissionDTO {

    private Integer questionId;
    private Integer selectedOptionId;
}
