package com.example.AdminService.interfaces.repositories;

import com.example.AdminService.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The EmployeeRepository interface extends the JpaRepository interface, and provides
 * CRUD operations for the Employee entity.
 * @author petar
 */
public interface EmployeeRepository extends JpaRepository<Employee, String> {
}
