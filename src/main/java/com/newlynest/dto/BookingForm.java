package com.newlynest.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class BookingForm {

    @NotNull(message = "Please select a date")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate date;

    @NotBlank(message = "Please select a time slot")
    private String timeSlot;

    private String message;

    public LocalDate getDate()          { return date; }
    public void setDate(LocalDate v)    { this.date = v; }

    public String getTimeSlot()         { return timeSlot; }
    public void setTimeSlot(String v)   { this.timeSlot = v; }

    public String getMessage()          { return message; }
    public void setMessage(String v)    { this.message = v; }
}
