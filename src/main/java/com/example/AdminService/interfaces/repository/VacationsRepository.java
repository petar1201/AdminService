package com.example.AdminService.interfaces.repository;

import com.example.AdminService.entity.Vacations;
import com.example.AdminService.entity.VacationsPK;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


/**
 * The VacationsRepository interface extends the JpaRepository interface, and provides
 * CRUD operations for the Vacations entity.
 * @author petar
 */
public interface VacationsRepository extends JpaRepository<Vacations, VacationsPK> {
    /**
     * Returns a list of vacations for a given year and employee email.
     * @param year the year for which to retrieve vacations.
     * @param email the email of the employee for whom to retrieve vacations.
     * @return a List of Vacations matching the given year and email.
     */
    List<Vacations> findByVacationsPKYearIsAndVacationsPKEmailIs(int year, String email);
}
