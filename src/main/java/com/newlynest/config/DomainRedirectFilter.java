package com.newlynest.config;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Redirects all traffic arriving on the old Railway subdomain
 * (newlynest-production.up.railway.app) to the canonical domain (newlynest.in).
 *
 * This 301 (permanent) redirect tells Google definitively which URL is canonical,
 * preventing the "Duplicate, Google chose different canonical than user" GSC error.
 */
@Component
@Order(1)
public class DomainRedirectFilter implements Filter {

    private static final String RAILWAY_HOST   = "newlynest-production.up.railway.app";
    private static final String CANONICAL_BASE = "https://newlynest.in";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest  req  = (HttpServletRequest)  request;
        HttpServletResponse resp = (HttpServletResponse) response;

        // Railway sits behind a proxy — use X-Forwarded-Host when available
        String host = req.getHeader("X-Forwarded-Host");
        if (host == null || host.isBlank()) {
            host = req.getServerName();
        }
        // Strip port if present (e.g. "host:443")
        if (host.contains(":")) {
            host = host.substring(0, host.indexOf(':'));
        }

        if (RAILWAY_HOST.equalsIgnoreCase(host)) {
            String path        = req.getRequestURI();
            String queryString = req.getQueryString();
            String redirectUrl = CANONICAL_BASE + path + (queryString != null ? "?" + queryString : "");

            resp.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY); // 301
            resp.setHeader("Location", redirectUrl);
            return;
        }

        chain.doFilter(request, response);
    }
}
