package com.example.AdminService.interfaces.repositories;

import com.example.AdminService.entities.UsedVacations;
import org.springframework.data.jpa.repository.JpaRepository;
/**
 * The UsedVacationsRepository interface extends the JpaRepository interface, and provides
 * CRUD operations for the UsedVacations entity.
 * @author petar
 */
public interface UsedVacationsRepository extends JpaRepository<UsedVacations, String> {
}
