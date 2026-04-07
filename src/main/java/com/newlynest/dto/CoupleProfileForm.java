package com.newlynest.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CoupleProfileForm {

    @NotBlank(message = "Partner name is required")
    private String partnerName;

    @NotBlank(message = "Please select your role")
    private String role;

    @NotNull(message = "Years married is required")
    @Min(value = 0, message = "Years married cannot be negative")
    private Integer yearsMarried;

    @NotBlank(message = "Bio is required")
    private String bio;

    private String areasOfStrength;
    private String city;
    private String profession;
    private String challengeAreas;
    private String avatarUrl;
    private boolean available = true;

    public String getPartnerName()              { return partnerName; }
    public void setPartnerName(String v)        { this.partnerName = v; }

    public String getRole()                     { return role; }
    public void setRole(String v)               { this.role = v; }

    public Integer getYearsMarried()            { return yearsMarried; }
    public void setYearsMarried(Integer v)      { this.yearsMarried = v; }

    public String getBio()                      { return bio; }
    public void setBio(String v)                { this.bio = v; }

    public String getAreasOfStrength()          { return areasOfStrength; }
    public void setAreasOfStrength(String v)    { this.areasOfStrength = v; }

    public String getCity()                     { return city; }
    public void setCity(String v)               { this.city = v; }

    public String getProfession()               { return profession; }
    public void setProfession(String v)         { this.profession = v; }

    public String getChallengeAreas()           { return challengeAreas; }
    public void setChallengeAreas(String v)     { this.challengeAreas = v; }

    public String getAvatarUrl()                { return avatarUrl; }
    public void setAvatarUrl(String v)          { this.avatarUrl = v; }

    public boolean isAvailable()                { return available; }
    public void setAvailable(boolean v)         { this.available = v; }
}
