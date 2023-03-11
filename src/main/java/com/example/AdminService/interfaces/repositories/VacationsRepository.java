package com.example.AdminService.interfaces.repositories;

import com.example.AdminService.entities.Vacations;
import com.example.AdminService.entities.VacationsPK;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VacationsRepository extends JpaRepository<Vacations, VacationsPK> {
    List<Vacations> findByVacationsPKYearIsAndVacationsPKEmailIs(int year, String email);
}
