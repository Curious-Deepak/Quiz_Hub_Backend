package com.quizApplication.repository;


import com.quizApplication.entity.Result;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;



public interface ResultRepository extends JpaRepository<Result, Long> {


    Optional<Result> findByUser_UserIdAndQuiz_QuizId(Integer userId, Integer quizId);

    @Query("""
    SELECT r FROM Result r
    WHERE r.user.userId = :userId
    ORDER BY r.submittedAt DESC
    """)
    List<Result> findAllByUserId(Integer userId);

    // For active/past user quizzes
    List<Result> findByUser_UserIdAndSubmittedAtBetween(Integer userId, LocalDateTime start, LocalDateTime end);

    List<Result> findByUser_UserIdAndSubmittedAtBefore(Integer userId, LocalDateTime date);


}