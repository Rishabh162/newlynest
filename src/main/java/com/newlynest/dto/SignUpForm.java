package com.newlynest.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class SignUpForm {

    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    @NotBlank(message = "Email is required")
    @Email(message = "Please enter a valid email address")
    private String email;

    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 30, message = "Username must be 3–30 characters")
    private String username;

    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters")
    private String password;

    @NotBlank(message = "Please confirm your password")
    private String confirmPassword;

    public String getFirstName()              { return firstName; }
    public void setFirstName(String v)        { this.firstName = v; }

    public String getLastName()               { return lastName; }
    public void setLastName(String v)         { this.lastName = v; }

    public String getEmail()                  { return email; }
    public void setEmail(String v)            { this.email = v; }

    public String getUsername()               { return username; }
    public void setUsername(String v)         { this.username = v; }

    public String getPassword()               { return password; }
    public void setPassword(String v)         { this.password = v; }

    public String getConfirmPassword()        { return confirmPassword; }
    public void setConfirmPassword(String v)  { this.confirmPassword = v; }
}
