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


@Service
public class AdminService implements AdminInterface {

    @Autowired
    private AdminRepository adminRepository;


    @Override
    public void addAdmin() {
        adminRepository.saveAndFlush(new Admin(ApiKeyGenerator.generateApiKey(), TokenGenerator.generateToken() ));
    }

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
