package com.quizApplication.repository;


import com.quizApplication.entity.Result;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;


public interface LeaderboardRepository extends JpaRepository<Result, Integer> {

    List<Result> findBySubmittedAtBetween(LocalDateTime start, LocalDateTime end);


    // Overall
    @Query("""
    SELECT u.userId,
           u.firstName,
           u.lastName,
           SUM(r.points),
           SUM(r.correct),
           SUM(r.wrong),
           COUNT(DISTINCT r.quiz.quizId)
    FROM Result r
    JOIN r.user u
    GROUP BY u.userId, u.firstName, u.lastName
    ORDER BY SUM(r.points) DESC
    """)
    List<Object[]> getOverallLeaderboard();

    // Today
    @Query("""
    SELECT u.userId,
           u.firstName,
           u.lastName,
           SUM(r.points),
           SUM(r.correct),
           SUM(r.wrong),
           COUNT(DISTINCT r.quiz.quizId)
    FROM Result r
    JOIN r.user u
    WHERE r.submittedAt >= :start AND r.submittedAt < :end
    GROUP BY u.userId, u.firstName, u.lastName
    ORDER BY SUM(r.points) DESC
    """)
    List<Object[]> getTodayLeaderboard(LocalDateTime start, LocalDateTime end);

    // Yesterday
    @Query("""
        SELECT u.userId,
               u.firstName,
               u.lastName,
               SUM(r.points),
               SUM(r.correct),
               SUM(r.wrong),
               COUNT(DISTINCT r.quiz.quizId)
        FROM Result r
        JOIN r.user u
        WHERE r.submittedAt >= :start AND r.submittedAt < :end
        GROUP BY u.userId, u.firstName, u.lastName
        ORDER BY SUM(r.points) DESC
    """)
    List<Object[]> getLeaderboardBetween(LocalDateTime start, LocalDateTime end);

    // Weekly
    @Query("""
        SELECT u.userId,
               u.firstName,
               u.lastName,
               SUM(r.points),
               SUM(r.correct),
               SUM(r.wrong),
               COUNT(DISTINCT r.quiz.quizId)
        FROM Result r
        JOIN r.user u
        WHERE r.submittedAt >= :start
        GROUP BY u.userId, u.firstName, u.lastName
        ORDER BY SUM(r.points) DESC
    """)
    List<Object[]> getWeeklyLeaderboard(LocalDateTime start);
}
