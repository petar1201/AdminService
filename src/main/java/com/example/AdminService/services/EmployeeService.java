package com.example.AdminService.services;


import com.example.AdminService.entities.Employee;
import com.example.AdminService.interfaces.repositories.EmployeeRepository;
import com.example.AdminService.interfaces.service.EmployeeInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Optional;
import java.util.Scanner;


/**
 * EmployeeService class implements EmployeeInterface and provides
 * methods for adding and removing employee profiles.
 */
@Service
public class EmployeeService implements EmployeeInterface {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Adds employee profiles to the database from a CSV file.
     * @param path the path of the CSV file.
     * @throws IllegalStateException if the CSV file is not found.
     */
    @Override
    public void addEmployeeProfiles(String path) {

       // String csvFile = "C:\\Users\\petar\\Desktop\\TehnicalTaskRBT\\Technical assignment\\Samples\\employee_profiles.csv";

        try{
            Scanner sc = new Scanner(new File(path));
            sc.useDelimiter("\n");
            while (sc.hasNext())  //returns a boolean value
            {   String line = sc.next();
                String email = line.split(",")[0].trim();
                String password = line.split(",")[1].trim();
                if(email.equals("Employee Email"))continue;
                addSingleEmployee(email, password);
            }
            sc.close();
        } catch (FileNotFoundException e) {
           throw new IllegalStateException("File not found: " + e.getMessage());
        }

    }

    /**
     * Adds a single employee profile to the database.
     * @param email the email of the employee.
     * @param password the password of the employee.
     */
    @Override
    public void addSingleEmployee(String email, String password) {
        Employee employee = new Employee(email,
                passwordEncoder.encode(password));
        employee.setActive(true);
        employeeRepository.saveAndFlush(employee);
    }

    /**
     * Removes an employee profile from the database.
     * @param email the email of the employee to be removed.
     * @throws IllegalStateException if the employee does not exist.
     */
    @Override
    public void removeEmployee(String email) {
        Optional<Employee> employee = employeeRepository.findById(email);
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
