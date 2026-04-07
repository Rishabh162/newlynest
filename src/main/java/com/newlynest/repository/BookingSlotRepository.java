package com.newlynest.repository;

import com.newlynest.model.BookingSlot;
import com.newlynest.model.CoupleProfile;
import com.newlynest.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface BookingSlotRepository extends JpaRepository<BookingSlot, Long> {
    List<BookingSlot> findByRequestingUser(User user);
    List<BookingSlot> findByExperiencedCouple(CoupleProfile couple);
    boolean existsByExperiencedCoupleAndDateAndTimeSlot(CoupleProfile couple, LocalDate date, String timeSlot);
    List<BookingSlot> findByExperiencedCoupleAndDate(CoupleProfile couple, LocalDate date);
    List<BookingSlot> findByRequestingUserAndDateGreaterThanEqualOrderByDateAscTimeSlotAsc(User user, LocalDate from);
    List<BookingSlot> findByRequestingUserAndDateBeforeOrderByDateDescTimeSlotDesc(User user, LocalDate before);
}
