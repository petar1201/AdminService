/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.AdminService.entities;

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
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.annotation.Nonnull;
/**
 *
 * @author petar
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
    @Id
    @Basic(optional = false)
    @Nonnull
  //  @Size(min = 1, max = 40)
    @Column(name = "email")
    private String email;
    @Basic(optional = false)
    @Nonnull
   // @Size(min = 1, max = 40)
    @Column(name = "password")
    private String password;

    @Basic(optional = false)
    @Nonnull
    @Column(name = "active")
    private boolean active;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "email")
    private List<UsedVacations> usedVacationsList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "employee")
    private List<Vacations> vacationsList;

    public Employee() {
    }

    public Employee(String email) {
        this.email = email;
    }

    public Employee(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<UsedVacations> getUsedVacationsList() {
        return usedVacationsList;
    }

    public void setUsedVacationsList(List<UsedVacations> usedVacationsList) {
        this.usedVacationsList = usedVacationsList;
    }

    public List<Vacations> getVacationsList() {
        return vacationsList;
    }

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
        // TODO: Warning - this method won't work in the case the id fields are not set
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
