package com.quizApplication.repository;


import com.quizApplication.entity.Quiz;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;



@Repository
public interface QuizRepository extends JpaRepository<Quiz, Integer> {

    // Latest Quizzes
    List<Quiz> findByEndDateGreaterThanEqualOrderByQuizIdDesc(LocalDate today);

    // onGoing Quizzes
    @Query("SELECT q FROM Quiz q WHERE :today BETWEEN q.startDate AND q.endDate")
    List<Quiz> findOngoingQuizzes(@Param("today") LocalDate today);

    // Closed Quizzes
    @Query("SELECT q FROM Quiz q WHERE q.endDate < CURRENT_DATE")
    List<Quiz> findClosedQuizzes();

    // Closed Quizzes Count
    @Query("SELECT COUNT(q) FROM Quiz q WHERE q.endDate < :today")
    long countClosedQuizzes(@Param("today") LocalDate today);

    // Recent Quizzes For Admin Dashboard
    @Query("SELECT q FROM Quiz q WHERE q.endDate >= :today ORDER BY q.quizId DESC")
    List<Quiz> findActiveQuizzes(@Param("today") LocalDate today);

    // Participants
    @Modifying
    @Transactional
    @Query("""
           UPDATE Quiz q
           SET q.participants = q.participants + 1
           WHERE q.quizId = :quizId
           """)
    void increaseParticipants(@Param("quizId") Integer quizId);


}