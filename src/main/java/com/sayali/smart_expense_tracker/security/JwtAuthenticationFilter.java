package com.sayali.smart_expense_tracker.security;

import java.io.IOException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final CustomUserDetailsService userDetailsService;

    public JwtAuthenticationFilter(
            JwtService jwtService,
            CustomUserDetailsService userDetailsService) {

        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,HttpServletResponse response,FilterChain filterChain) 
    		throws ServletException, IOException 
    {

    	 String username = null;
         String jwt = null;
         
        String authHeader = request.getHeader("Authorization");

        // Check whether Authorization header exists
        if (authHeader != null && authHeader.startsWith("Bearer ")) {

            jwt = authHeader.substring(7);
        } else {

            // If Authorization header is not present,
            // check JWT cookie
            Cookie[] cookies = request.getCookies();

            if (cookies != null) {

                for (Cookie cookie : cookies) {

                    if ("jwt".equals(cookie.getName())) {

                        jwt = cookie.getValue();
                        break;
                    }
                }
            }
        }
           
        System.out.println("Requested URL: " + request.getRequestURI());
        System.out.println("JWT found: " + (jwt != null));
        System.out.println("Username: " + username);
        
     // Extract username from JWT
        if (jwt != null) {

            try {
                username = jwtService.extractUsername(jwt);
            } catch (Exception e) {
            	System.out.println("JWT Error: " + e.getMessage());
            }
        }
        

        // Authenticate user if username was extracted
        if (username != null &&
                SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails =
                    userDetailsService.loadUserByUsername(username);

            if (jwtService.isTokenValid(jwt, userDetails)) {

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );

                authentication.setDetails(
                        new WebAuthenticationDetailsSource()
                                .buildDetails(request)
                );

                SecurityContextHolder.getContext()
                        .setAuthentication(authentication);
            }
        }

        System.out.println("Authentication: " +SecurityContextHolder.getContext().getAuthentication());
        
        filterChain.doFilter(request, response);
    }
}