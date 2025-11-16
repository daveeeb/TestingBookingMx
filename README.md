# Reservation Management Service – Unit Testing with JUnit & Mockito

Challenge 7: Java and JavaScript. Programming Procedures

## General Project Description

BookingMX is a frontend application designed to manage reservations, visualize city connections through a graph-based interface, and provide a seamless user experience for booking-related workflows. The system integrates interactive UI components, API consumption, and structured navigation to support the reservation module, city graph visualization, and other key functionalities.

This project focuses on implementing and validating unit tests for a **Java Spring Boot backend**, specifically targeting core features of a reservation system. The main goal is to ensure that the service responsible for handling reservations behaves correctly, reliably, and consistently.

## Key Functionalities

-   **Reservation Module**: Allows users to create, modify, and
    visualize reservations.
-   **City Graph System**: Displays city connections using graph-based
    structures.
-   **Interactive UI Components** built with JavaScript and modern
    frontend tooling.
-   **Automated Testing** using Jest for unit testing across sprints.

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

## Installation Instructions

### 1. Clone the Repository

``` bash
git clone https://github.com/yourusername/bookingmx-frontend.git
cd bookingmx-frontend
```

### 2. Install Dependencies

``` bash
npm install
```

### 3. Run the Development Server

``` bash
npm run dev
```

### 4. Build for Production

``` bash
npm run build
```

### 5. Run Tests

``` bash
npm test
```


## Description of the Tests

### Unit Tests Implemented in Previous Sprints

Unit tests were developed during Sprint 1 and Sprint 2 to validate: -
**Reservation data handling**\
- **City graph data processing**\
- **Component rendering and event behavior**

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

## Requirements

- Java 17+
- Maven 3.8+
- Spring Boot 3+

## Dependencies
Add this dependencies in pom.xml
```
<dependency>
  <groupId>org.junit.jupiter</groupId>
  <artifactId>junit-jupiter</artifactId>
  <version>5.9.3</version>
  <scope>test</scope>
</dependency>
<dependency>
  <groupId>org.mockito</groupId>
  <artifactId>mockito-core</artifactId>
  <version>5.7.0</version>
  <scope>test</scope>
</dependency>
<plugin>
  <groupId>org.jacoco</groupId>
  <artifactId>jacoco-maven-plugin</artifactId>
  <version>0.8.11</version>
  <executions>
    <execution>
      <goals>
        <goal>prepare-agent</goal>
      </goals>
    </execution>
  </executions>
</plugin>

```
## Components Tested - Backend

Backend tests focused on the Reservation module, specifically:
- ReservationService
- ReservationRepository (mocked)
- ReservationRequest and ReservationResponse

## Test Categories Implemented - Backend
  - Reservation creation tests
    - Valid reservation should be saved correctly.
    - Should return a properly formatted success message.
    - Should validate input fields (dates, city, passenger count).

- Reservation cancellation tests
    - Cancel an existing reservation successfully.
    - Return appropriate message.
    - Throw errors when reservation ID does not exist.

- Reservation update tests
    - Update city, date, or passenger count.
    - Validate updated fields.
    - Reject updates with invalid data.

- Edge case tests
    - Attempt to create reservations with missing fields.
    - Invalid dates (past dates, reversed ranges).
    - Null or empty fields.
    - Repository failures simulated with Mockito.

## Components Tested - Frontend

The focus was on the graph visualization module, specifically:
    - Graph class
    - validateGraphData()
    - buildGraph()
    - getNearbyCities()
    - sampleData
    
## Test Categories Implemented
- Graph class tests
    - Add valid cities
    - Reject invalid city names
    - Add edges correctly
    - Reject edges referencing unknown cities
    - Reject invalid distances
    - Retrieve neighbors correctly
- Dataset validation tests
    - Accept valid datasets
    - Reject:
    - Duplicate city names
    - Non-string city entries
    - Edges pointing to unknown cities
    - Negative or invalid distances
- buildGraph() tests
    - Build a complete graph from a dataset
    - Ensure that all nodes and edges are correctly loaded
- getNearbyCities() tests
    - Returns nearby cities sorted by ascending distance
    - Filters cities beyond the specified max distance
    - Returns empty list if city does not exist
    - Throws error when graph instance is invalid
- Edge cases
    - Empty datasets
    - Cities without edges
    - Distances equal to the max threshold
    - Passing invalid graph objects
      
Javier David Barraza Ureña
