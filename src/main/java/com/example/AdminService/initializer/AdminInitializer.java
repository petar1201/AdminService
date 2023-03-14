package com.example.AdminService.initializer;

import com.example.AdminService.entity.Admin;
import com.example.AdminService.interfaces.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AdminInitializer implements CommandLineRunner {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        String plainPassword = "admin";
        String encodedPassword = passwordEncoder.encode(plainPassword);
        Admin adminUser = new Admin();
        adminUser.setUsername("admin");
        adminUser.setPassword(encodedPassword);
        adminRepository.saveAndFlush(adminUser);
    }
}
