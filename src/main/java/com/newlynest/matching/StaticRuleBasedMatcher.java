package com.newlynest.matching;

import com.newlynest.model.CoupleProfile;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Static rule-based matching algorithm.
 *
 * Scoring (higher = better match):
 *  +2  city matches (case-insensitive, null-safe)
 *  +1  per overlapping tag between seeker's challengeAreas and candidate's areasOfStrength
 *
 * Among tied candidates, prefer the one with more years of marriage.
 * This implementation is intentionally simple and fully swappable via the
 * MatchingAlgorithm interface.
 */
@Component
public class StaticRuleBasedMatcher implements MatchingAlgorithm {

    @Override
    public CoupleProfile findBestMatch(CoupleProfile seeker, List<CoupleProfile> candidates) {
        if (candidates == null || candidates.isEmpty()) {
            throw new IllegalArgumentException("Candidate list must not be empty");
        }

        CoupleProfile best = null;
        int bestScore = -1;

        for (CoupleProfile candidate : candidates) {
            int score = score(seeker, candidate);
            if (best == null
                    || score > bestScore
                    || (score == bestScore && candidate.getYearsMarried() > best.getYearsMarried())) {
                best = candidate;
                bestScore = score;
            }
        }
        return best;
    }

    private int score(CoupleProfile seeker, CoupleProfile candidate) {
        int score = 0;

        // +2 for same city
        String seekerCity = seeker.getCity();
        String candidateCity = candidate.getCity();
        if (seekerCity != null && !seekerCity.isBlank()
                && candidateCity != null && !candidateCity.isBlank()
                && seekerCity.trim().equalsIgnoreCase(candidateCity.trim())) {
            score += 2;
        }

        // +1 per overlapping tag (seeker's challenge areas vs candidate's strengths)
        Set<String> challengeTags = normalisedSet(seeker.getChallengeAreas());
        Set<String> strengthTags  = normalisedSet(candidate.getAreasOfStrength());
        for (String tag : challengeTags) {
            if (strengthTags.contains(tag)) score++;
        }

        return score;
    }

    private Set<String> normalisedSet(String csv) {
        Set<String> result = new HashSet<>();
        if (csv == null || csv.isBlank()) return result;
        for (String part : csv.split(",")) {
            String t = part.trim().toLowerCase();
            if (!t.isEmpty()) result.add(t);
        }
        return result;
    }
}
