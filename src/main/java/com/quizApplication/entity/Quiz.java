package com.quizApplication.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "quiz")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "quiz_id")
    private Integer quizId;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    private int totalQuestions;
    private int duration;
    private int participants;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate startDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate endDate;

    private String author;
    private String imageUrl;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "created_by")
    private User createdBy;


}
