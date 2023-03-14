/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.AdminService.entity;

import java.io.Serializable;
import java.util.List;
import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.annotation.Nonnull;

/**
 * The Employee class represents an employee entity in the system.
 * Each employee has an email, password, and an active status.
 * It also has a list of used vacations and vacations.
 */
@Entity
@Table(name = "employee")
@NamedQueries({
        @NamedQuery(name = "Employee.findAll", query = "SELECT e FROM Employee e"),
        @NamedQuery(name = "Employee.findByEmail", query = "SELECT e FROM Employee e WHERE e.email = :email"),
        @NamedQuery(name = "Employee.findByPassword", query = "SELECT e FROM Employee e WHERE e.password = :password")})
public class Employee implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation

    /**
     * The email address of the employee, which is the unique identifier for each employee.
     */
    @Id
    @Basic(optional = false)
    @Nonnull
  //  @Size(min = 1, max = 40)
    @Column(name = "email")
    private String email;

    /**
     * The password of the employee, which is used for authentication.
     */
    @Basic(optional = false)
    @Nonnull
   // @Size(min = 1, max = 40)
    @Column(name = "password")
    private String password;

    /**
     * The active status of the employee.
     */
    @Basic(optional = false)
    @Nonnull
    @Column(name = "active")
    private boolean active;

    /**
     * The list of used vacations for the employee.
     */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "email")
    private List<UsedVacations> usedVacationsList;

    /**
     * The list of vacations for the employee.
     */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "employee")
    private List<Vacations> vacationsList;

    /**
     * Constructs a new Employee object with no parameters.
     */
    public Employee() {
    }

    /**
     * Constructs a new Employee object with the given email address.
     * @param email The email address of the employee.
     */
    public Employee(String email) {
        this.email = email;
    }

    /**
     * Constructs a new Employee object with the given email address and password.
     * @param email The email address of the employee.
     * @param password The password of the employee.
     */
    public Employee(String email, String password) {
        this.email = email;
        this.password = password;
    }

    /**
     * Returns the active status of the employee.
     * @return The active status of the employee.
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Sets the active status of the employee.
     * @param active The active status of the employee.
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * Returns the email address of the employee.
     * @return The email address of the employee.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email address of the employee.
     * @param email The email address of the employee.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Returns the password of the employee.
     * @return The password of the employee.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password of the employee.
     * @param password The password of the employee.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Returns the list of used vacations for the employee.
     * @return The list of used vacations for the employee.
     */
    public List<UsedVacations> getUsedVacationsList() {
        return usedVacationsList;
    }

    /**
     * Sets the list of used vacations for the employee.
     * @param usedVacationsList list of used vacations for the employee.
     */
    public void setUsedVacationsList(List<UsedVacations> usedVacationsList) {
        this.usedVacationsList = usedVacationsList;
    }

    /**
     * Returns the list of vacations for the employee.
     * @return The list of vacations for the employee.
     */
    public List<Vacations> getVacationsList() {
        return vacationsList;
    }

    /**
     * Sets the list of vacations for the employee.
     * @param vacationsList The list of vacations for the employee.
     */
    public void setVacationsList(List<Vacations> vacationsList) {
        this.vacationsList = vacationsList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (email != null ? email.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Employee)) {
            return false;
        }
        Employee other = (Employee) object;
        if ((this.email == null && other.email != null) || (this.email != null && !this.email.equals(other.email))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Employee[ email=" + email + " ]";
    }

}
