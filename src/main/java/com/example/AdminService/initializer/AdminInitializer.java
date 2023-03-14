package com.example.AdminService.initializer;

import com.example.AdminService.entity.Admin;
import com.example.AdminService.interfaces.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


/**
 * AdminInitilazer is a class implementing CommandLineRunner
 * Used to add admin when booting aplication
 * admin petar@rbt.rs with password admin can be used as default admin
 */
@Component
public class AdminInitializer implements CommandLineRunner {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     *
     * run method will run when booting spring application
     * it creates Instance of Entity Admin and adds it to DataBase
     * @param args
     * @throws Exception
     */
    @Override
    public void run(String... args) throws Exception {
        String plainPassword = "admin";
        String encodedPassword = passwordEncoder.encode(plainPassword);
        Admin adminUser = new Admin();
        adminUser.setUsername("petar@rbt.rs");
        adminUser.setPassword(encodedPassword);
        adminRepository.saveAndFlush(adminUser);
    }
}
