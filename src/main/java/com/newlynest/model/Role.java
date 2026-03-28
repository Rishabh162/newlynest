package com.newlynest.model;

public enum Role {
    NEW("Newly Married"),
    EXPERIENCED("Experienced Couple");

    private final String displayName;

    Role(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
