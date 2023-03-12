package com.example.AdminService.services;

import com.example.AdminService.entities.Employee;
import com.example.AdminService.entities.UsedVacations;
import com.example.AdminService.generators.ShaEncryptionGenerator;
import com.example.AdminService.interfaces.repositories.EmployeeRepository;
import com.example.AdminService.interfaces.repositories.UsedVacationsRepository;
import com.example.AdminService.interfaces.service.UsedVacationsInterface;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.sql.Date;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.time.Month;
import java.util.Locale;
import java.util.Optional;
import java.util.Scanner;

@Service
public class UsedVacationsService implements UsedVacationsInterface{

    @Autowired
    private UsedVacationsRepository usedVacationsRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private VacationsService vacationsService;

    @Override
    public void addUsedDaysPerYearPerEmployee(String path) {

        try{
            Scanner sc = new Scanner(new File(path));
            sc.useDelimiter("\n");
            while (sc.hasNext())  //returns a boolean value
            {   String line = sc.next().trim().replaceAll("\n$", "");
                String[] fields = line.split(",");
                if(fields[0].equals("Employee"))continue;
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

    @Override
    public void addSingleRow(String emaill, int year1, int month1, int day1, int year2, int month2, int day2) {
        String email = ShaEncryptionGenerator.hashString(emaill);
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

    @Override
    public Date createDate(int year, int month, int day) {
        LocalDate date = LocalDate.of(year, month, day);
        long millis = date.atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli();
        Date datum = new Date(millis);
        return datum;
    }

}
