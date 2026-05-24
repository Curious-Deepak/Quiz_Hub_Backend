package com.quizApplication.service;


import com.quizApplication.dto.ResultDTO;
import com.quizApplication.dto.SubmissionDTO;
import com.quizApplication.entity.*;
import com.quizApplication.repository.QuestionRepository;
import com.quizApplication.repository.QuizRepository;
import com.quizApplication.repository.ResultRepository;
import com.quizApplication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;



@Service
public class ResultService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ResultRepository resultRepository;

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private QuestionRepository questionRepository;

    public Result evaluateQuiz(ResultDTO dto) {

        List<SubmissionDTO> submissions = dto.getSubmissions();

        int correct = 0;

        for (SubmissionDTO sub : submissions) {

            Question q = questionRepository.findById(sub.getQuestionId()).orElse(null);

            if (q != null) {
                for (Option opt : q.getOptions()) {
                    if (opt.isCorrect() &&
                            opt.getOptionId().equals(sub.getSelectedOptionId())) {
                        correct++;
                    }
                }
            }
        }

        int total = submissions.size();
        int wrong = total - correct;
        int points = (correct * 10) - (wrong);
        double accuracy = total == 0 ? 0.0 : ((double) correct / total) * 100;
        accuracy = Math.round(accuracy * 100.0) / 100.0;

        Result result = new Result();

        Quiz quiz = quizRepository.findById(dto.getQuizId())
                .orElseThrow(() -> new RuntimeException("Quiz not found"));
        result.setQuiz(quiz);

        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        result.setUser(user);

        result.setPoints(points);
        result.setCorrect(correct);
        result.setWrong(wrong);
        result.setAccuracy(accuracy);

        Result savedResult = resultRepository.save(result);
        quizRepository.increaseParticipants(dto.getQuizId());

        return resultRepository.save(result);
    }


    public Optional<Result> getResult(Integer userId, Integer quizId) {
        return resultRepository.findByUser_UserIdAndQuiz_QuizId(userId, quizId);
    }



}