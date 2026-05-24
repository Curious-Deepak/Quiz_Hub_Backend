package com.quizApplication.dto;


import lombok.*;
import java.util.List;



@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OptionDTO {

    private Integer optionId;
    private String optionText;


}
