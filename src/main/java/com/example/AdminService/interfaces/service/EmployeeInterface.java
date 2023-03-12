package com.example.AdminService.interfaces.service;

import com.example.AdminService.entities.Employee;

/**
 * The EmployeeInterface provides methods for adding, removing, and managing employee profiles.
 * @author petar
 */
public interface EmployeeInterface {

    /**
     * Adds employee profiles from the given file path to the system.
     *
     * @param path the file path containing employee profiles
     */
    public void addEmployeeProfiles(String path);

    /**
     * Adds a single employee profile with the given email and password to the system.
     *
     * @param email the email address of the employee
     * @param password the password for the employee's account
     */
    public void addSingleEmployee(String email, String password);

    /**
     * Removes the employee profile with the given email address from the system.
     *
     * @param email the email address of the employee to remove
     */
    public void removeEmployee(String email);

}
