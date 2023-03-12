package com.example.AdminService.interfaces.service;

import java.sql.Date;
import java.time.LocalDate;


/**
 * The UsedVacationsInterface provides methods for managing used vacation days for employees.
 * @author petar
 */
public interface UsedVacationsInterface {

    /**
     * Adds used vacation days for each employee from the given file path.
     *
     * @param path the file path containing used vacation days data
     */
    public void addUsedDaysPerYearPerEmployee(String path);

    /**
     * Adds a single row of used vacation days for an employee with the given email address
     * and start and end dates.
     *
     * @param emaill the email address of the employee
     * @param year1 the year of the start date
     * @param month1 the month of the start date (1-12)
     * @param day1 the day of the start date
     * @param year2 the year of the end date
     * @param month2 the month of the end date (1-12)
     * @param day2 the day of the end date
     */
    public void addSingleRow(String emaill, int year1, int month1, int day1
                                         , int year2, int month2, int day2);

    /**
     * Calculates the number of days between the given start and end dates.
     *
     * @param startDate the start date
     * @param endDate the end date
     * @return the number of days between the start and end dates
     */
    public long calcDays(Date startDate, Date endDate);

    /**
     * Creates a new Date object with the given year, month, and day.
     *
     * @param year the year of the date
     * @param month the month of the date (1-12)
     * @param day the day of the date
     * @return the new Date object
     */
    public Date createDate(int year, int month, int day);



}
