package com.example.AdminService.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.AdminService.service.JwtUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;

/**
 * A filter that intercepts incoming HTTP requests and extracts JWT tokens from the
 * request headers. It uses the token to authenticate the user and set the authentication
 * details in the SecurityContext. If the token is invalid or expired, the request is not
 * authenticated and the filter does not modify the SecurityContext.
 */
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
    /**
     * The prefix for the JWT token in the HTTP request header.
     */
    private static final String TOKEN_PREFIX = "Bearer ";

    /**
     * The service used to load user details from the database based on the username.
     */
    private final JwtUserDetailsService jwtUserDetailsService;


    /**
     * The secret key used to sign and verify JWT tokens.
     */
    private final String secret;


    /**
     * Constructs a new JwtAuthorizationFilter with the specified authentication manager,
     * JWT user details service, and secret key.
     *
     * @param authenticationManager the authentication manager used to authenticate the user
     * @param jwtUserDetailsService the service used to load user details from the database
     * @param secret the secret key used to sign and verify JWT tokens
     */
    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, JwtUserDetailsService jwtUserDetailsService, String secret) {
        super(authenticationManager);
        this.jwtUserDetailsService = jwtUserDetailsService;
        this.secret = secret;
    }


    /**
     * Authenticates the user by extracting the JWT token from the request header,
     * verifying it using the secret key, and loading the user details from the database based
     * on the username in the token. If the token is invalid or expired, the user is not authenticated
     * and the filter does not modify the SecurityContext.
     *
     * @param request the HTTP request
     * @param response the HTTP response
     * @param filterChain the filter chain
     * @throws IOException if an I/O error occurs while handling the request
     * @throws ServletException if a servlet-specific error occurs while handling the request
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)throws IOException, ServletException{
        UsernamePasswordAuthenticationToken auth = getAuthentication(request);
        if(auth == null){
            filterChain.doFilter(request,response);
            return;
        }
        SecurityContextHolder.getContext().setAuthentication(auth);
        filterChain.doFilter(request,response);

    }


    /**
     * Attempts to authenticate the user by extracting the JWT token from the request header,
     * verifying it using the secret key, and loading the user details from the database based
     * on the username in the token. If the token is invalid or expired, the user is not authenticated.
     *
     * @param request the HTTP request
     * @return an authentication token representing the authenticated user or null if the user is not authenticated
     */
    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        if(token == null || !token.startsWith(TOKEN_PREFIX)){
            return null;
        }
        String username = JWT.require(Algorithm.HMAC256(secret))
                .build()
                .verify(token.replace(TOKEN_PREFIX, ""))
                .getSubject();
        if(username == null){
            return null;
        }
        UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
