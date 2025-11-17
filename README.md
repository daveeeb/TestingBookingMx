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
## Test Cases Table
| #  | Test Name                                                     | Category    | Description                            | Expected Behavior                   |
| -- | ------------------------------------------------------------- | ----------- | -------------------------------------- | ----------------------------------- |
| 1  | `testCreateReservation_Success`                               | Create      | Valid data should create a reservation | Reservation created successfully    |
| 2  | `testCreateReservation_InvalidDates_ThrowsException`          | Create      | Checkout < checkin                     | Throws `BadRequestException`        |
| 3  | `testCreateReservation_PastDate_ThrowsException`              | Create      | Check-in date in past                  | Throws `BadRequestException`        |
| 4  | `testCreateReservation_Success_Complete`                      | Create      | Valid data + repo interaction          | Reservation saved, fields validated |
| 5  | `testCreateReservation_NullFields_ThrowsException`            | Create      | Null guest + hotel name                | Throws `BadRequestException`        |
| 6  | `testCreateReservation_BlankGuestName_ThrowsException`        | Create      | Blank guest name                       | Throws `BadRequestException`        |
| 7  | `testCreateReservation_BlankHotelName_ThrowsException`        | Create      | Blank hotel name                       | Throws `BadRequestException`        |
| 8  | `testCreateReservation_NullCheckIn_ThrowsException`           | Create      | Null check-in                          | Throws `BadRequestException`        |
| 9  | `testCreateReservation_NullCheckOut_ThrowsException`          | Create      | Null check-out                         | Throws `BadRequestException`        |
| 10 | `testUpdateReservation_Success`                               | Update      | Valid update on existing reservation   | Reservation updated successfully    |
| 11 | `testUpdateReservation_NotFound`                              | Update      | Updating non-existing ID               | Throws `NotFoundException`          |
| 12 | `testUpdateCanceledReservation_ThrowsException`               | Update      | Updating canceled reservation          | Throws `BadRequestException`        |
| 13 | `testUpdateReservation_InvalidDates_ThrowsException`          | Update      | Checkout < checkin                     | Throws `BadRequestException`        |
| 14 | `testUpdateReservation_NullCheckIn_ThrowsException`           | Update      | Null check-in                          | Throws `BadRequestException`        |
| 15 | `testUpdateReservation_NullCheckOut_ThrowsException`          | Update      | Null check-out                         | Throws `BadRequestException`        |
| 16 | `testUpdateReservation_CheckOutBeforeCheckIn_ThrowsException` | Update      | Invalid date order                     | Throws `BadRequestException`        |
| 17 | `testUpdateReservation_CheckInPast_ThrowsException`           | Update      | Check-in in past                       | Throws `BadRequestException`        |
| 18 | `testUpdateReservation_CheckOutPast_ThrowsException`          | Update      | Check-out in past                      | Throws `BadRequestException`        |
| 19 | `testCancelReservation_Success`                               | Cancel      | Valid cancel                           | Sets status to CANCELED             |
| 20 | `testCancelReservation_NotFound`                              | Cancel      | Cancel nonexistent reservation         | Throws `NotFoundException`          |
| 21 | `testCancelReservation_AlreadyCanceled_ThrowsException`       | Cancel      | Cancel already canceled                | Throws `BadRequestException`        |
| 22 | `testCancelReservation_SetsStatusAndSaves`                    | Cancel      | Verify save + status update            | Repo saves updated reservation      |
| 23 | `testListReservations`                                        | List        | List contains items                    | Returns non-empty list              |
| 24 | `testListReservations_EmptyList`                              | List        | Repository returns empty list          | Returns empty list safely           |
| 25 | `testEmptyConstructor`                                        | Constructor | Coverage-only                          | Object should instantiate           |
| 26 | *(Mockito initialization inside setUp)*                       | Setup       | Ensures mocks load correctly           | Mocks initialized with no failures  |


Javier David Barraza Ureña
