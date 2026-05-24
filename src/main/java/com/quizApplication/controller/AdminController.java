package com.quizApplication.controller;


import com.quizApplication.dto.AdminDTO;
import com.quizApplication.dto.QuizUpdateDTO;
import com.quizApplication.dto.UserDTO;
import com.quizApplication.entity.Quiz;
import com.quizApplication.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.quizApplication.service.QuizService;
import java.util.List;



@RestController
@RequestMapping("/admin")
@CrossOrigin
public class AdminController {

    @Autowired
    private QuizService quizService;

    @Autowired
    private AdminService adminService;

    @GetMapping("/dashboard")
    public AdminDTO getDashboardStats() {
        return adminService.getDashboardStats();
    }

    @GetMapping("/active-quizzes")
    public List<Quiz> getRecentQuizzes() {
        return adminService.getActiveQuizzes();
    }

    @GetMapping("/users")
    public List<UserDTO> getUsers() {
        return adminService.getAllUsers();
    }

    @PatchMapping("/quiz/update")
    public Quiz updateQuiz(@RequestBody QuizUpdateDTO dto) {
        return quizService.updateQuiz(dto);
    }

}