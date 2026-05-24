package com.quizApplication.service;


import com.quizApplication.dto.AdminCreateQuizDTO;
import com.quizApplication.dto.AdminOptionDTO;
import com.quizApplication.dto.OptionDTO;
import com.quizApplication.dto.QuestionDTO;
import com.quizApplication.entity.Option;
import com.quizApplication.entity.Question;
import com.quizApplication.entity.Quiz;
import com.quizApplication.repository.QuestionRepository;
import com.quizApplication.repository.QuizRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service
public class QuestionService {

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private QuestionRepository questionRepository;


    public List<QuestionDTO> getQuestionsByQuizId(Integer quizId) {

        List<Question> questions = questionRepository.findByQuiz_QuizId(quizId);

        return questions.stream().map(question -> {

            List<OptionDTO> optionDTOs = question.getOptions().stream()
                    .map(option -> OptionDTO.builder()
                            .optionId(option.getOptionId())
                            .optionText(option.getOptionText())
                            .build())
                    .toList();

            return QuestionDTO.builder()
                    .questionId(question.getQuestionId())
                    .questionText(question.getQuestionText())
                    .options(optionDTOs)
                    .build();

        }).toList();
    }

    // Check Quiz & Questions Existence
    public Map<String, Object> checkQuizStatus(Integer quizId) {

        Quiz quiz = quizRepository.findById(quizId).orElse(null);

        if (quiz == null) {
            return Map.of(
                    "success", false,
                    "message", "Quiz not found"
            );
        }

        boolean hasQuestions = questionRepository.existsByQuiz_QuizId(quizId);

        if (hasQuestions) {
            return Map.of(
                    "success", false,
                    "message", "Questions already exist for this quiz"
            );
        }

        return Map.of(
                "success", true,
                "quizTitle", quiz.getTitle(),
                "totalQuestions", quiz.getTotalQuestions(),
                "currentCount", questionRepository.countByQuiz_QuizId(quizId)
        );
    }

    //Save Q & Options
    @Transactional
    public void saveQuestions(List<AdminCreateQuizDTO> request) {

        if (request == null || request.isEmpty()) {
            throw new RuntimeException("No questions provided");
        }

        Integer quizId = request.get(0).getQuizId();

        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new RuntimeException("Quiz not found"));

        int existingCount = questionRepository.countByQuiz_QuizId(quizId);
        int incomingCount = request.size();
        int maxAllowed = quiz.getTotalQuestions();

        if (existingCount + incomingCount > maxAllowed) {
            throw new RuntimeException(
                    "Question limit exceeded! Allowed: " + maxAllowed +
                            ", Already present: " + existingCount +
                            ", Trying to add: " + incomingCount
            );
        }

        for (AdminCreateQuizDTO dto : request) {

            Question question = new Question();
            question.setQuestionText(dto.getQuestionText());
            question.setQuiz(quiz);

            List<Option> optionList = new ArrayList<>();

            for (AdminOptionDTO opt : dto.getOptions()) {

                Option option = new Option();
                option.setOptionText(opt.getOptionText());
                option.setIsCorrect(opt.getIsCorrect());
                option.setQuestion(question);
                optionList.add(option);
            }
            question.setOptions(optionList);
            questionRepository.save(question);
        }
    }

}
