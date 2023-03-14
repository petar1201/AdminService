package com.example.AdminService.security;


import com.example.AdminService.service.JwtUserDetailsService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;

@Configuration
public class SecurityConfig {
    @Autowired
    private AuthenticationManager authenticationManager;
    private final AuthSuccessHandler authSuccessHandler;
    private final JwtUserDetailsService jwtUserDetailsService;
    private final String secret;

    @Autowired
    private PasswordEncoder passwordEncoder;



    public SecurityConfig(AuthSuccessHandler authSuccessHandler, JwtUserDetailsService jwtUserDetailsService, @Value("${jwt.secret}") String secret) {
        this.authSuccessHandler = authSuccessHandler;
        this.jwtUserDetailsService = jwtUserDetailsService;
        this.secret = secret;
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http
                .cors()
                .and()
                .csrf()
                .disable()
                .authorizeHttpRequests((auth)-> {
                            try {
                                auth.anyRequest().permitAll()
                                .and()
                                .sessionManagement()
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                                .and()
                                .addFilter(authenticationFilter())
                                .addFilter(new JwtAuthorizationFilter(authenticationManager, jwtUserDetailsService, secret))
                                .exceptionHandling()
                                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED));
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                        })
                .httpBasic(Customizer.withDefaults());
        return http.build();
    }


    @Bean JsonObjectAuthenticationFilter authenticationFilter()throws Exception{
        JsonObjectAuthenticationFilter filter = new JsonObjectAuthenticationFilter();
        filter.setAuthenticationSuccessHandler(authSuccessHandler);
        filter.setAuthenticationManager(authenticationManager);
        return filter;


    }
}
