package com.newlynest.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalModelAdvice {

    /**
     * Injects isAuthenticated into every template model automatically.
     * Used in base.html to conditionally show nav links.
     */
    @ModelAttribute("isAuthenticated")
    public boolean isAuthenticated(Authentication auth) {
        return auth != null
                && auth.isAuthenticated()
                && !(auth.getPrincipal() instanceof String); // "anonymousUser" is a String
    }
}
