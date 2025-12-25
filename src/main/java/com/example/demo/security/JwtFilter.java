package com.example.demo.security;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;

public class JwtFilter implements Filter {

    private final JwtUtil util;

    public JwtFilter(JwtUtil util) {
        this.util = util;
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest r = (HttpServletRequest) req;
        String header = r.getHeader("Authorization");

        if (header != null && header.startsWith("Bearer ")) {
            try {
                util.getAllClaims(header.substring(7));
            } catch (Exception ignored) {}
        }
        chain.doFilter(req, res);
    }
}
