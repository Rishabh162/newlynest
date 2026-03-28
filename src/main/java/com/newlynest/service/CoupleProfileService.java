package com.newlynest.service;

import com.newlynest.dto.CoupleProfileForm;
import com.newlynest.model.CoupleProfile;
import com.newlynest.model.Role;
import com.newlynest.model.User;
import com.newlynest.repository.CoupleProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CoupleProfileService {

    @Autowired
    private CoupleProfileRepository profileRepository;

    public Optional<CoupleProfile> findByUser(User user) {
        return profileRepository.findByUser(user);
    }

    public Optional<CoupleProfile> findById(Long id) {
        return profileRepository.findById(id);
    }

    public List<CoupleProfile> getAvailableExperiencedCouples() {
        return profileRepository.findByRoleAndAvailableTrue(Role.EXPERIENCED);
    }

    @Transactional
    public CoupleProfile saveOrUpdate(User user, CoupleProfileForm form) {
        CoupleProfile profile = profileRepository.findByUser(user)
                .orElse(new CoupleProfile());

        profile.setUser(user);
        profile.setPartnerName(form.getPartnerName());
        profile.setRole(Role.valueOf(form.getRole()));
        profile.setYearsMarried(form.getYearsMarried());
        profile.setBio(form.getBio());
        profile.setAreasOfStrength(form.getAreasOfStrength());
        profile.setAvatarUrl(form.getAvatarUrl());
        profile.setAvailable(form.isAvailable());

        return profileRepository.save(profile);
    }

    public CoupleProfileForm toForm(CoupleProfile profile) {
        CoupleProfileForm form = new CoupleProfileForm();
        form.setPartnerName(profile.getPartnerName());
        form.setRole(profile.getRole().name());
        form.setYearsMarried(profile.getYearsMarried());
        form.setBio(profile.getBio());
        form.setAreasOfStrength(profile.getAreasOfStrength());
        form.setAvatarUrl(profile.getAvatarUrl());
        form.setAvailable(profile.isAvailable());
        return form;
    }
}
