package com.quizApplication.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class UserDTO {

    private Integer userId;
    private String name;
    private String email;
    private LocalDateTime createdAt;

}