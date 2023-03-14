/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.AdminService.entity;

import java.io.Serializable;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.annotation.Nonnull;

//import jakarta.validation.constraints.Size;

/**
 * The Admin class represents an administrator user in the system.
 * It contains information about the admin's username, password, and active status.
 * @author petar
 */
@Entity
@Table(name = "admin")
@NamedQueries({
@NamedQuery(name = "Admin.findAll", query = "SELECT a FROM Admin a"),
@NamedQuery(name = "Admin.findByUsername", query = "SELECT a FROM Admin a WHERE a.username = :username"),
@NamedQuery(name = "Admin.findByPassword", query = "SELECT a FROM Admin a WHERE a.password = :password")})
public class Admin implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * The username of the admin.
     */
    @Id
    @Basic(optional = false)
    @Nonnull
    //@Size(min = 1, max = 40)
    @Column(name = "username")
    private String username;

    /**
     * The password of the admin.
     */
    @Basic(optional = false)
    @Nonnull
   // @Size(min = 1, max = 40)
    @Column(name = "password")
    private String password;

    /**
     * The active status of the admin.
     */
    @Basic(optional = false)
    @Nonnull
    @Column(name = "active")
    private boolean active;

    /**
     * Creates an empty Admin object.
     */
    public Admin() {
    }

    /**
     * Creates an Admin object with the specified username.
     * @param username the username of the admin
     */
    public Admin(String username) {
        this.username = username;
    }

    /**
     * Creates an Admin object with the specified username and password.
     * @param username the username of the admin
     * @param password the password of the admin
     */
    public Admin(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Returns the active status of the admin.
     * @return the active status of the admin
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Sets the active status of the admin.
     * @param active the active status of the admin
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * Returns the username of the admin.
     * @return the username of the admin
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username of the admin.
     * @param username the username of the admin
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Returns the password of the admin.
     * @return the password of the admin
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password of the admin.
     * @param password the password of the admin
     */
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (username != null ? username.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Admin)) {
            return false;
        }
        Admin other = (Admin) object;
        if ((this.username == null && other.username != null) || (this.username != null && !this.username.equals(other.username))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Admin[ username=" + username + " ]";
    }

}
