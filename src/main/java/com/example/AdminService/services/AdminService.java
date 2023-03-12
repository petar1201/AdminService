package com.example.AdminService.services;

import com.example.AdminService.entities.Admin;
import com.example.AdminService.entities.Employee;
import com.example.AdminService.generators.ApiKeyGenerator;
import com.example.AdminService.generators.TokenGenerator;
import com.example.AdminService.interfaces.repositories.AdminRepository;
import com.example.AdminService.interfaces.repositories.EmployeeRepository;
import com.example.AdminService.interfaces.service.AdminInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * The AdminService class implements the AdminInterface and provides methods for managing admins.
 * @author petar
 */
@Service
public class AdminService implements AdminInterface {

    @Autowired
    private AdminRepository adminRepository;

    /**
     * Adds a new admin to the system with a generated API key and token.
     */
    @Override
    public void addAdmin() {
        adminRepository.saveAndFlush(new Admin(ApiKeyGenerator.generateApiKey(), TokenGenerator.generateToken() ));
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
