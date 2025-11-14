# Reservation Management Service – Unit Testing with JUnit & Mockito

Challengue 7: Java and JavaScript. Programming Procedures

This project focuses on implementing and validating unit tests for a **Java Spring Boot backend**, specifically targeting core features of a reservation system. The main goal is to ensure that the service responsible for handling reservations behaves correctly, reliably, and consistently.

## Overview
This project is part of the **TechnoReady – Java & JavaScript Programming Procedures** module. It includes backend and frontend components implemented with **Java, JavaScript, JUnit, Jest, Maven, and JaCoCo**, focusing on testing-driven development.

The project consists of:

- **Sprint 1** → Java backend reservation module + JUnit tests  
- **Sprint 2** → JavaScript graph visualization module + Jest tests  


## Technologies Used

### Backend
- Java 17
- Spring Boot
- Spring Data JPA
- Hibernate Validator
- Maven

## Testing
- JUnit 5
- Mockito
- Spring Boot Test
- Jacoco (for code coverage reports)

### Frontend
- Node.js + ECMAScript Modules (ESM)
- Jest 29
- Babel-free ESM testing (using `--experimental-vm-modules`)
- Pure JavaScript functions for testability

## Testing 
- Jest



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
│   │   │                       └── ReservationServiceTest.java  ---> File where are the backend tests.
│   ├── target/
│   ├── TESTING_NOTES.md
│
├── frontend/
│   ├── js/
│   │   ├── graph.js
│   │   ├── api.js
│   │   └── test/graph.test.js ---> File where are the forntend tests.
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
cd backend oo frontend
```

```
mvn test
```

## Requirements

- Java 17+
- Maven 3.8+
- Spring Boot 3+

## Author
Javier David Barraza Ureña
