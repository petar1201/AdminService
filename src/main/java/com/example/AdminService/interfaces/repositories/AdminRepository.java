package com.example.AdminService.interfaces.repositories;

import com.example.AdminService.entities.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, String> {
}
