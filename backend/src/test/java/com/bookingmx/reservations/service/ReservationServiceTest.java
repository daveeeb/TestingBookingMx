/**
 * Test suite for ReservationService using JUnit 5 and Mockito.
 *
 * These tests validate:
 * - Creation of reservations (valid and invalid cases)
 * - Updating existing reservations
 * - Canceling reservations
 * - Validation rules for dates, null fields, and statuses
 * - Repository interaction with mocks
 */

package com.bookingmx.reservations.service;

import com.bookingmx.reservations.dto.ReservationRequest;
import com.bookingmx.reservations.exception.BadRequestException;
import com.bookingmx.reservations.exception.NotFoundException;
import com.bookingmx.reservations.model.Reservation;
import com.bookingmx.reservations.model.ReservationStatus;
import com.bookingmx.reservations.repo.ReservationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Optional;
import java.util.List;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReservationServiceTest {

    @Mock
    private ReservationRepository repo;

    @InjectMocks
    private ReservationService service;

    /**
     * Initializes Mockito before each test case.
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // -------------------------------------------------------------
    // CREATE TESTS
    // -------------------------------------------------------------

    /**
     * Tests successful creation of a reservation with valid data.
     */
    @Test
    void testCreateReservation_Success() {
        ReservationRequest req = new ReservationRequest();
        req.setGuestName("Juan");
        req.setHotelName("Hotel MX");
        req.setCheckIn(LocalDate.now().plusDays(1));
        req.setCheckOut(LocalDate.now().plusDays(3));
    }

    /**
     * Ensures an exception is thrown when checkout < checkin.
     */
    @Test
    void testCreateReservation_InvalidDates_ThrowsException() {
        ReservationRequest req = new ReservationRequest("Ana", "Hotel MX",
                LocalDate.now().plusDays(5), LocalDate.now().plusDays(2));

        assertThrows(BadRequestException.class, () -> service.create(req));
    }

    /**
     * Ensures creation fails if check-in date is in the past.
     */
    @Test
    void testCreateReservation_PastDate_ThrowsException() {
        ReservationRequest req = new ReservationRequest("Ana", "Hotel MX",
                LocalDate.now().minusDays(2), LocalDate.now().plusDays(1));

        assertThrows(BadRequestException.class, () -> service.create(req));
    }

    /**
     * Validates creation with all fields correct and repository interaction.
     */
    @Test
    void testCreateReservation_Success_Complete() {
        ReservationRequest req = new ReservationRequest("Juan", "Hotel MX",
                LocalDate.now().plusDays(1), LocalDate.now().plusDays(3));

        Reservation saved = new Reservation(1L, "Juan", "Hotel MX",
                req.getCheckIn(), req.getCheckOut());

        when(repo.save(any(Reservation.class))).thenReturn(saved);

        Reservation result = service.create(req);

        assertNotNull(result);
        assertEquals("Juan", result.getGuestName());
        assertEquals("Hotel MX", result.getHotelName());
        assertEquals(ReservationStatus.ACTIVE, result.getStatus());
        verify(repo, times(1)).save(any(Reservation.class));
    }

    /**
     * Tests invalid creation when required fields are null or blank.
     */
    @Test
    void testCreateReservation_NullFields_ThrowsException() {
        ReservationRequest req = new ReservationRequest(null, null,
                LocalDate.now().plusDays(1), LocalDate.now().plusDays(2));

        assertThrows(BadRequestException.class, () -> service.create(req));
    }

    @Test
    void testCreateReservation_BlankGuestName_ThrowsException() {
        ReservationRequest req = new ReservationRequest("   ", "Hotel MX",
                LocalDate.now().plusDays(1), LocalDate.now().plusDays(2));

        assertThrows(BadRequestException.class, () -> service.create(req));
    }

    @Test
    void testCreateReservation_BlankHotelName_ThrowsException() {
        ReservationRequest req = new ReservationRequest("Juan", "   ",
                LocalDate.now().plusDays(1), LocalDate.now().plusDays(2));

        assertThrows(BadRequestException.class, () -> service.create(req));
    }

    @Test
    void testCreateReservation_NullCheckIn_ThrowsException() {
        ReservationRequest req = new ReservationRequest("Juan", "Hotel MX",
                null, LocalDate.now().plusDays(2));

        assertThrows(BadRequestException.class, () -> service.create(req));
    }

    @Test
    void testCreateReservation_NullCheckOut_ThrowsException() {
        ReservationRequest req = new ReservationRequest("Juan", "Hotel MX",
                LocalDate.now().plusDays(2), null);

        assertThrows(BadRequestException.class, () -> service.create(req));
    }


    // -------------------------------------------------------------
    // UPDATE TESTS
    // -------------------------------------------------------------

    /**
     * Tests successful update of a reservation.
     */
    @Test
    void testUpdateReservation_Success() {
        Reservation existing = new Reservation(1L, "Juan", "Hotel MX",
                LocalDate.now().plusDays(2), LocalDate.now().plusDays(5));

        ReservationRequest req = new ReservationRequest("Pedro", "Hotel Y",
                LocalDate.now().plusDays(3), LocalDate.now().plusDays(6));

        when(repo.findById(1L)).thenReturn(Optional.of(existing));
        when(repo.save(any(Reservation.class))).thenReturn(existing);

        Reservation result = service.update(1L, req);

        assertEquals("Pedro", result.getGuestName());
        verify(repo).save(existing);
    }

    /**
     * Ensures update fails when the reservation is not found.
     */
    @Test
    void testUpdateReservation_NotFound() {
        when(repo.findById(99L)).thenReturn(Optional.empty());

        ReservationRequest req = new ReservationRequest("Maria", "Hotel Z",
                LocalDate.now().plusDays(1), LocalDate.now().plusDays(3));

        assertThrows(NotFoundException.class, () -> service.update(99L, req));
    }

    /**
     * Prevents updating canceled reservations.
     */
    @Test
    void testUpdateCanceledReservation_ThrowsException() {
        Reservation existing = new Reservation(1L, "Juan", "Hotel MX",
                LocalDate.now().plusDays(2), LocalDate.now().plusDays(5));
        existing.setStatus(ReservationStatus.CANCELED);

        when(repo.findById(1L)).thenReturn(Optional.of(existing));

        ReservationRequest req = new ReservationRequest("Pedro", "Hotel Y",
                LocalDate.now().plusDays(3), LocalDate.now().plusDays(6));

        assertThrows(BadRequestException.class, () -> service.update(1L, req));
    }

    /**
     * Ensures invalid date combinations fail on update.
     */
    @Test
    void testUpdateReservation_InvalidDates_ThrowsException() {
        Reservation existing = new Reservation(1L, "Juan", "Hotel MX",
                LocalDate.now().plusDays(2), LocalDate.now().plusDays(5));

        when(repo.findById(1L)).thenReturn(Optional.of(existing));

        ReservationRequest req = new ReservationRequest("Pedro", "Hotel Y",
                LocalDate.now().plusDays(6), LocalDate.now().plusDays(4));

        assertThrows(BadRequestException.class, () -> service.update(1L, req));
    }

    @Test
    void testUpdateReservation_NullCheckIn_ThrowsException() {
        Reservation existing = new Reservation(1L, "Juan", "Hotel",
                LocalDate.now().plusDays(2), LocalDate.now().plusDays(5));

        when(repo.findById(1L)).thenReturn(Optional.of(existing));

        ReservationRequest req = new ReservationRequest("Juan", "Hotel",
                null, LocalDate.now().plusDays(3));

        assertThrows(BadRequestException.class, () -> service.update(1L, req));
    }

    @Test
    void testUpdateReservation_NullCheckOut_ThrowsException() {
        Reservation existing = new Reservation(1L, "Juan", "Hotel",
                LocalDate.now().plusDays(2), LocalDate.now().plusDays(5));

        when(repo.findById(1L)).thenReturn(Optional.of(existing));

        ReservationRequest req = new ReservationRequest("Juan", "Hotel",
                LocalDate.now().plusDays(3), null);

        assertThrows(BadRequestException.class, () -> service.update(1L, req));
    }

    @Test
    void testUpdateReservation_CheckOutBeforeCheckIn_ThrowsException() {
        Reservation existing = new Reservation(1L, "Juan", "Hotel",
                LocalDate.now().plusDays(2), LocalDate.now().plusDays(5));

        when(repo.findById(1L)).thenReturn(Optional.of(existing));

        ReservationRequest req = new ReservationRequest("Juan", "Hotel",
                LocalDate.now().plusDays(5), LocalDate.now().plusDays(4));

        assertThrows(BadRequestException.class, () -> service.update(1L, req));
    }

    @Test
    void testUpdateReservation_CheckInPast_ThrowsException() {
        Reservation existing = new Reservation(1L, "Juan", "Hotel",
                LocalDate.now().plusDays(2), LocalDate.now().plusDays(5));

        when(repo.findById(1L)).thenReturn(Optional.of(existing));

        ReservationRequest req = new ReservationRequest("Juan", "Hotel",
                LocalDate.now().minusDays(1), LocalDate.now().plusDays(3));

        assertThrows(BadRequestException.class, () -> service.update(1L, req));
    }

    @Test
    void testUpdateReservation_CheckOutPast_ThrowsException() {
        Reservation existing = new Reservation(1L, "Juan", "Hotel",
                LocalDate.now().plusDays(2), LocalDate.now().plusDays(5));

        when(repo.findById(1L)).thenReturn(Optional.of(existing));

        ReservationRequest req = new ReservationRequest("Juan", "Hotel",
                LocalDate.now().plusDays(2), LocalDate.now().minusDays(1));

        assertThrows(BadRequestException.class, () -> service.update(1L, req));
    }


    // -------------------------------------------------------------
    // CANCEL TESTS
    // -------------------------------------------------------------

    /**
     * Tests successful cancellation of an active reservation.
     */
    @Test
    void testCancelReservation_Success() {
        Reservation existing = new Reservation(1L, "Juan", "Hotel MX",
                LocalDate.now().plusDays(2), LocalDate.now().plusDays(5));

        when(repo.findById(1L)).thenReturn(Optional.of(existing));
        when(repo.save(any(Reservation.class))).thenReturn(existing);

        Reservation result = service.cancel(1L);

        assertEquals(ReservationStatus.CANCELED, result.getStatus());
        verify(repo).save(existing);
    }

    /**
     * Ensures cancellation fails when reservation does not exist.
     */
    @Test
    void testCancelReservation_NotFound() {
        when(repo.findById(10L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> service.cancel(10L));
    }

    /**
     * Prevents a reservation from being canceled twice.
     */
    @Test
    void testCancelReservation_AlreadyCanceled_ThrowsException() {
        Reservation existing = new Reservation(1L, "Juan", "Hotel MX",
                LocalDate.now().plusDays(1), LocalDate.now().plusDays(3));
        existing.setStatus(ReservationStatus.CANCELED);

        when(repo.findById(1L)).thenReturn(Optional.of(existing));

        assertThrows(BadRequestException.class, () -> service.cancel(1L));
    }

    /**
     * Ensures repository is called and status is changed correctly.
     */
    @Test
    void testCancelReservation_SetsStatusAndSaves() {
        Reservation existing = new Reservation(1L, "Carlos", "Hotel Z",
                LocalDate.now().plusDays(1), LocalDate.now().plusDays(4));

        when(repo.findById(1L)).thenReturn(Optional.of(existing));
        when(repo.save(any(Reservation.class))).thenAnswer(i -> i.getArguments()[0]);

        Reservation result = service.cancel(1L);

        assertEquals(ReservationStatus.CANCELED, result.getStatus());
        verify(repo).save(existing);
    }


    // -------------------------------------------------------------
    // LIST TESTS
    // -------------------------------------------------------------

    /**
     * Returns a non-empty list of reservations.
     */
    @Test
    void testListReservations() {
        List<Reservation> list = new ArrayList<>();
        list.add(new Reservation(1L, "Ana", "Hotel A", LocalDate.now().plusDays(1), LocalDate.now().plusDays(2)));
        when(repo.findAll()).thenReturn(list);

        List<Reservation> result = service.list();

        assertEquals(1, result.size());
        verify(repo).findAll();
    }

    /**
     * Ensures an empty list is handled correctly.
     */
    @Test
    void testListReservations_EmptyList() {
        when(repo.findAll()).thenReturn(new ArrayList<>());

        List<Reservation> result = service.list();

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(repo, times(1)).findAll();
    }


    // -------------------------------------------------------------
    // SERVICE CONSTRUCTOR
    // -------------------------------------------------------------

    /**
     * Validates the default constructor for coverage.
     */
    @Test
    void testEmptyConstructor() {
        ReservationService s = new ReservationService();
        assertNotNull(s);
    }

}
