package com.example.AdminService.interfaces.repository;

import com.example.AdminService.entity.UsedVacations;
import org.springframework.data.jpa.repository.JpaRepository;
/**
 * The UsedVacationsRepository interface extends the JpaRepository interface, and provides
 * CRUD operations for the UsedVacations entity.
 * @author petar
 */
public interface UsedVacationsRepository extends JpaRepository<UsedVacations, String> {
}
