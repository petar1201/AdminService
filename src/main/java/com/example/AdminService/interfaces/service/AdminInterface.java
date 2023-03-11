package com.example.AdminService.interfaces.service;
import com.example.AdminService.entities.Admin;

public interface AdminInterface {

    public void addAdmin(String username, String Password);

    public void removeAdmin(String username);

}
