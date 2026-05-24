package com.quizApplication.repository;


import com.quizApplication.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer> {

    List<Question>  findByQuiz_QuizId(Integer quizId);

    boolean existsByQuiz_QuizId(Integer quizId);

    int countByQuiz_QuizId(Integer quizId);


}
