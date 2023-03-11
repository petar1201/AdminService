package com.example.AdminService.interfaces.repositories;

import com.example.AdminService.entities.UsedVacations;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsedVacationsRepository extends JpaRepository<UsedVacations, String> {
}
