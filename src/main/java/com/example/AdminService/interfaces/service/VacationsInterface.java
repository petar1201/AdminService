package com.example.AdminService.interfaces.service;

public interface VacationsInterface {

    public void addDaysPerYearPerEmployee(String path);

    public void addSingleRow(int year, String email, int days);

    public void changeUsedDaysForYear(String emaill, int year, int days);

}
