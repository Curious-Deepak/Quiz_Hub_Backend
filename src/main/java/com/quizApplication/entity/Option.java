package com.quizApplication.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;



@Entity
@Table(name = "options")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "question")

public class Option {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "option_id")
    private Integer optionId;

    @Column(name = "option_text")
    private String optionText;

    @Column(name = "is_correct")
    private boolean isCorrect;

    @ManyToOne
    @JoinColumn(name = "question_id")
    @JsonBackReference
    private Question question;

    public void setIsCorrect(boolean isCorrect) {
        this.isCorrect = isCorrect;
    }

}
