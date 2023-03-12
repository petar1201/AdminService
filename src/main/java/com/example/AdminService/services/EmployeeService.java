package com.example.AdminService.services;


import com.example.AdminService.entities.Employee;
import com.example.AdminService.generators.ShaEncryptionGenerator;
import com.example.AdminService.interfaces.repositories.EmployeeRepository;
import com.example.AdminService.interfaces.service.EmployeeInterface;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import com.opencsv.CSVReader;

@Service
public class EmployeeService implements EmployeeInterface {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public void addEmployeeProfiles(String path) {

       // String csvFile = "C:\\Users\\petar\\Desktop\\TehnicalTaskRBT\\Technical assignment\\Samples\\employee_profiles.csv";

        try{
            Scanner sc = new Scanner(new File(path));
            sc.useDelimiter("\n");
            while (sc.hasNext())  //returns a boolean value
            {   String line = sc.next();
                String email = line.split(",")[0];
                String password = line.split(",")[1];
                if(email.equals("Employee Email"))continue;
                addSingleEmployee(email, password);
            }
            sc.close();
        } catch (FileNotFoundException e) {
           throw new IllegalStateException("File not found: " + e.getMessage());
        }

    }

    @Override
    public void addSingleEmployee(String email, String password) {
        Employee employee = new Employee(ShaEncryptionGenerator.hashString(email),
                ShaEncryptionGenerator.hashString(password));
        employee.setActive(true);
        employeeRepository.saveAndFlush(employee);
    }

    @Override
    public void removeEmployee(String email) {
        Optional<Employee> employee = employeeRepository.findById(ShaEncryptionGenerator.hashString(email));
        if(employee.isPresent()) {
            if(employee.get().isActive()) {
                employee.get().setActive(false);
                employeeRepository.saveAndFlush(employee.get());
            }
        }
        else{
            throw new IllegalStateException("Employee doesnt exist");
        }
    }


}
