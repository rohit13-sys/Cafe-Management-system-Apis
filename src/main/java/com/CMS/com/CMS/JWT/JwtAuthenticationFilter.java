package com.CMS.com.CMS.JWT;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private JWTUtil jwtUtil;


    private String userName = null;
    Claims claims = null;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        if (request.getServletPath().matches("/user/login|/user/signUp|/user/forgotPassword")) {
            filterChain.doFilter(request, response);
        } else {
            final String requestTokenHeader = request.getHeader("Authorization");
            String jwtToken = null;
            if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer")) {
                jwtToken = requestTokenHeader.substring(7);
                if (!jwtUtil.isTokenExpired(jwtToken)) {
                    try {
                        userName = jwtUtil.getUsernameFromToken(jwtToken);
                        claims=jwtUtil.getAllClaimsFromToken(jwtToken);
                        System.out.println("User found from token : " + userName);
                    } catch (IllegalArgumentException e) {
                        System.out.println("Unable to parse token");
                    }
                } else {
                    throw new Error("Token is expired");
                }

            }

            if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(userName);
                if (jwtUtil.validateToken(jwtToken, userDetails)) {
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    usernamePasswordAuthenticationToken
                            .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
// After setting the Authentication in the context, we specify
// that the current user is authenticated. So it passes the Spring Security Configurations successfully.
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }
            }
            filterChain.doFilter(request, response);
        }

    }

    public boolean isAdmin(){
         return "admin".equalsIgnoreCase((String) claims.get("role"));
    }

    public boolean isUser(){
         return "admin".equalsIgnoreCase((String) claims.get("role"));
    }

    public String getCurrentUser(){
        return userName;
    }
}
