package com.example.AdminService.controllers;


import com.example.AdminService.services.AdminService;
import com.example.AdminService.services.EmployeeService;
import com.example.AdminService.services.UsedVacationsService;
import com.example.AdminService.services.VacationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="api/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private VacationsService vacationsService;

    @Autowired
    private UsedVacationsService usedVacationsService;


    @PostMapping(path="new")
    @ResponseBody
    public String createNewAdmin(){
        adminService.addAdmin();
        return "New Admin created";
    }

    @DeleteMapping(path="delete")
    @ResponseBody
    public String deleteAdminSelf(){
        adminService.removeAdmin("admin");
        return "Admin deleted";
    }

    @PostMapping(path="employee/new")
    @ResponseBody
    public String addOneEmployee(@RequestParam(name = "email", required = true) String email,
                                 @RequestParam(name="password", required = true) String password){
        employeeService.addSingleEmployee(email, password);
        return "Employee " + email + " created";
    }

    @PostMapping(path="employee/import")
    @ResponseBody
    public String importEmployeesFromCsv(@RequestParam(name="path", required = true) String path){
        employeeService.addEmployeeProfiles(path);
        return "Employees loaded";
    }

    @DeleteMapping(path="employee/delete")
    @ResponseBody
    public String deleteEmployee(@RequestParam(name="email", required = true) String email){
        employeeService.removeEmployee(email);
        return "Employee " + email + " removed";
    }

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

    @PostMapping(path="/vacations/import")
    @ResponseBody
    public String  importVacationDaysForYearForEmployee(
            @RequestParam(name="path", required = true)String path
    ){
        vacationsService.addDaysPerYearPerEmployee(path);
        return "New data for Employees loaded";
    }

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

    @PostMapping(path="/usedVacations/import")
    @ResponseBody
    public String  importUsedVacationDays(
            @RequestParam(name="path", required = true)String path
    ){
        usedVacationsService.addUsedDaysPerYearPerEmployee(path);
        return "New data for Employees loaded";
    }


}
