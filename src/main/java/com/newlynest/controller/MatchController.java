package com.newlynest.controller;

import com.newlynest.model.CoupleMatch;
import com.newlynest.model.CoupleProfile;
import com.newlynest.model.User;
import com.newlynest.service.MatchingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/match")
public class MatchController {

    @Autowired private MatchingService matchingService;

    /** Trigger the matching algorithm and redirect to the result page. */
    @PostMapping("/run")
    public String runMatch(@AuthenticationPrincipal User currentUser,
                           RedirectAttributes redirectAttributes) {
        try {
            matchingService.runMatch(currentUser);
            return "redirect:/match/result";
        } catch (IllegalStateException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/dashboard";
        }
    }

    /** Show the anonymous matched couple profile. */
    @GetMapping("/result")
    public String matchResult(@AuthenticationPrincipal User currentUser, Model model) {
        Optional<CoupleMatch> match = matchingService.getActiveMatch(currentUser);
        if (match.isEmpty()) {
            return "redirect:/dashboard";
        }
        CoupleProfile couple = match.get().getExperiencedCouple();
        // Truncate bio for the excerpt (120 chars max)
        String bioExcerpt = (couple.getBio() != null && couple.getBio().length() > 120)
                ? couple.getBio().substring(0, 120) + "…"
                : couple.getBio();
        model.addAttribute("couple", couple);
        model.addAttribute("bioExcerpt", bioExcerpt);
        return "matching/match-result";
    }
}
