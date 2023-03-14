package com.example.AdminService.service;

import com.example.AdminService.entity.Admin;
import com.example.AdminService.interfaces.repository.AdminRepository;
import com.example.AdminService.interfaces.service.AdminInterface;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * The AdminService class implements the AdminInterface and provides methods for managing admins.
 * @author petar
 */
@Service
public class AdminService implements AdminInterface {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AdminRepository adminRepository;

    public Admin findAdminByUserName(String username){
        Optional<Admin> byId = adminRepository.findById(username);
        if(byId.isPresent())return byId.get();
        return null;
    }


    /**
     * Adds a new admin to the system with a generated API key and token.
     */
    @Override
    public void addAdmin(String username, String password) {
        adminRepository.saveAndFlush(new Admin(username, passwordEncoder.encode(password) ));
    }

    /**
     * Removes an admin from the system by setting their status to inactive.
     * @param username the username of the admin to remove
     * @throws IllegalStateException if the admin is deleting themselves or if the admin cannot be found
     */
    @Override
    public void removeAdmin(String username) {
        Optional<Admin> admin = adminRepository.findById(username);
        if(admin.isPresent()) {
            if(admin.get().isActive()) {
                admin.get().setActive(false);
                adminRepository.save(admin.get());
            }
        }
        else{
            throw new IllegalStateException("UNEXPECETED ERROR, ADMIN DELETING HIMSELF AND NOT FOUND");
        }
    }
}
