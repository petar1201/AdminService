package com.example.AdminService.interfaces.service;
import com.example.AdminService.entities.Admin;

/**
 * The AdminInterface provides methods for adding and removing administrators.
 * @author petar
 */
public interface AdminInterface {
    /**
     * Adds a new administrator to the system.
     */
    public void addAdmin();

    /**
     * Removes an administrator with the given username from the system.
     *
     * @param username the username of the administrator to remove
     */
    public void removeAdmin(String username);

}
