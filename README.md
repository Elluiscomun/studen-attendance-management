# Student Attendance Management

A Spring Boot API for managing student attendance records, including student registration, attendance tracking, and integration with a relational database using JPA.

## Features

- **Student Management**: Register, update, retrieve, and deactivate students.
- **Course Management**: Create, update, retrieve, and deactivate courses.
- **Attendance Tracking**: Record and retrieve student attendance for courses.
- **RESTful API**: Follows REST principles with HATEOAS support.
- **Validation**: Input validation for student and course data.
- **In-memory Database**: Uses H2 for development and testing.

## Project Structure

- `src/main/java/com/fighthub/attentance/`  
  Main application source code.
- `src/main/resources/application.properties`  
  Application configuration.
- `src/test/java/com/fighthub/attentance/`  
  Unit and integration tests.

## Getting Started

### Prerequisites

- Java 17+
- Maven

### Build & Run

```sh
./mvnw spring-boot:run
```

The API will be available at http://localhost:8080/.

## API Endpoints
### Students

```sh
GET /v1/students/ — List all students
POST /v1/students/ — Create a new student
GET /v1/students/{id} — Get student by ID
POST /v1/students/{id} — Update student by ID
DELETE /v1/students/{id} — Delete student
GET /v1/students/identification/{identificationNumber} — Get student by identification number
POST /v1/students/identification/{identificationNumber} — Update student by identification number
GET /v1/students/phone/{phone} — Get students by phone
```
### Courses
```sh
GET /v1/courses/ — List all courses
POST /v1/courses/ — Create a new course
GET /v1/courses/{id} — Get course by ID
POST /v1/courses/{id} — Update course by ID
DELETE /v1/courses/{id} — Delete course
```
### Attendance
```sh
GET /v1/students/attendances — List all attendances
POST /v1/students/attendances — Record attendance
GET /v1/students/attendances/{id} — Get attendance by ID
GET /v1/students/phone/{phoneNumber}/attendances — Get attendance by student phone
DELETE /v1/students/attendances/{id} — Delete attendance
```
License
This project is licensed under CC0 1.0 Universal.

For more details, see the source code in src/main/java/com/fighthub/attentance/.

