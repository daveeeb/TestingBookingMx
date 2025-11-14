package com.bookingmx.reservations.service;

import com.bookingmx.reservations.dto.ReservationRequest;
import com.bookingmx.reservations.model.Reservation;
import com.bookingmx.reservations.model.ReservationStatus;
import com.bookingmx.reservations.repo.ReservationRepository;
import com.bookingmx.reservations.exception.BadRequestException;
import com.bookingmx.reservations.exception.NotFoundException;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ReservationService {
    private final ReservationRepository repo;

    public ReservationService(ReservationRepository repo) {
        this.repo = repo;
    }

    public ReservationService() {
        this.repo = null;
    }

    public List<Reservation> list() {
        return repo.findAll();
    }

    //CREATE
    public Reservation create(ReservationRequest req) {
        if (req.getGuestName() == null || req.getGuestName().isBlank()) {
            throw new BadRequestException("Guest name is required");
        }
        if (req.getHotelName() == null || req.getHotelName().isBlank()) {
            throw new BadRequestException("Hotel name is required");
        }
        if (req.getCheckIn() == null || req.getCheckOut() == null) {
            throw new BadRequestException("Dates are required");
        }
        if (req.getCheckIn().isAfter(req.getCheckOut())) {
            throw new BadRequestException("Check-in date must be before check-out date");
        }
        if (req.getCheckIn().isBefore(LocalDate.now())) {
            throw new BadRequestException("Check-in date cannot be in the past");
        }

        Reservation r = new Reservation(null, req.getGuestName(), req.getHotelName(),
                req.getCheckIn(), req.getCheckOut());

        return repo.save(r);
    }


    //UPDATE
    public Reservation update(Long id, ReservationRequest req) {
        Reservation existing = repo.findById(id).orElseThrow(() -> new NotFoundException("Reservation not found"));
        if (!existing.isActive()) throw new BadRequestException("Cannot update a canceled reservation");
        validateDates(req.getCheckIn(), req.getCheckOut());
        existing.setGuestName(req.getGuestName());
        existing.setHotelName(req.getHotelName());
        existing.setCheckIn(req.getCheckIn());
        existing.setCheckOut(req.getCheckOut());
        return repo.save(existing);
    }

    //CANCEL
    public Reservation cancel(Long id) {
        Reservation r = repo.findById(id)
                .orElseThrow(() -> new NotFoundException("Reservation not found"));

        if (r.getStatus() == ReservationStatus.CANCELED) {
            throw new BadRequestException("Reservation is already canceled");
        }

        r.setStatus(ReservationStatus.CANCELED);
        return repo.save(r);
    }

    private void validateDates(LocalDate in, LocalDate out) {
        if (in == null || out == null) throw new BadRequestException("Dates cannot be null");
        if (!out.isAfter(in)) throw new BadRequestException("Check-out must be after check-in");
        if (in.isBefore(LocalDate.now())) throw new BadRequestException("Check-in must be in the future");
        if (out.isBefore(LocalDate.now())) throw new BadRequestException("Check-out must be in the future");
    }
}
