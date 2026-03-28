package com.newlynest.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(
    name = "booking_slots",
    uniqueConstraints = @UniqueConstraint(columnNames = {"experienced_couple_id", "date", "time_slot"})
)
public class BookingSlot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "requesting_user_id", nullable = false)
    private User requestingUser;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "experienced_couple_id", nullable = false)
    private CoupleProfile experiencedCouple;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private String timeSlot;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BookingStatus status = BookingStatus.PENDING;

    @Column(columnDefinition = "TEXT")
    private String message;

    @CreationTimestamp
    private LocalDateTime createdAt;

    public BookingSlot() {}

    // Getters
    public Long getId()                          { return id; }
    public User getRequestingUser()              { return requestingUser; }
    public CoupleProfile getExperiencedCouple()  { return experiencedCouple; }
    public LocalDate getDate()                   { return date; }
    public String getTimeSlot()                  { return timeSlot; }
    public BookingStatus getStatus()             { return status; }
    public String getMessage()                   { return message; }
    public LocalDateTime getCreatedAt()          { return createdAt; }

    // Setters
    public void setId(Long id)                                  { this.id = id; }
    public void setRequestingUser(User requestingUser)          { this.requestingUser = requestingUser; }
    public void setExperiencedCouple(CoupleProfile couple)      { this.experiencedCouple = couple; }
    public void setDate(LocalDate date)                         { this.date = date; }
    public void setTimeSlot(String timeSlot)                    { this.timeSlot = timeSlot; }
    public void setStatus(BookingStatus status)                  { this.status = status; }
    public void setMessage(String message)                      { this.message = message; }
}
