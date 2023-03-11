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
        try (CSVReader reader = new CSVReader(new FileReader(path))) {
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                String email = nextLine[0];
                String startDate = nextLine[1];
                String endDate = nextLine[2];
                if(email.equals("Employee"))continue;
                String[] partsStart = startDate.split(",\\s+");
                String[] monthDayStart = partsStart[1].split("\\s+");

                String[] partsEnd = endDate.split(",\\s+");
                String[] monthDayEnd = partsEnd[1].split("\\s+");

                addSingleRow(email, Integer.parseInt(partsStart[2]),
                        Month.valueOf(monthDayStart[0].toUpperCase()).getValue(),
                        Integer.parseInt(monthDayStart[1]),
                        Integer.parseInt(partsEnd[2]),
                        Month.valueOf(monthDayEnd[0].toUpperCase()).getValue(),
                        Integer.parseInt(monthDayEnd[1]));
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
    public void addSingleRow(String emaill, int year1, int month1, int day1, int year2, int month2, int day2) {
        String email = ShaEncryptionGenerator.hashString(emaill);
        Optional<Employee> employee = employeeRepository.findById(ShaEncryptionGenerator.hashString(email));
        if(employee.isPresent()){
            Date startDate = createDate(year1, month1, day1);
            Date endDate = createDate(year2, month2, day2);
            if(startDate.compareTo(endDate)>=0){
                //TODO DO NOTHING IF EQUAL?THROW EXCEPTION?
            }
            else{
                if(year1==year2) {
                    vacationsService.changeUsedDaysForYear(email, year1, (int) calcDays(startDate, endDate));
                }
                else{
                    vacationsService.changeUsedDaysForYear(email, year1, (int)calcDays(startDate, createDate(year1,12,31)));
                    vacationsService.changeUsedDaysForYear(email, year2, (int)calcDays(createDate(year2, 1,1), endDate));
                }
                UsedVacations usedVacations = new UsedVacations();
                usedVacations.setEmployee(employee.get());
                usedVacations.setEmail(email);
                usedVacations.setStartDate(startDate);
                usedVacations.setEndDate(endDate);
                usedVacationsRepository.saveAndFlush(usedVacations);
                //TODO TRY CATCH IF NOT ENOUGH DAYS FOR VACATION?
            }

        }
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
                //TODO CHECK IF DATE IS A HOLIDAY(DEFINE HOLIDAYS)
                businessDays++;
            }
        }
        return businessDays;
    }

    @Override
    public Date createDate(int year, int month, int day) {
        LocalDate date = LocalDate.of(year, month, day);
        long millis = date.atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli();
        Date datum = new Date(millis);//TODO ERROR BAD PARAMETRRS
        return datum;
    }

}
