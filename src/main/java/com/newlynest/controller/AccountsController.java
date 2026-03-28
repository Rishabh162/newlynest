package com.newlynest.controller;

import com.newlynest.dto.CoupleProfileForm;
import com.newlynest.dto.SignUpForm;
import com.newlynest.model.CoupleProfile;
import com.newlynest.model.User;
import com.newlynest.service.CoupleProfileService;
import com.newlynest.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/accounts")
public class AccountsController {

    @Autowired private UserService userService;
    @Autowired private CoupleProfileService coupleProfileService;
    @Autowired private AuthenticationManager authenticationManager;

    @GetMapping("/signup")
    public String showSignup(Authentication auth, Model model) {
        if (auth != null && auth.isAuthenticated()) return "redirect:/dashboard";
        model.addAttribute("signUpForm", new SignUpForm());
        return "accounts/signup";
    }

    @PostMapping("/signup")
    public String processSignup(@Valid @ModelAttribute SignUpForm signUpForm,
                                BindingResult result,
                                RedirectAttributes redirectAttributes) {
        if (!signUpForm.getPassword().equals(signUpForm.getConfirmPassword())) {
            result.rejectValue("confirmPassword", "error.form", "Passwords do not match");
        }
        if (userService.usernameExists(signUpForm.getUsername())) {
            result.rejectValue("username", "error.form", "Username is already taken");
        }
        if (userService.emailExists(signUpForm.getEmail())) {
            result.rejectValue("email", "error.form", "An account with this email already exists");
        }
        if (result.hasErrors()) return "accounts/signup";

        userService.registerUser(signUpForm);

        // Auto-login after registration
        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(signUpForm.getUsername(), signUpForm.getPassword());
        Authentication authentication = authenticationManager.authenticate(authToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        redirectAttributes.addFlashAttribute("successMessage", "Welcome to NewlyNest! Let's set up your profile.");
        return "redirect:/accounts/profile-setup";
    }

    @GetMapping("/login")
    public String showLogin(Authentication auth,
                            @RequestParam(required = false) String error,
                            Model model) {
        if (auth != null && auth.isAuthenticated()) return "redirect:/dashboard";
        if (error != null) model.addAttribute("errorMessage", "Invalid username or password.");
        return "accounts/login";
    }

    @GetMapping("/profile-setup")
    public String showProfileSetup(@AuthenticationPrincipal User currentUser, Model model) {
        Optional<CoupleProfile> existing = coupleProfileService.findByUser(currentUser);
        CoupleProfileForm form = existing.map(coupleProfileService::toForm).orElse(new CoupleProfileForm());
        model.addAttribute("coupleProfileForm", form);
        model.addAttribute("isEdit", existing.isPresent());
        return "accounts/profile-setup";
    }

    @PostMapping("/profile-setup")
    public String processProfileSetup(@AuthenticationPrincipal User currentUser,
                                      @Valid @ModelAttribute CoupleProfileForm coupleProfileForm,
                                      BindingResult result,
                                      RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) return "accounts/profile-setup";
        coupleProfileService.saveOrUpdate(currentUser, coupleProfileForm);
        redirectAttributes.addFlashAttribute("successMessage", "Profile saved successfully!");
        return "redirect:/dashboard";
    }
}
