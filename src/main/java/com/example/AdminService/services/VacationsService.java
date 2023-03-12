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

@Service
public class VacationsService implements VacationsInterface {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private VacationsRepository vacationsRepository;

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

    @Override
    public void addSingleRow(int year, String email, int days) {
        Optional<Employee> employee = employeeRepository.findById(ShaEncryptionGenerator.hashString(email));
        if(employee.isPresent()){
            Vacations vacations = new Vacations();
            VacationsPK vacationsPK = new VacationsPK();
            vacationsPK.setYear(year);
            vacationsPK.setEmail(ShaEncryptionGenerator.hashString(email));
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

    @Override
    public void changeUsedDaysForYear(String emaill, int year, int days) {
        String email = ShaEncryptionGenerator.hashString(emaill);
        Optional<Employee> employee = employeeRepository.findById(email);
        if(employee.isPresent()){

            List<Vacations> vacations = vacationsRepository.findByVacationsPKYearIsAndVacationsPKEmailIs(year,email);
            if(vacations.size()==0){
               //TODO addSingleRow(year, emaill, days);
                //TODO ASK WHAT HAPPENS WHEN IMPORTING SOMETHING THAT IS NOT IN BASE
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
