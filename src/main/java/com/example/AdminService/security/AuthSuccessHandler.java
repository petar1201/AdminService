package com.example.AdminService.security;


import com.auth0.jwt.algorithms.Algorithm;
import com.example.AdminService.service.AdminService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import com.auth0.jwt.JWT;
import java.io.IOException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;


/**
 * This class handles successful authentication requests and generates a JSON Web Token (JWT) for the authenticated user.
 * The JWT is then added to the response headers as an Authorization Bearer token and also returned in the response body as a JSON object.
 */
@Component
@Slf4j
public class AuthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    /**
     * The time in seconds for which the JWT token will be valid.
     */
    private final int expTime;

    /**
     * The secret key used to sign the JWT token.
     */
    private final String secret;

    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * The service for managing admins.
     */
    private final AdminService adminService;


    /**
     * Constructs a new instance of the AuthSuccessHandler class.
     * @param expTime The time in seconds for which the JWT token will be valid.
     * @param secret The secret key used to sign the JWT token.
     * @param adminService The service for managing admins.
     */
    public AuthSuccessHandler(@Value("${jwt.expiration}") int expTime, @Value("${jwt.secret}") String secret, AdminService adminService) {
        this.expTime = expTime;
        this.secret = secret;
        this.adminService = adminService;
    }


    /**
     * Generates a JWT token for the authenticated user and adds it to the response headers and body.
     * @param request The HTTP servlet request.
     * @param response The HTTP servlet response.
     * @param authentication The authentication object representing the authenticated user.
     * @throws IOException If an I/O error occurs while writing to the response.
     * @throws ServletException If an error occurs while handling the authentication success.
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                       Authentication authentication)throws IOException, ServletException {
        UserDetails principal = (UserDetails) authentication.getPrincipal();
        String token = JWT.create()
                .withSubject(adminService.findAdminByUserName(principal.getUsername()).getUsername())
                .withExpiresAt(Date.from(Instant.ofEpochMilli(ZonedDateTime.now(ZoneId.systemDefault()).toInstant().toEpochMilli()+expTime)))
                .sign(Algorithm.HMAC256(secret));
        response.addHeader("Authorization", "Bearer " + token);
        response.addHeader("Content-Type", "application/json");
        response.getWriter().write("{\"token\": \""+token+"\"}");
    }


}
