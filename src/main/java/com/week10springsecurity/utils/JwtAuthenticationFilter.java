package com.week10springsecurity.utils;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter  {
    private  final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    @Override
    protected void doFilterInternal(
         @NonNull HttpServletRequest request,
         @NonNull   HttpServletResponse response,
         //filter contains list of the filters we need
         @NonNull   FilterChain filterChain
    ) throws ServletException, IOException {
        //extract the header, and pass header name, (it contains bearer token)
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;
        //implement the checks we had done in line 24
        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            filterChain.doFilter(request, response);
            return;
        }
        //next Step os to extract the token for the  header(authHeader)
        jwt = authHeader.substring(7);
        //extract the user email from the JWT token;
        userEmail = jwtService.extractUserName(jwt);
        if(userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null){
            //after the validation we need to get the user from the database
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
            //validate if the token is still valid
            if(jwtService.isTokenValid(jwt, userDetails)){
                //if the user is valid, we need to update the security context and send to dispatcher servlet
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities())
                        ;
                        authToken.setDetails(
                                new WebAuthenticationDetailsSource().buildDetails(request)

                        );
                        SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}
