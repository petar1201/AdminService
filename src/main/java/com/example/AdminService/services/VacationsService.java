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
                if(year == -1)break;//TODO ERROR FORM OF CSV
                addSingleRow(year, email, Integer.parseInt(days.trim().replaceAll("\n$", "")));
                //TODO WHAT IF IS NOT PRESENT?
            }
            sc.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        }

        //TODO THROW WHEN ERROR
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
        //TODO ELSE ERROR EMPLOYEE DOESNT EXIST
    }

    @Override
    public void changeUsedDaysForYear(String emaill, int year, int days) {
        String email = ShaEncryptionGenerator.hashString(emaill);
        Optional<Employee> employee = employeeRepository.findById(email);
        if(employee.isPresent()){

            List<Vacations> vacations = vacationsRepository.findByVacationsPKYearIsAndVacationsPKEmailIs(year,email);
            for(Vacations v: vacations){
                if(v.getTotalDays()-v.getUsedDays()>=days){
                    v.setUsedDays(v.getUsedDays()+days);
                    vacationsRepository.saveAndFlush(v);
                    break;
                }
                else{
                    int curYr=year-1;
                    long totalDays=0;
                    long usedDays=0;
                    long daysAvailable = v.getTotalDays()-v.getUsedDays();
                    boolean okay = false;
                    while(vacationsRepository.findByVacationsPKYearIsAndVacationsPKEmailIs(curYr,email).size()>0){
                        Vacations vacations1 = vacationsRepository.findByVacationsPKYearIsAndVacationsPKEmailIs(curYr, email).get(0);
                        totalDays = vacations1.getTotalDays();
                        usedDays = vacations1.getUsedDays();
                        if(totalDays-usedDays+daysAvailable>=days){
                            okay=true;
                            for(long i = year;i>=curYr;i--){
                                vacations1 = vacationsRepository.findByVacationsPKYearIsAndVacationsPKEmailIs(curYr, email).get(0);
                                if(year!=curYr)vacations1.setUsedDays(vacations1.getTotalDays());
                                else vacations1.setUsedDays(totalDays-(totalDays-usedDays+daysAvailable-days));
                                vacationsRepository.saveAndFlush(vacations1);
                            }
                            break;
                        }
                        daysAvailable+= totalDays-usedDays;
                        curYr--;
                    }
                    if(!okay){//TODO NOT ENOUGH DAYS ERROR?

                    }
                }


            }
        }
    }
}
