package com.newlynest.controller;

import com.newlynest.dto.BookingForm;
import com.newlynest.model.CoupleProfile;
import com.newlynest.model.User;
import com.newlynest.service.BookingService;
import com.newlynest.service.CoupleProfileService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
public class MatchingController {

    @Autowired private CoupleProfileService coupleProfileService;
    @Autowired private BookingService bookingService;

    @GetMapping("/dashboard")
    public String dashboard(@AuthenticationPrincipal User currentUser, Model model) {
        if (coupleProfileService.findByUser(currentUser).isEmpty()) {
            return "redirect:/accounts/profile-setup";
        }
        model.addAttribute("couples", coupleProfileService.getAvailableExperiencedCouples());
        return "matching/dashboard";
    }

    @GetMapping("/couple/{id}")
    public String coupleDetail(@PathVariable Long id, Model model) {
        Optional<CoupleProfile> profileOpt = coupleProfileService.findById(id);
        if (profileOpt.isEmpty()) return "redirect:/dashboard";

        model.addAttribute("profile", profileOpt.get());
        model.addAttribute("bookingForm", new BookingForm());
        model.addAttribute("timeSlots", getTimeSlots());
        return "matching/couple-detail";
    }

    @PostMapping("/couple/{id}/book")
    public String bookSlot(@PathVariable Long id,
                           @AuthenticationPrincipal User currentUser,
                           @Valid @ModelAttribute BookingForm bookingForm,
                           BindingResult result,
                           RedirectAttributes redirectAttributes,
                           Model model) {

        Optional<CoupleProfile> profileOpt = coupleProfileService.findById(id);
        if (profileOpt.isEmpty()) return "redirect:/dashboard";

        CoupleProfile couple = profileOpt.get();

        if (result.hasErrors()) {
            model.addAttribute("profile", couple);
            model.addAttribute("timeSlots", getTimeSlots());
            return "matching/couple-detail";
        }

        try {
            bookingService.book(currentUser, couple, bookingForm);
            redirectAttributes.addFlashAttribute("successMessage", "Booking request sent!");
            return "redirect:/booking/confirmed";
        } catch (IllegalStateException e) {
            model.addAttribute("profile", couple);
            model.addAttribute("timeSlots", getTimeSlots());
            model.addAttribute("errorMessage", e.getMessage());
            return "matching/couple-detail";
        }
    }

    @GetMapping("/booking/confirmed")
    public String bookingConfirmed() {
        return "matching/booking-confirmation";
    }

    private List<String[]> getTimeSlots() {
        return List.of(
                new String[]{"09:00", "9:00 AM"},
                new String[]{"10:00", "10:00 AM"},
                new String[]{"11:00", "11:00 AM"},
                new String[]{"12:00", "12:00 PM"},
                new String[]{"13:00", "1:00 PM"},
                new String[]{"14:00", "2:00 PM"},
                new String[]{"15:00", "3:00 PM"},
                new String[]{"16:00", "4:00 PM"}
        );
    }
}
