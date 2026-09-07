package com.sumit.ridesystem.security;

import com.sumit.ridesystem.service.CustomUserDetailsService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import org.springframework.stereotype.Component;

import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor

public class JwtFilter extends OncePerRequestFilter {

    private final JwtService
            jwtService;

    private final CustomUserDetailsService
            userDetailsService;

    @Override
    protected void doFilterInternal(

            HttpServletRequest request,

            HttpServletResponse response,

            FilterChain filterChain

    ) throws ServletException, IOException {

        String path =
                request.getServletPath();

        // =================================
        // SKIP PUBLIC ENDPOINTS
        // =================================

        if (

                path.startsWith("/auth")

                        ||

                        path.startsWith("/api/auth")

                        ||

                        path.startsWith("/swagger-ui")

                        ||

                        path.startsWith("/v3/api-docs")

                        ||

                        path.startsWith("/api-docs")

                        ||

                        path.startsWith("/ws")

                        ||

                        path.equals("/")

                        ||

                        path.equals("/error")

        ) {

            filterChain.doFilter(
                    request,
                    response
            );

            return;
        }

        // =================================
        // AUTH HEADER
        // =================================

        final String authHeader =

                request.getHeader(
                        "Authorization"
                );

        // =================================
        // NO TOKEN
        // =================================

        if (

                authHeader == null

                        ||

                        !authHeader.startsWith(
                                "Bearer "
                        )

        ) {

            filterChain.doFilter(
                    request,
                    response
            );

            return;
        }

        try {

            // =================================
            // EXTRACT TOKEN
            // =================================

            String jwt =
                    authHeader.substring(7);

            // =================================
            // EXTRACT EMAIL
            // =================================

            String email =
                    jwtService.extractEmail(jwt);

            // =================================
            // VALIDATE USER
            // =================================

            if (

                    email != null

                            &&

                            SecurityContextHolder

                                    .getContext()

                                    .getAuthentication()

                                    == null

            ) {

                UserDetails userDetails =

                        userDetailsService
                                .loadUserByUsername(
                                        email
                                );

                // =================================
                // TOKEN VALIDATION
                // =================================

                if (

                        jwtService.isTokenValid(
                                jwt,
                                userDetails
                        )

                ) {

                    UsernamePasswordAuthenticationToken authToken =

                            new UsernamePasswordAuthenticationToken(

                                    userDetails,

                                    null,

                                    userDetails
                                            .getAuthorities()
                            );

                    authToken.setDetails(

                            new WebAuthenticationDetailsSource()

                                    .buildDetails(
                                            request
                                    )
                    );

                    SecurityContextHolder

                            .getContext()

                            .setAuthentication(
                                    authToken
                            );
                }
            }

        } catch (Exception e) {

            response.setStatus(
                    HttpServletResponse.SC_UNAUTHORIZED
            );

            response.setContentType(
                    "application/json"
            );

            response.getWriter().write(

                    """
                    {
                      "success": false,
                      "message": "Invalid or expired token"
                    }
                    """
            );

            return;
        }

        filterChain.doFilter(
                request,
                response
        );
    }
}