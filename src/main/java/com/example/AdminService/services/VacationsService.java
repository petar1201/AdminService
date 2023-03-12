package com.example.AdminService.services;

import com.example.AdminService.entities.Employee;
import com.example.AdminService.entities.Vacations;
import com.example.AdminService.entities.VacationsPK;
import com.example.AdminService.generators.ShaEncryptionGenerator;
import com.example.AdminService.interfaces.repositories.EmployeeRepository;
import com.example.AdminService.interfaces.repositories.VacationsRepository;
import com.example.AdminService.interfaces.service.VacationsInterface;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;


/**
 * The VacationsService class implements the VacationsInterface and provides methods for managing vacation days for employees.
 * @author petar
 */
@Service
public class VacationsService implements VacationsInterface {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private VacationsRepository vacationsRepository;


    /**
     * This method reads the vacation data from a file and adds the days per year per employee.
     * @param path the path of the file containing the vacation data.
     * @throws IllegalStateException if the file is not found or if the header is incorrect.
     */
    @Override
    public void addDaysPerYearPerEmployee(String path) {

        int year=-1;

        try{
            Scanner sc = new Scanner(new File(path));
            sc.useDelimiter("\n");
            while (sc.hasNext())  //returns a boolean value
            {   String nextLine = sc.next();
                String email = nextLine.split(",")[0];
                String days = nextLine.split(",")[1];
                if(email.equals("Employee"))continue;
                if(email.equals("Vacation year")){
                    year=Integer.parseInt(""+days.charAt(0)+days.charAt(1)+days.charAt(2)+days.charAt(3));
                    continue;
                }
                if(year == -1)throw new IllegalStateException("Bad header, unknown year");
                addSingleRow(year, email, Integer.parseInt(days.trim().replaceAll("\n$", "")));
            }
            sc.close();
        } catch (FileNotFoundException e) {
            throw new IllegalStateException("File not found: " + e.getMessage());
        }
    }

    /**
     * This method adds a single row of vacation data for an employee in a specific year.
     * If the vacation data already exists for the employee in the given year, the total days are updated.
     * @param year the year of the vacation data.
     * @param email the email of the employee.
     * @param days the number of vacation days.
     * @throws IllegalStateException if the employee does not exist.
     */
    @Override
    public void addSingleRow(int year, String email, int days) {
        Optional<Employee> employee = employeeRepository.findById(email);
        if(employee.isPresent()){
            if(vacationsRepository.findByVacationsPKYearIsAndVacationsPKEmailIs(year, email).size()>0){
                Vacations vacations = vacationsRepository.findByVacationsPKYearIsAndVacationsPKEmailIs(year, email).get(0);
                vacations.setTotalDays(vacations.getTotalDays()+days);
                vacationsRepository.saveAndFlush(vacations);
                return;
            }
            Vacations vacations = new Vacations();
            VacationsPK vacationsPK = new VacationsPK();
            vacationsPK.setYear(year);
            vacationsPK.setEmail(email);
            vacations.setVacationsPK(vacationsPK);
            vacations.setEmployee(employee.get());
            vacations.setTotalDays(days);
            vacations.setUsedDays(0);
            vacationsRepository.saveAndFlush(vacations);
        }
        else{
            throw new IllegalStateException("Trying to add vacation days for Employee who doesnt exist");
        }
    }

    /**
     * This method changes the used vacation days for an employee in a specific year.
     * If the vacation data does not exist for the employee in the given year, a new row is added.
     * @param emaill the email of the employee.
     * @param year the year of the vacation data.
     * @param days the number of used vacation days to be added.
     * @throws IllegalStateException if the employee does not exist.
     */
    @Override
    public void changeUsedDaysForYear(String emaill, int year, int days) {
        String email = emaill;
        Optional<Employee> employee = employeeRepository.findById(email);
        if(employee.isPresent()){

            List<Vacations> vacations = vacationsRepository.findByVacationsPKYearIsAndVacationsPKEmailIs(year,email);
            if(vacations.size()==0){
               addSingleRow(year, emaill, days);
                vacations = vacationsRepository.findByVacationsPKYearIsAndVacationsPKEmailIs(year,email);
                vacations.get(0).setUsedDays(days);
                return;
            }
            for(Vacations v: vacations){
                v.setUsedDays(v.getUsedDays()+days);
                v.setTotalDays(v.getTotalDays()+days);
                vacationsRepository.saveAndFlush(v);
                //return;
            }
            //throw new IllegalStateException("Error when adding new record");
        }
        else{
            throw new IllegalStateException("Employee doesnt exist, error when adding new record");
        }
    }
}
