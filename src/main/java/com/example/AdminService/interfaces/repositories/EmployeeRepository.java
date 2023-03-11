package com.example.AdminService.interfaces.repositories;

import com.example.AdminService.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
