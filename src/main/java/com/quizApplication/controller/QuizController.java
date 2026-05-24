package com.quizApplication.controller;


import com.quizApplication.entity.Quiz;
import com.quizApplication.entity.User;
import com.quizApplication.repository.UserRepository;
import com.quizApplication.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;



@RestController
@RequestMapping("/quiz")
@CrossOrigin
public class QuizController {

    @Autowired
    private QuizService quizService;

    @Autowired
    private UserRepository userRepository;


    @GetMapping("/all")
    public List<Quiz> getAllQuizzes() {
        return quizService.getAllQuizzes();
    }

    @GetMapping("/latest")
    public List<Quiz> latest(){
        return quizService.getLatestQuizzes();
    }

    @GetMapping("/ongoing")
    public List<Quiz> onGoing(){
        return quizService.getOngoingQuizzes();
    }

    @GetMapping("/closed")
    public List<Quiz> closed(){
        return quizService.getClosedQuizzes();
    }

    @GetMapping("/{id}")
    public Quiz getQuiz(@PathVariable Integer id ){
        return quizService.getQuiz(id);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createQuiz(
            @RequestParam("title") String title,
            @RequestParam("author") String author,
            @RequestParam("description") String description,
            @RequestParam("noOfQuestions") int noOfQuestions,
            @RequestParam("duration") int duration,
            @RequestParam("startDate") String startDate,
            @RequestParam("endDate") String endDate,
            @RequestParam("image") MultipartFile image,
            Principal principal
    ) {

        try {

            String email = principal.getName();
            User admin = userRepository
                    .findByEmail(email)
                    .orElseThrow();

            // FORMATTING

            title = convertToTitleCase(title);
            author = convertToTitleCase(author);
            description = convertToSentenceCase(description);

            //Validations

            if (noOfQuestions < 10) {
                return ResponseEntity
                        .badRequest()
                        .body("Questions cannot be less than 10");
            }

            if (duration < 120) {
                return ResponseEntity
                        .badRequest()
                        .body("Duration cannot be less than 120 seconds");
            }

            // Date validation
            LocalDate today = LocalDate.now();
            LocalDate start = LocalDate.parse(startDate);
            LocalDate end = LocalDate.parse(endDate);
            // Start date check
            if (start.isBefore(today)) {
                return ResponseEntity
                        .badRequest()
                        .body("Start date cannot be before today");
            }
            // End date check
            if (end.isBefore(start)) {
                return ResponseEntity
                        .badRequest()
                        .body("End date cannot be before start date");
            }

            // IMAGE LOGIC
            String originalFileName = image.getOriginalFilename();
            // Generate random prefix
            String randomPrefix = generateRandomLetters();
            // Final file name
            String finalFileName = randomPrefix + "_" + originalFileName;
            System.out.println("Final File Name: " + finalFileName);

            // Upload folder
            String uploadDir = "uploads/assets/";
            File directory = new File(uploadDir);
            if (!directory.exists()) {
                directory.mkdirs();
            }
            // Full path
            Path filePath = Paths.get(uploadDir, finalFileName);
            // Save image
            Files.copy(image.getInputStream(), filePath);
            // Generate image URL
            String imageUrl = "http://localhost:8080/assets/" + finalFileName;

            // Create quiz object

            Quiz quiz = new Quiz();
            quiz.setTitle(title);
            quiz.setAuthor(author);
            quiz.setDescription(description);
            quiz.setTotalQuestions(noOfQuestions);
            quiz.setDuration(duration);
            quiz.setParticipants(0);
            quiz.setStartDate(start);
            quiz.setEndDate(end);
            quiz.setImageUrl(imageUrl);
            quiz.setCreatedBy(admin);

            // SAVE QUIZ
            quizService.saveQuiz(quiz);

            System.out.println("Quiz Saved Successfully");
            return ResponseEntity.ok("Quiz Created Successfully");
        }

        catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity
                    .badRequest()
                    .body("Image Upload Failed");
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity
                    .badRequest()
                    .body("Quiz Creation Failed");
        }

    }

    private String convertToTitleCase(String text) {
        String[] words = text.toLowerCase().split(" ");
        StringBuilder result = new StringBuilder();

        for (String word : words) {
            if (!word.isEmpty()) {
                result.append(
                        Character.toUpperCase(
                                word.charAt(0)
                        )
                );
                result.append(
                        word.substring(1)
                );
                result.append(" ");
            }
        }
        return result.toString().trim();
    }

    private String convertToSentenceCase(String text) {
        text = text.toLowerCase();

        return Character.toUpperCase(
                text.charAt(0)
        ) + text.substring(1);
    }

    private String generateRandomLetters() {
        String letters = "abcdefghijklmnopqrstuvwxyz";
        Random random = new Random();
        char first = letters.charAt(random.nextInt(26));
        char second = letters.charAt(random.nextInt(26));
        return "" + first + second;
    }


}
