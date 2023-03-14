package com.example.AdminService.security;

import com.example.AdminService.security.domain.LoginCredentials;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * A custom authentication filter that reads authentication credentials from a JSON object in the request body.
 * This filter is designed to work with Spring Security's AuthenticationManager to authenticate a user.
 */
public class JsonObjectAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Reads authentication credentials from a JSON object in the request body, constructs an {@link Authentication}
     * object using the provided AuthenticationManager, and returns the result.
     *
     * @param request  the request to be authenticated
     * @param response the response
     * @return an Authentication object if the authentication is successful; null otherwise
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response){
        try{
            BufferedReader reader = request.getReader();
            StringBuilder sb = new StringBuilder();
            String line;
            while((line = reader.readLine()) != null){
                sb.append(line);
            }
            LoginCredentials authRequest = objectMapper.readValue(sb.toString(), LoginCredentials.class);
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                    authRequest.getEmail(), authRequest.getPassword()
            );
            setDetails(request, token);
            return this.getAuthenticationManager().authenticate(token);
        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }

}
