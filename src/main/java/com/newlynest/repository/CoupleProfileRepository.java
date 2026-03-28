package com.newlynest.repository;

import com.newlynest.model.CoupleProfile;
import com.newlynest.model.Role;
import com.newlynest.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CoupleProfileRepository extends JpaRepository<CoupleProfile, Long> {
    Optional<CoupleProfile> findByUser(User user);
    List<CoupleProfile> findByRoleAndAvailableTrue(Role role);
    long countByRole(Role role);
    boolean existsByUser(User user);
}
