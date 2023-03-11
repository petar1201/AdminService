package com.example.AdminService.services;

import com.example.AdminService.entities.Employee;
import com.example.AdminService.entities.Vacations;
import com.example.AdminService.entities.VacationsPK;
import com.example.AdminService.interfaces.repositories.EmployeeRepository;
import com.example.AdminService.interfaces.repositories.VacationsRepository;
import com.example.AdminService.interfaces.service.VacationsInterface;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Optional;

@Service
public class VacationsService implements VacationsInterface {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private VacationsRepository vacationsRepository;

    @Override
    public void addDaysPerYearPerEmployee(String path) {

        int year=-1;

        try (CSVReader reader = new CSVReader(new FileReader(path))) {
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                String email = nextLine[0];
                String days = nextLine[1];
                if(email.equals("Employee"))continue;
                if(email.equals("Vacation year")){
                    year=Integer.parseInt(days);
                    continue;
                }
                if(year == -1)break;//TODO ERROR FORM OF CSV
                addSingleRow(year, email, Integer.parseInt(days));
                //TODO WHAT IF IS NOT PRESENT?
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
    public void addSingleRow(int year, String email, int days) {
        Optional<Employee> employee = employeeRepository.findById(email);
        if(employee.isPresent()){
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
        //TODO ELSE ERROR EMPLOYEE DOESNT EXIST
    }
}
