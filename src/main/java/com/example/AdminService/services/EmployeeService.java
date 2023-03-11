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

        //String csvFile = "../../../employee_profiles.csv";

        try (CSVReader reader = new CSVReader(new FileReader(path))) {
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                String email = nextLine[0];
                String password = nextLine[1];
                if(email.equals("Employee Email"))continue;
                addSingleEmployee(email, password);
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }catch (CsvValidationException e) {
            e.printStackTrace();
        }

        //TODO THROW WHEN ERROR


    }

    @Override
    public void addSingleEmployee(String email, String password) {
        //TODO ENCRIPTION
        employeeRepository.saveAndFlush(new Employee(ShaEncryptionGenerator.hashString(email),
                ShaEncryptionGenerator.hashString(password)));
    }

    @Override
    public void removeEmployee(String email) {
        Optional<Employee> employee = employeeRepository.findById(ShaEncryptionGenerator.hashString(email));
        if(employee.isPresent()) {
            if(employee.get().isActive()) {
                employee.get().setActive(false);
                employeeRepository.save(employee.get());
            }
        }
        //TODO ERROR TRYING TO DELETE EMPLOYEE, EMPLOYEE DOESNT EXIST
    }


}
