package com.newlynest.repository;

import com.newlynest.model.CoupleMatch;
import com.newlynest.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CoupleMatchRepository extends JpaRepository<CoupleMatch, Long> {
    Optional<CoupleMatch> findByNewUser(User user);
    void deleteByNewUser(User user);
}
