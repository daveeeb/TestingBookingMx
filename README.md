# Reservation Management Service – Unit Testing with JUnit & Mockito

Challengue 7: Java and JavaScript. Programming Procedures

This project focuses on implementing and validating unit tests for a **Java Spring Boot backend**, specifically targeting core features of a reservation system. The main goal is to ensure that the service responsible for handling reservations behaves correctly, reliably, and consistently.

## Project Overview

The system includes the implementation of unit tests for the following reservation operations:

- Register a reservation
- Cancel a reservation
- Update a reservation

These operations are tested using **JUnit 5** and **Mockito**.
The project also includes coverage analysis using **JaCoCo**.

## Technologies Used

### Backend
- Java 17
- Spring Boot
- Spring Data JPA
- Hibernate Validator
- Maven

### Testing
- JUnit 5
- Mockito
- AssertJ (if applicable)
- Spring Boot Test
- Jacoco (for code coverage reports)

## Project Structure

```
TestingBookingMx/
├── backend/
│   ├── pom.xml
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   │   └── com/
│   │   │   │       └── bookingmx/
│   │   │   │           └── reservations/
│   │   │   │               ├── controller/
│   │   │   │               ├── dto/
│   │   │   │               ├── exception/
│   │   │   │               ├── model/
│   │   │   │               ├── repo/
│   │   │   │               └── service/
│   │   │   │                   ├── ReservationService.java
│   │   │   │                   └── BookingMxApplication.java
│   │   │   └── resources/
│   │   ├── test/
│   │   │   └── java/
│   │   │       └── com/
│   │   │           └── bookingmx/
│   │   │               └── reservations/
│   │   │                   └── service/
│   │   │                       └── ReservationServiceTest.java  ---> File where are the tests.
│   ├── target/
│   ├── TESTING_NOTES.md
│
├── frontend/
│   ├── js/
│   ├── app.js
│   ├── index.html
│   ├── jest.config.js
│   ├── package.json
│   ├── package-lock.json
│   ├── styles.css
│   └── TESTING_NOTES.md
│
└── README.md 

```

## Unit Tests Implemented

### Create Reservation Test
Ensures a valid reservation is successfully saved and returned.

### Cancel Reservation Test
Verifies that the reservation exists, is retrieved, and is marked as canceled.

### Update Reservation Test
Ensures modifications to an existing reservation are persisted correctly.

Each test follows a standard pattern:

1. Arrange – prepare mock objects
2. Act – call the service under test
3. Assert – verify behavior, state, and interactions

## Code Coverage (JaCoCo)

The project uses **JaCoCo** to measure how much of the business logic is tested.

To generate the coverage report:

```
mvn clean test
mvn jacoco:report
```

Report output:

```
/target/site/jacoco/index.html
```

## How to Run Tests

```
mvn test
```

## Requirements

- Java 17+
- Maven 3.8+
- Spring Boot 3+

## Author
Javier David Barraza Ureña
