package com.newlynest.service;

import com.newlynest.dto.BookingForm;
import com.newlynest.model.BookingSlot;
import com.newlynest.model.CoupleProfile;
import com.newlynest.model.User;
import com.newlynest.repository.BookingSlotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class BookingService {

    @Autowired
    private BookingSlotRepository bookingSlotRepository;

    public boolean isSlotTaken(CoupleProfile couple, LocalDate date, String timeSlot) {
        return bookingSlotRepository.existsByExperiencedCoupleAndDateAndTimeSlot(couple, date, timeSlot);
    }

    public List<String> getTakenSlotsForDate(CoupleProfile couple, LocalDate date) {
        return bookingSlotRepository.findByExperiencedCoupleAndDate(couple, date)
                .stream()
                .map(BookingSlot::getTimeSlot)
                .toList();
    }

    @Transactional
    public BookingSlot book(User requestingUser, CoupleProfile experiencedCouple, BookingForm form) {
        if (isSlotTaken(experiencedCouple, form.getDate(), form.getTimeSlot())) {
            throw new IllegalStateException("This time slot is already booked.");
        }

        BookingSlot slot = new BookingSlot();
        slot.setRequestingUser(requestingUser);
        slot.setExperiencedCouple(experiencedCouple);
        slot.setDate(form.getDate());
        slot.setTimeSlot(form.getTimeSlot());
        slot.setMessage(form.getMessage());

        return bookingSlotRepository.save(slot);
    }
}
