package com.example.AdminService.service;

import com.example.AdminService.entity.Employee;
import com.example.AdminService.entity.UsedVacations;
import com.example.AdminService.interfaces.repository.EmployeeRepository;
import com.example.AdminService.interfaces.repository.UsedVacationsRepository;
import com.example.AdminService.interfaces.service.UsedVacationsInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.sql.Date;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.time.Month;
import java.util.Optional;
import java.util.Scanner;



/**
 * UsedVacationsService class implements UsedVacationsInterface and provides
 * methods for managing used vacation days.
 */
@Service
public class UsedVacationsService implements UsedVacationsInterface{

    @Autowired
    private UsedVacationsRepository usedVacationsRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private VacationsService vacationsService;

    /**
     * Reads data from a CSV file located at the specified path and calls addSingleRow method to add a row for each employee to the database.
     * @param path The path of the CSV file to read data from.
     * @throws IllegalStateException If the specified file is not found.
     */
    @Override
    public void addUsedDaysPerYearPerEmployee(String path) {

        try{
            Scanner sc = new Scanner(new File(path));
            sc.useDelimiter("\n");
            boolean flag = false;
            while (sc.hasNext())  //returns a boolean value
            {   String line = sc.next().trim().replaceAll("\n$", "");
                String[] fields = line.split(",");
                if(fields[0].equals("Employee")){
                    flag = true;
                    continue;
                }
                else{
                    if(!flag)throw new IllegalStateException("Bad Form of CSV file");
                }
                String email = fields[0];
                String monthDayS = fields[2].trim();
                String year1 = fields[3].replaceAll("^\"|\"$", "").trim();
                String monthDayE = fields[5].trim();
                String year2 = fields[6].replaceAll("^\"|\"$", "").trim();
                String monthStart = monthDayS.split(" ")[0];
                String dayStart = monthDayS.split(" ")[1];
                String monthEnd = monthDayE.split(" ")[0];
                String dayEnd = monthDayE.split(" ")[1];
                addSingleRow(email, Integer.parseInt(year1),
                        Month.valueOf(monthStart.toUpperCase()).getValue(),
                        Integer.parseInt(dayStart),
                        Integer.parseInt(year2),
                        Month.valueOf(monthEnd.toUpperCase()).getValue(),
                        Integer.parseInt(dayEnd));
            }
            sc.close();
        } catch (FileNotFoundException e) {
            throw new IllegalStateException("File not found: " + e.getMessage());
        }
    }

    /**
     * Adds a single row for an employee's used vacation days to the database and updates their available days accordingly.
     * The start and end dates are used to calculate the number of business days between them, taking into account weekends and holidays.
     * @param emaill The email address of the employee.
     * @param year1 The year of the start date.
     * @param month1 The month of the start date.
     * @param day1 The day of the start date.
     * @param year2 The year of the end date.
     * @param month2 The month of the end date.
     * @param day2 The day of the end date.
     * @throws IllegalStateException If the start date is after the end date.
     */
    @Override
    public void addSingleRow(String emaill, int year1, int month1, int day1, int year2, int month2, int day2) {
        String email = emaill;
        Optional<Employee> employee = employeeRepository.findById(email);
        if(employee.isPresent()){
            Date startDate = createDate(year1, month1, day1);
            Date endDate = createDate(year2, month2, day2);
            if(startDate.compareTo(endDate)>0){
                throw new IllegalStateException("Start date is after End Date");
            }
            else{
                try {
                    if (year1 == year2) {
                        vacationsService.changeUsedDaysForYear(emaill, year1, (int) calcDays(startDate, endDate));
                    } else {
                        vacationsService.changeUsedDaysForYear(emaill, year1, (int) calcDays(startDate, createDate(year1, 12, 31)));
                        vacationsService.changeUsedDaysForYear(emaill, year2, (int) calcDays(createDate(year2, 1, 1), endDate));
                    }
                    UsedVacations usedVacations = new UsedVacations();
                    //usedVacations.setIdUsed(UsedVacations.id++);
                    usedVacations.setEmail(employee.get());
                    usedVacations.setStartDate(startDate);
                    usedVacations.setEndDate(endDate);
                    usedVacationsRepository.saveAndFlush(usedVacations);
                }
                catch(IllegalStateException e){
                    throw e;
                }
            }

        }
    }

    /**
     * Checks if given date is holiday day or not
     *
     * @param date the date to be checked
     * @return the boolean value representing if given date is holiday or not
     * */
    private boolean isHoliday(LocalDate date){
        int day = date.getDayOfMonth();
        int month = date.getMonthValue();
        if(month == 1){
            if(day==1 || day==2 || day == 3 || day == 7)return true;
        }
        else if(month == 2){
            if(day == 15 || day ==16)return true;
        }
        else if(month == 5){
            if(day == 1 || day == 2)return true;
        }
        else if(month == 11){
            if(day == 11) return true;
        }
        return false;
    }

    /**
     * Calculates the number of days between the given start and end dates.
     *
     * @param startDate the start date
     * @param endDate the end date
     * @return the number of days between the start and end dates
     */
    @Override
    public long calcDays(Date startDate, Date endDate) {
        LocalDate starttDate = startDate.toLocalDate();
        LocalDate enddDate = endDate.toLocalDate();
        long daysBetween = ChronoUnit.DAYS.between(starttDate, enddDate)+1;
        long businessDays = 0;
        for (int i = 0; i < daysBetween; i++) {
            LocalDate date = starttDate.plusDays(i);
            if (date.getDayOfWeek() != DayOfWeek.SATURDAY && date.getDayOfWeek() != DayOfWeek.SUNDAY) {
                if(!isHoliday(date)) businessDays++;
            }
        }
        return businessDays;
    }

    /**
     * Creates a new Date object with the given year, month, and day.
     *
     * @param year the year of the date
     * @param month the month of the date (1-12)
     * @param day the day of the date
     * @return the new Date object
     */
    @Override
    public Date createDate(int year, int month, int day) {
        LocalDate date = LocalDate.of(year, month, day);
        long millis = date.atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli();
        Date datum = new Date(millis);
        return datum;
    }

}
