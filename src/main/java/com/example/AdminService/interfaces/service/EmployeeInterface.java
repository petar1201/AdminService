package com.example.AdminService.interfaces.service;

import com.example.AdminService.entities.Employee;

public interface EmployeeInterface {

    public void addEmployeeProfiles(String path);

    public void addSingleEmployee(String email, String password);

    public void removeEmployee(String email);

}
