package com.example.AdminService.interfaces.repositories;

import com.example.AdminService.entities.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The AdminRepository interface extends the JpaRepository interface, and provides
 * CRUD operations for the Admin entity.
 * @author petar
 */
public interface AdminRepository extends JpaRepository<Admin, String> {
}
