package com.quizApplication.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultDTO {

    private Integer quizId;
    private Integer userId;
    private List<SubmissionDTO> submissions;

}