package com.newlynest.data;

import com.newlynest.model.CoupleProfile;
import com.newlynest.model.Role;
import com.newlynest.model.User;
import com.newlynest.repository.CoupleProfileRepository;
import com.newlynest.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DataSeeder implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(DataSeeder.class);

    @Autowired private UserRepository userRepository;
    @Autowired private CoupleProfileRepository profileRepository;
    @Autowired private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        if (profileRepository.countByRole(Role.EXPERIENCED) > 0) {
            log.info("Demo data already exists — skipping seed.");
            return;
        }
        log.info("Seeding demo couple profiles...");

        seed("anita", "Anita", "Kumar", "anita@example.com", "Raj Kumar", 12,
                "After 12 years together we've navigated cultural differences, career changes, and raising two kids. We're here to share what worked for us and what didn't.",
                "communication, cultural differences, career balance", "Mumbai", "Software Engineer");

        seed("maya", "Maya", "Patel", "maya@example.com", "David Patel", 8,
                "Combining two very different financial upbringings taught us a lot. We're passionate about helping couples build a shared money mindset early.",
                "finances, budgeting, work-life balance", "Bangalore", "Doctor");

        seed("sarah", "Sarah", "Thompson", "sarah@example.com", "James Thompson", 15,
                "Raising kids while keeping your marriage strong is hard. After 15 years and three children, we've learned to protect our partnership above all else.",
                "parenting, work-life balance, communication", "Delhi", "Teacher");

        seed("priya", "Priya", "Sharma", "priya@example.com", "Vikram Sharma", 6,
                "Blending families with strong in-laws takes real skill. We've figured out how to set loving boundaries while keeping everyone close.",
                "in-laws, boundaries, conflict resolution", "Mumbai", "Entrepreneur");

        seed("lisa", "Lisa", "Chen", "lisa@example.com", "Michael Chen", 10,
                "An intercultural marriage means navigating two sets of traditions, holidays, and expectations. We make it work by leading with curiosity and humour.",
                "communication, conflict resolution, work-life balance", "Pune", "HR Manager");

        log.info("Seeding complete — 5 couples added.");
    }

    private void seed(String username, String firstName, String lastName, String email,
                      String partnerName, int yearsMarried, String bio, String strengths,
                      String city, String profession) {
        if (userRepository.existsByUsername(username)) return;

        User user = new User();
        user.setUsername(username);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode("newlynest123"));
        user = userRepository.save(user);

        CoupleProfile profile = new CoupleProfile();
        profile.setUser(user);
        profile.setPartnerName(partnerName);
        profile.setRole(Role.EXPERIENCED);
        profile.setYearsMarried(yearsMarried);
        profile.setBio(bio);
        profile.setAreasOfStrength(strengths);
        profile.setCity(city);
        profile.setProfession(profession);
        profile.setAvailable(true);
        profileRepository.save(profile);
    }
}
