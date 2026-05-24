package com.quizApplication.controller;


import com.quizApplication.dto.AdminCreateQuizDTO;
import com.quizApplication.dto.AdminOptionDTO;
import com.quizApplication.dto.QuestionDTO;
import com.quizApplication.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;



@RestController
@RequestMapping("/questions")
@CrossOrigin
public class QuestionController {


    @Autowired
    private QuestionService questionService;

    @GetMapping("/quiz/{quizId}")
    public List<QuestionDTO> getQuestions(@PathVariable Integer quizId) {
        return questionService.getQuestionsByQuizId(quizId);
    }

    @GetMapping("/check/{quizId}")
    public ResponseEntity<?> checkQuiz(@PathVariable Integer quizId) {
        return ResponseEntity.ok(
                questionService.checkQuizStatus(quizId)
        );
    }

    @PostMapping("/add")
    public ResponseEntity<?> addQuestions(
            @RequestBody List<AdminCreateQuizDTO> request) {
        questionService.saveQuestions(request);

        return ResponseEntity.ok("Questions saved successfully");
    }




}
