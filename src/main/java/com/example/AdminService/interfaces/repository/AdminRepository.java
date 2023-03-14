package com.example.AdminService.interfaces.repository;

import com.example.AdminService.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The AdminRepository interface extends the JpaRepository interface, and provides
 * CRUD operations for the Admin entity.
 * @author petar
 */
public interface AdminRepository extends JpaRepository<Admin, String> {
}
