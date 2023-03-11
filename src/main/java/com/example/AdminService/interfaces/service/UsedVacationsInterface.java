package com.example.AdminService.interfaces.service;

import java.sql.Date;
import java.time.LocalDate;

public interface UsedVacationsInterface {

    public void addUsedDaysPerYearPerEmployee(String path);

    public void addSingleRow(String email, int year1, int month1, int day1
                                         , int year2, int month2, int day2);

    public long calcDays(Date startDate, Date endDate);

    public Date createDate(int year, int month, int day);



}
