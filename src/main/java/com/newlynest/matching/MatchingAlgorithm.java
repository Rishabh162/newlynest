package com.newlynest.matching;

import com.newlynest.model.CoupleProfile;

import java.util.List;

public interface MatchingAlgorithm {

    /**
     * Given the seeker's profile and a list of available experienced couples,
     * returns the best matching couple. Never returns null — always picks one
     * from the candidates list (which must be non-empty).
     */
    CoupleProfile findBestMatch(CoupleProfile seekerProfile, List<CoupleProfile> candidates);
}
