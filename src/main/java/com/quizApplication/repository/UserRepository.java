package com.quizApplication.repository;


import com.quizApplication.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;



public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);

    // For Admin
    List<User> findByRole(String role);

    @Query("SELECT COUNT(u) FROM User u WHERE u.role = 'USER'")
    long countNormalUsers();


}