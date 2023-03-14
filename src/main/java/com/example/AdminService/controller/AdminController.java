package com.example.AdminService.controller;


import com.example.AdminService.service.AdminService;
import com.example.AdminService.service.EmployeeService;
import com.example.AdminService.service.UsedVacationsService;
import com.example.AdminService.service.VacationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * RestController class that handles requests related to administrative operations such as creating and deleting admin accounts,
 * managing employee accounts, managing vacation days
 * @author petar
 */
@RestController
@RequestMapping(path="api/admin")
public class AdminController {

    /**
     * Path to folder where sample files are
     */
    private final String samplePath="C:\\Users\\petar\\Desktop\\TehnicalTaskRBT\\AdminService\\src\\main\\resources\\file\\";

    @Autowired
    private AdminService adminService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private VacationsService vacationsService;

    @Autowired
    private UsedVacationsService usedVacationsService;


    /**
     * Creates a new admin account.
     * @param username username of admin to be added
     * @param password password of admin to be added
     * @return String message indicating that a new admin account has been created.
     */
    @PostMapping(path="new")
    @ResponseBody
    public String createNewAdmin(@RequestParam(name = "username", required = true) String username,
                                 @RequestParam(name="password", required = true) String password){
        adminService.addAdmin(username,password);
        return "New Admin created";
    }

    /**
     * Deletes the currently logged-in admin account.
     *
     * @return String message indicating that the admin account has been deleted.
     */
    @DeleteMapping(path="delete")
    @ResponseBody
    public String deleteAdmin(@RequestParam(name = "username", required = true) String username
                              ){
        adminService.removeAdmin(username);
        return "Admin deleted";
    }

    /**
     * Adds a new employee account.
     *
     * @param email the email address of the new employee
     * @param password the password for the new employee account
     * @return String message indicating that the new employee account has been created.
     */
    @PostMapping(path="employee/new")
    @ResponseBody
    public String addOneEmployee(@RequestParam(name = "email", required = true) String email,
                                 @RequestParam(name="password", required = true) String password){
        employeeService.addSingleEmployee(email, password);
        return "Employee " + email + " created";
    }

    /**
     * Imports employee account information from a CSV file and adds it to the database.
     *
     * @param path the path to the CSV file containing the employee account information
     * @return String message indicating that the employee account information has been loaded.
     */
    @PostMapping(path="employee/import")
    @ResponseBody
    public String importEmployeesFromCsv(@RequestParam(name="path", required = true) String path){
        employeeService.addEmployeeProfiles(samplePath+path);
        return "Employees loaded";
    }


    /**
     * Deletes an employee account.
     *
     * @param email the email address of the employee account to be deleted
     * @return String message indicating that the employee account has been deleted.
     */
    @DeleteMapping(path="employee/delete")
    @ResponseBody
    public String deleteEmployee(@RequestParam(name="email", required = true) String email){
        employeeService.removeEmployee(email);
        return "Employee " + email + " removed";
    }

    /**
     * Adds vacation days for an employee for a given year.
     *
     * @param email the email address of the employee
     * @param year the year for which vacation days are being added
     * @param days the number of vacation days being added
     * @return String message indicating that the vacation days have been added for the employee.
     */
    @PostMapping(path="/vacactions/new")
    @ResponseBody
    public String addVacationDaysForYearForEmployee(
            @RequestParam(name = "email", required = true)String email,
            @RequestParam(name = "year", required = true)int year,
            @RequestParam(name = "days", required = true)int days
    ){
        vacationsService.addSingleRow(year, email, days);
        return "New data for Employee " + email + " loaded";
    }


    /**
     * Imports vacation days for the employees from the provided file path and adds them to the system.
     * @param path the file path of the data to be imported
     * @return a string indicating that new data for employees has been loaded
     */
    @PostMapping(path="/vacations/import")
    @ResponseBody
    public String  importVacationDaysForYearForEmployee(
            @RequestParam(name="path", required = true)String path
    ){
        vacationsService.addDaysPerYearPerEmployee(samplePath+path);
        return "New data for Employees loaded";
    }


    /**
     * Adds a new used vacation row for a specified employee with the provided start and end dates.
     * @param email the email address of the employee
     * @param startDate the start date of the vacation, in the format "dd mm yyyy"
     * @param endDate the end date of the vacation, in the format "dd mm yyyy"
     * @return a string indicating that new data for the specified employee has been loaded
     * @throws IllegalStateException if the provided dates are not in the correct format
     */
    @PostMapping(path="/usedVacations/new")
    @ResponseBody
    public String addUsedVacationForEmployee(
            @RequestParam(name = "email", required = true)String email,
            @RequestParam(name = "startDate", required = true)String startDate,
            @RequestParam(name = "endDate", required = true)String endDate
    ){
        String[] start = startDate.split(" ");

        String[] end = startDate.split(" ");
        if(start.length!=3 || end.length!=3)throw new IllegalStateException("Date should be in dd mm yyyy");
        int year1, month1, day1,year2,month2, day2;
        try {
            year1 = Integer.parseInt(start[2]);
            year2 = Integer.parseInt(end[2]);
            month1 = Integer.parseInt(start[1]);
            month2 = Integer.parseInt(end[1]);
            day1 = Integer.parseInt(start[0]);
            day2 = Integer.parseInt(end[0]);
        }
        catch(NumberFormatException e){
            throw new IllegalStateException("Date should be in dd mm yy where d,m,y are integers only");
        }
        usedVacationsService.addSingleRow(email,year1,month1,day1,year2,month2,day2);
        return "New data for Employee " + email + " loaded";
    }


    /**
     * Imports used vacation days for the employees from the provided file path and adds them to the system.
     * @param path the file path of the data to be imported
     * @return a string indicating that new data for employees has been loaded
     */
    @PostMapping(path="/usedVacations/import")
    @ResponseBody
    public String  importUsedVacationDays(
            @RequestParam(name="path", required = true)String path
    ){
        usedVacationsService.addUsedDaysPerYearPerEmployee(samplePath+path);
        return "New data for Employees loaded";
    }


}
