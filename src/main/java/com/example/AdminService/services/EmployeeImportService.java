package com.example.AdminService.services;


import com.example.AdminService.entities.Employee;
import com.example.AdminService.interfaces.repositories.EmployeeRepository;
import com.example.AdminService.interfaces.service.EmployeeInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeImportService implements EmployeeInterface {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public void addEmployeeProfiles(String path) {
        List<Employee> employeeList = new ArrayList<>();
        //TODO ReadCSV, create Employees and add them to List
        employeeRepository.saveAllAndFlush(employeeList);
    }
}
