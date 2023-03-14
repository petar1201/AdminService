package com.example.AdminService.security.config;


import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


/**
 * Class for SecurityConfigs, used as escape of circular dependency
 *
 * */
@EnableWebSecurity
@Component
@Configuration
public class SecurityBeans {
    /**
     * Returns a PasswordEncoder instance that uses the BCrypt algorithm for password hashing.
     * The returned instance can be used to encode passwords for secure storage and comparison.
     *
     * @return A PasswordEncoder instance using the BCrypt algorithm.
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /**
     * Returns an  instance using the provided  AuthenticationConfiguration.
     * The returned instance can be used to authenticate users based on their credentials.
     *
     * @param authenticationConfiguration AuthenticationConfiguration instance to use for configuration.
     * @return An AuthenticationManager instance based on the provided configuration.
     * @throws Exception If an error occurs during configuration or instantiation of the authentication manager.
     */
    @SneakyThrows
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration){
        return authenticationConfiguration.getAuthenticationManager();
    }

}
