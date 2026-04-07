package com.newlynest.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "couple_profiles")
public class CoupleProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Column(nullable = false)
    private String partnerName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Column(nullable = false)
    private int yearsMarried;

    @Column(columnDefinition = "TEXT")
    private String bio;

    private String areasOfStrength;
    private String city;
    private String profession;
    private String challengeAreas;
    private String avatarUrl;

    @Column(nullable = false)
    private boolean available = true;

    @CreationTimestamp
    private LocalDateTime createdAt;

    public CoupleProfile() {}

    // Getters
    public Long getId()                  { return id; }
    public User getUser()                { return user; }
    public String getPartnerName()       { return partnerName; }
    public Role getRole()                { return role; }
    public int getYearsMarried()         { return yearsMarried; }
    public String getBio()               { return bio; }
    public String getAreasOfStrength()   { return areasOfStrength; }
    public String getCity()              { return city; }
    public String getProfession()        { return profession; }
    public String getChallengeAreas()    { return challengeAreas; }
    public String getAvatarUrl()         { return avatarUrl; }
    public boolean isAvailable()         { return available; }
    public LocalDateTime getCreatedAt()  { return createdAt; }

    // Setters
    public void setId(Long id)                          { this.id = id; }
    public void setUser(User user)                      { this.user = user; }
    public void setPartnerName(String partnerName)      { this.partnerName = partnerName; }
    public void setRole(Role role)                      { this.role = role; }
    public void setYearsMarried(int yearsMarried)       { this.yearsMarried = yearsMarried; }
    public void setBio(String bio)                      { this.bio = bio; }
    public void setAreasOfStrength(String areas)        { this.areasOfStrength = areas; }
    public void setCity(String city)                    { this.city = city; }
    public void setProfession(String profession)        { this.profession = profession; }
    public void setChallengeAreas(String areas)         { this.challengeAreas = areas; }
    public void setAvatarUrl(String avatarUrl)          { this.avatarUrl = avatarUrl; }
    public void setAvailable(boolean available)         { this.available = available; }

    // Helper methods
    public List<String> getChallengeTags() {
        if (challengeAreas == null || challengeAreas.isBlank()) return List.of();
        return Arrays.stream(challengeAreas.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());
    }

    public List<String> getStrengthTags() {
        if (areasOfStrength == null || areasOfStrength.isBlank()) return List.of();
        return Arrays.stream(areasOfStrength.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());
    }

    public String getInitials() {
        String first = (user != null && user.getFirstName() != null && !user.getFirstName().isEmpty())
                ? String.valueOf(user.getFirstName().charAt(0)).toUpperCase() : "?";
        String second = (partnerName != null && !partnerName.isBlank())
                ? String.valueOf(partnerName.trim().charAt(0)).toUpperCase() : "?";
        return first + second;
    }

    public String getDisplayName() {
        String userName = (user != null && user.getFirstName() != null) ? user.getFirstName() : "?";
        String partner = (partnerName != null && !partnerName.isBlank())
                ? partnerName.split(" ")[0] : "?";
        return userName + " & " + partner;
    }
}
