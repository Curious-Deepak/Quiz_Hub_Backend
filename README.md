# QUIZ APPLICATION BACKEND DETAILS

#### Project Name : Quiz Hub - Online Quiz Tournament WEB Application

#### Backend Technology : Spring Boot

#### Database : MySQL

#### Architecture Style : REST API Based Monolithic Architecture

#### Build Tool : Maven

#### Programming Language : Java

#### Development Tools : IntelliJ IDEA, MySQL, Railway (Deployment)

---

## ----   BACKEND OVERVIEW   ----

The backend of the Quiz Application is responsible for managing quizzes, questions, 
users, quiz submissions, result generation, participant tracking, image handling, 
and real-time quiz updates. The system follows a layered architecture using 
Controller, Service, Repository, and Entity layers.

The backend provides APIs for :

* Admin quiz creation
* Quiz management
* Question and option handling
* Quiz participation
* Result calculation
* Submission validation
* Participant count updates
* Dynamic image serving
* Real-time updates using WebSocket

---

## ----   TECHNOLOGIES USED   ----

* Java
* Spring Boot
* Spring MVC
* Spring Data JPA
* JWT Auth
* Hibernate
* MySQL
* Maven
* WebSocket
* Bootstrap (Frontend Integration)
* REST APIs

---

## ---- PROJECT STRUCTURE  ----

com.quizApplication

│

├── controller

├── service

├── repository

├── entity

├── dto

├── config

└── websocket

---

## ----   LAYER DESCRIPTION   ----

### A. CONTROLLER LAYER
Handles HTTP requests and API endpoints.

Responsible for :

* Receiving requests from frontend
* Validating request data
* Sending responses
* Calling service layer methods


### B. SERVICE LAYER
Contains business logic of the application.

Responsible for :

* Quiz creation logic
* Question mapping
* Result generation
* Participant updates
* Validation rules
* WebSocket notifications


### C. REPOSITORY LAYER
Handles database operations using Spring Data JPA.

Responsible for :

* CRUD operations
* Query execution
* Entity persistence


### D. ENTITY LAYER
Represents database tables.

Main Entities :

* Quiz
* Question
* Option
* User
* Result
* Submission

---

## ----   QUIZ CREATION FLOW   ----

1. Admin creates quiz from frontend.
2. Quiz data is sent as JSON.
3. Backend receives DTO request.
4. Quiz entity is created.
5. Questions are mapped.
6. Options are linked to questions.
7. Correct answers are stored.
8. Data is saved into MySQL database.
9. Live updates are pushed using WebSocket.

---

## ----   QUIZ SUBMISSION FLOW   ----

1. User attempts quiz.
2. Selected answers are sent to backend.
3. Backend validates answers.
4. Score is calculated.
5. Result entity is created.
6. One submission per email is checked.
7. Participant count increases after successful submission.
8. Final result is returned to frontend.

---

## ----   RESULT PROCESSING   ----

The backend automatically :

* Checks correct answers
* Calculates total score
* Stores result history
* Prevents duplicate submissions
* Generates final performance summary

---

## ----   WEBSOCKET IMPLEMENTATION   ----

WebSocket is used for live quiz updates.
Purpose :

* Push new quiz updates instantly
* Update frontend without refresh
* Real-time participant visibility

Main Components :

* SimpMessagingTemplate
* WebSocket Configuration
* Topic-based messaging

Example Topic  : /topic/quizzes

---

## ----   IMAGE STORAGE SYSTEM   ----

The application uses external asset storage instead of static resource folders.

### Custom Image Upload

- The project supports dynamic image handling for quiz thumbnails
- Admin will send custom made poster/image for quiz and that will be send to backend
- On backend this images will be stored dynamically in the folder (upload/assets)
- Images will be renamed dynmically as randomAlphabets_imageName.type(eg_image.png) for handing same name clash
- The url (folder name & renamed image) will be created and sent to databse and stored in databse dynamically

Features :

* Dynamic image uploads
* External assets folder
* WebMvc resource handling
* Runtime image serving

Benefits :

* Easier deployment
* Better scalability
* Dynamic image management

---

## ----   DATA TRANSFER OBJECTS (DTOs)   ----

DTOs are used for secure and clean data transfer.

Main DTOs :

* AdminCreateQuizDTO
* QuestionDTO
* OptionDTO
* AdminOptionDTO

Purpose :

* Avoid direct entity exposure
* Simplify JSON structure
* Improve validation

---

## ----   SECURITY FEATURES   ----

Implemented/Supported Features :

* Guest access control (no authentication required, can only see homepage and quiz 
list)
* Input validation
* Duplicate submission restriction (One submission per email, per quiz)
* Secure database handling
* Controlled API access
* Backend validation before persistence

---

## ----   API FUNCTIONALITIES   ----

Main Backend Functionalities :

* Create Quiz
* Update Quiz
* Delete Quiz
* Fetch Quiz List
* Fetch Quiz Details
* Submit Quiz
* Generate Results
* Count Participants
* Upload Images
* Push Live Updates

---

## ----   PARTICIPANT COUNT LOGIC   ---- 

Participant count increases only when :

* Quiz submission is successful
* User has not already submitted
* Result is stored successfully

The backend updates the participant count directly in the quiz table.

---

## ----   JSON-BASED REQUEST STRUCTURE   ----

The application uses JSON payloads for communication between frontend and backend.

Advantages :

* Clean API structure
* Easy frontend integration
* Flexible nested question handling
* Efficient serialization/deserialization

---

## ----    EXCEPTION HANDLING   ----

The backend handles :

* Invalid quiz requests
* Missing data
* Duplicate submissions
* Database exceptions
* Invalid question mappings
* Resource not found exceptions

---

## ----    FUTURE ENHANCEMENTS    ----    

Planned Features :

* Timer synchronization
* Quiz analytics dashboard
* AI-generated questions
* Performance reports
* Email notifications
* Multi-category quiz support
* Fast update optimization

---

## ----    DEPLOYMENT SUPPORT  ----

Backend is deployed using :

* Railway

Database is hosted using :

* Railway MySQL
* MySQL Cloud Services

---

## ---- REST API ENDPOINTS (API DOCUMENTATION)  -----

#### APIs can be accessed using "url + endpoints" on webrowser or Postman as "url/endpoint".

#### # Authentication APIs

- POST	 -  /auth/register
- POST	 -  /auth/login

#### # Quiz APIs

- GET	  -  /quiz/latest
- GET	  -  /quiz/closed
- GET	  -  /quiz/ongoing
- GET	  -  /quiz/{id}

#### # Question APIs

- GET	  -  /questions/{quizId}

#### # Result APIs

- GET	  -  /result/status

#### # Leaderboard APIs

- GET	  -  /leaderboard/{quizId}

#### # Admin APIs

- POST	  -  /admin/createQuiz
- PUT	    -  /admin/updateQuiz/{id}
- DELETE	-  /admin/deleteQuiz/{id}


---

## ----    SUMMARY   ----

The Quiz Application backend is designed to provide a scalable, maintainable, and 
efficient system for online quiz management. The backend supports quiz creation, 
result processing, real-time updates, participant tracking, and structured database 
management using Spring Boot and MySQL.

---

