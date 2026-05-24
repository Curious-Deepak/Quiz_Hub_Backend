package com.quizApplication.service;


import com.quizApplication.dto.QuizUpdateDTO;
import com.quizApplication.entity.Quiz;
import com.quizApplication.repository.QuizRepository;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



@Service
public class QuizService {

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    //  Push live updates
    public void pushQuizUpdate() {
        Map<String, Object> data = new HashMap<>();

        data.put("latest", getLatestQuizzes());
        data.put("ongoing", getOngoingQuizzes());

        messagingTemplate.convertAndSend("/live/quizzes", (Object) data);
    }

    // Save and Notify
    public Quiz saveQuizData(Quiz quiz) {
        Quiz saved = quizRepository.save(quiz);
        pushQuizUpdate();
        return saved;
    }


    // API's
    public List<Quiz> getAllQuizzes() {
        return quizRepository.findAll();
    }

    public List<Quiz> getLatestQuizzes() {
        return quizRepository.findByEndDateGreaterThanEqualOrderByQuizIdDesc(LocalDate.now());
    }

    public List<Quiz> getOngoingQuizzes() {
        return quizRepository.findOngoingQuizzes(LocalDate.now());
    }

    public List<Quiz> getClosedQuizzes() {
        return quizRepository.findClosedQuizzes();
    }

    public Quiz getQuiz(Integer id) {
        return quizRepository.findById(id).orElse(null);
    }

    @PostConstruct
    public void debugData() {
        System.out.println("TOTAL QUIZ = " + quizRepository.count());
        System.out.println("ALL QUIZ = " + quizRepository.findAll());
    }

    public Quiz saveQuiz(Quiz quiz) {
        return quizRepository.save(quiz);
    }

    @Transactional
    public Quiz updateQuiz(QuizUpdateDTO dto) {

        Quiz quiz = quizRepository.findById(dto.getQuizId())
                .orElseThrow(() -> new RuntimeException("Quiz not found"));

        if (dto.getTitle() != null) {
            quiz.setTitle(dto.getTitle());
        }

        if (dto.getEndDate() != null) {
            quiz.setEndDate(dto.getEndDate());
        }

        if (dto.getDuration() != null) {
            quiz.setDuration(dto.getDuration());
        }

        if (dto.getAuthor() != null)
            quiz.setAuthor(dto.getAuthor());

        return quizRepository.save(quiz);
    }

}

