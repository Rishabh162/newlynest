package com.newlynest.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "couple_matches")
public class CoupleMatch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "new_user_id", nullable = false, unique = true)
    private User newUser;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "experienced_couple_id", nullable = false)
    private CoupleProfile experiencedCouple;

    @CreationTimestamp
    @Column(name = "matched_at", nullable = false, updatable = false)
    private LocalDateTime matchedAt;

    @Column(name = "match_score")
    private Integer matchScore;

    public CoupleMatch() {}

    public Long getId()                              { return id; }
    public User getNewUser()                         { return newUser; }
    public CoupleProfile getExperiencedCouple()      { return experiencedCouple; }
    public LocalDateTime getMatchedAt()              { return matchedAt; }
    public Integer getMatchScore()                   { return matchScore; }

    public void setId(Long id)                                      { this.id = id; }
    public void setNewUser(User newUser)                            { this.newUser = newUser; }
    public void setExperiencedCouple(CoupleProfile couple)          { this.experiencedCouple = couple; }
    public void setMatchScore(Integer score)                        { this.matchScore = score; }
}
