package com.newlynest.service;

import com.newlynest.matching.MatchingAlgorithm;
import com.newlynest.model.CoupleMatch;
import com.newlynest.model.CoupleProfile;
import com.newlynest.model.Role;
import com.newlynest.model.User;
import com.newlynest.repository.CoupleMatchRepository;
import com.newlynest.repository.CoupleProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class MatchingService {

    @Autowired private CoupleMatchRepository matchRepository;
    @Autowired private CoupleProfileRepository profileRepository;
    @Autowired private MatchingAlgorithm matchingAlgorithm;

    /**
     * Runs the matching algorithm for the given NEW user, persists (upserts) the
     * result, and returns the matched experienced couple's profile.
     *
     * @throws IllegalStateException if the user has no couple profile, or if there
     *                               are no available experienced couples.
     */
    @Transactional
    public CoupleProfile runMatch(User newUser) {
        CoupleProfile seekerProfile = profileRepository.findByUser(newUser)
                .orElseThrow(() -> new IllegalStateException("No profile found for user"));

        List<CoupleProfile> candidates = profileRepository.findByRoleAndAvailableTrue(Role.EXPERIENCED);
        if (candidates.isEmpty()) {
            throw new IllegalStateException("No experienced couples are available right now.");
        }

        CoupleProfile matched = matchingAlgorithm.findBestMatch(seekerProfile, candidates);

        // Upsert: delete existing match then insert new one so @CreationTimestamp refreshes
        matchRepository.deleteByNewUser(newUser);
        matchRepository.flush();

        CoupleMatch coupleMatch = new CoupleMatch();
        coupleMatch.setNewUser(newUser);
        coupleMatch.setExperiencedCouple(matched);
        matchRepository.save(coupleMatch);

        return matched;
    }

    /** Returns the active match for a user if one exists. */
    public Optional<CoupleMatch> getActiveMatch(User user) {
        return matchRepository.findByNewUser(user);
    }

    /** Removes any active match for the user (called after profile update). */
    @Transactional
    public void clearMatch(User user) {
        matchRepository.deleteByNewUser(user);
    }
}
