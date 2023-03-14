/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.AdminService.entity;

import java.io.Serializable;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.annotation.Nonnull;

/**
 * This class represents the Vacations entity in the database, with its associated properties and relationships.
 */
@Entity
@Table(name = "vacations")
@NamedQueries({
        @NamedQuery(name = "Vacations.findAll", query = "SELECT v FROM Vacations v"),
        @NamedQuery(name = "Vacations.findByYear", query = "SELECT v FROM Vacations v WHERE v.vacationsPK.year = :year"),
        @NamedQuery(name = "Vacations.findByEmail", query = "SELECT v FROM Vacations v WHERE v.vacationsPK.email = :email"),
        @NamedQuery(name = "Vacations.findByTotalDays", query = "SELECT v FROM Vacations v WHERE v.totalDays = :totalDays"),
        @NamedQuery(name = "Vacations.findByUsedDays", query = "SELECT v FROM Vacations v WHERE v.usedDays = :usedDays")})
public class Vacations implements Serializable {


    private static final long serialVersionUID = 1L;

    /**
     * The primary key for the Vacations entity, consisting of a composite key of the year and email fields.
     */
    @EmbeddedId
    protected VacationsPK vacationsPK;

    /**
     * The total number of vacation days allocated to the employee for the year, represented as a long.
     */
    @Basic(optional = false)
    @Nonnull
    @Column(name = "totalDays")
    private long totalDays;

    /**
     * The number of vacation days that the employee has used so far in the year, represented as a long.
     */
    @Basic(optional = false)
    @Nonnull
    @Column(name = "usedDays")
    private long usedDays;

    /**
     * The Employee object associated with this Vacations object.
     */
    @JoinColumn(name = "email", referencedColumnName = "email", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Employee employee;


    /**
     * Default constructor for the Vacations class.
     */
    public Vacations() {
    }

    /**
     * Constructor for the Vacations class that takes a VacationsPK object as a parameter.
     * @param vacationsPK the primary key for this Vacations object
     */
    public Vacations(VacationsPK vacationsPK) {
        this.vacationsPK = vacationsPK;
    }


    /**
     * Constructor for the Vacations class that takes a VacationsPK object and the total and used vacation days as parameters.
     * @param vacationsPK the primary key for this Vacations object
     * @param totalDays the total number of vacation days allocated to the employee for the year
     * @param usedDays the number of vacation days that the employee has used so far in the year
     */
    public Vacations(VacationsPK vacationsPK, long totalDays, long usedDays) {
        this.vacationsPK = vacationsPK;
        this.totalDays = totalDays;
        this.usedDays = usedDays;
    }


    /**
     * Constructor for the Vacations class that takes a Employee email  and the year as parameters.
     * @param year year of the vacation
     * @param email email of the employee
     */
    public Vacations(long year, String email) {
        this.vacationsPK = new VacationsPK(year, email);
    }


    /**
     * Returns the vacations primary key.
     * @return the vacations primary key
     */
    public VacationsPK getVacationsPK() {
        return vacationsPK;
    }


    /**
     * Sets the vacations primary key.
     * @param vacationsPK the vacations primary key to set
     */
    public void setVacationsPK(VacationsPK vacationsPK) {
        this.vacationsPK = vacationsPK;
    }


    /**
     * Returns the total number of vacation days for this vacation object.
     * @return the total number of vacation days
     */
    public long getTotalDays() {
        return totalDays;
    }


    /**
     * Sets the total number of vacation days for this vacation object.
     * @param totalDays the total number of vacation days to set
     */
    public void setTotalDays(long totalDays) {
        this.totalDays = totalDays;
    }


    /**
     * Returns the number of vacation days used for this vacation object.
     * @return the number of vacation days used
     */
    public long getUsedDays() {
        return usedDays;
    }


    /**
     * Sets the number of vacation days used for this vacation object.
     * @param usedDays the number of vacation days used to set
     */
    public void setUsedDays(long usedDays) {
        this.usedDays = usedDays;
    }


    /**
     * Returns the employee associated with this vacation object.
     * @return the employee associated with this vacation object
     */
    public Employee getEmployee() {
        return employee;
    }


    /**
     * Sets the employee associated with this vacation object.
     * @param employee the employee to associate with this vacation object
     */
    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (vacationsPK != null ? vacationsPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Vacations)) {
            return false;
        }
        Vacations other = (Vacations) object;
        if ((this.vacationsPK == null && other.vacationsPK != null) || (this.vacationsPK != null && !this.vacationsPK.equals(other.vacationsPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Vacations[ vacationsPK=" + vacationsPK + " ]";
    }

}
