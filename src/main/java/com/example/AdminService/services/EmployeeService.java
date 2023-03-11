package com.example.AdminService.services;


import com.example.AdminService.entities.Employee;
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
        List<Employee> employeeList = new ArrayList<>();

        //String csvFile = "../../../employee_profiles.csv";

        try (CSVReader reader = new CSVReader(new FileReader(path))) {
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                String email = nextLine[0];
                String password = nextLine[1];
                if(email.equals("Employee Email"))continue;
                employeeList.add(new Employee(email, password));
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }catch (CsvValidationException e) {
            e.printStackTrace();
        }

        //TODO THROW WHEN ERROR


        employeeRepository.saveAllAndFlush(employeeList);
    }

    @Override
    public void addSingleEmployee(String email, String password) {
        employeeRepository.saveAndFlush(new Employee(email,password));
    }

    @Override
    public void removeEmployee(String email) {
        Optional<Employee> employee = employeeRepository.findById(email);
        if(employee.isPresent()) {
            employee.get().setActive(false);
            employeeRepository.save(employee.get());
        }
    }


}
