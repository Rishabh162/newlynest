package com.newlynest.model;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    private String firstName;
    private String lastName;

    @Column(nullable = false)
    private boolean enabled = true;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private CoupleProfile coupleProfile;

    public User() {}

    // Getters
    public Long getId()                       { return id; }
    public String getEmail()                  { return email; }
    public String getFirstName()              { return firstName; }
    public String getLastName()               { return lastName; }
    public CoupleProfile getCoupleProfile()   { return coupleProfile; }

    // Setters
    public void setId(Long id)                         { this.id = id; }
    public void setUsername(String username)            { this.username = username; }
    public void setEmail(String email)                  { this.email = email; }
    public void setPassword(String password)            { this.password = password; }
    public void setFirstName(String firstName)          { this.firstName = firstName; }
    public void setLastName(String lastName)            { this.lastName = lastName; }
    public void setEnabled(boolean enabled)             { this.enabled = enabled; }
    public void setCoupleProfile(CoupleProfile p)       { this.coupleProfile = p; }

    public String getFullName() {
        if (firstName != null && lastName != null) return firstName + " " + lastName;
        return username;
    }

    // UserDetails
    @Override public String getUsername()                       { return username; }
    @Override public String getPassword()                       { return password; }
    @Override public boolean isEnabled()                        { return enabled; }
    @Override public boolean isAccountNonExpired()              { return true; }
    @Override public boolean isAccountNonLocked()               { return true; }
    @Override public boolean isCredentialsNonExpired()          { return true; }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }
}
