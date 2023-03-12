/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.AdminService.entities;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.*;
import jakarta.annotation.Nonnull;




/**
 * The UsedVacations class represents a table of used vacations in the company's database.
 * It contains information about the start date, end date, and the employee who used the vacation.
 */
@Entity
@Table(name = "used_vacations")
@NamedQueries({
        @NamedQuery(name = "UsedVacations.findAll", query = "SELECT u FROM UsedVacations u"),
        @NamedQuery(name = "UsedVacations.findByIdUsed", query = "SELECT u FROM UsedVacations u WHERE u.idUsed = :idUsed"),
        @NamedQuery(name = "UsedVacations.findByStartDate", query = "SELECT u FROM UsedVacations u WHERE u.startDate = :startDate"),
        @NamedQuery(name = "UsedVacations.findByEndDate", query = "SELECT u FROM UsedVacations u WHERE u.endDate = :endDate")})
public class UsedVacations implements Serializable {

    public static long id = 1;

    private static final long serialVersionUID = 1L;

    /**
     * The ID of the used vacation record.
     */
    @Id
    @GeneratedValue
    @Basic(optional = false)
    @Nonnull
    @Column(name = "id_used")
    private Long idUsed;

    /**
     * The start date of the used vacation.
     */
    @Basic(optional = false)
    @Nonnull
    @Column(name = "start_date")
    @Temporal(TemporalType.DATE)
    private Date startDate;

    /**
     * The end date of the used vacation.
     */
    @Basic(optional = false)
    @Nonnull
    @Column(name = "end_date")
    @Temporal(TemporalType.DATE)
    private Date endDate;

    /**
     * The employee who used the vacation.
     */
    @JoinColumn(name = "email", referencedColumnName = "email")
    @ManyToOne(optional = false)
    private Employee email;


    /**
     * Default constructor for the UsedVacations class.
     */
    public UsedVacations() {
    }


    /**
     * Constructor for the UsedVacations class.
     * @param idUsed The ID of the used vacation record.
     */
    public UsedVacations(Long idUsed) {
        this.idUsed = idUsed;
    }


    /**
     * Constructor for the UsedVacations class.
     * @param idUsed The ID of the used vacation record.
     * @param startDate The start date of the used vacation.
     * @param endDate The end date of the used vacation.
     */
    public UsedVacations(Long idUsed, Date startDate, Date endDate) {
        this.idUsed = idUsed;
        this.startDate = startDate;
        this.endDate = endDate;
    }


    /**
     * Gets the ID of the used vacation record.
     * @return The ID of the used vacation record.
     */
    public Long getIdUsed() {
        return idUsed;
    }


    /**
     * Sets the ID of the used vacation record.
     * @param idUsed The ID of the used vacation record.
     */
    public void setIdUsed(Long idUsed) {
        this.idUsed = idUsed;
    }


    /**
     * Gets the start date of the used vacation.
     * @return The start date of the used vacation.
     */
    public Date getStartDate() {
        return startDate;
    }


    /**
     * Sets the start date of the used vacation.
     * @param startDate The start date of the used vacation.
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }


    /**
     * Gets the end date of the used vacation.
     * @return The end date of the used vacation.
     */
    public Date getEndDate() {
        return endDate;
    }


    /**
     * Sets the end date of the used vacation.
     * @param endDate The end date of the used vacation.
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }


    /**
     * Gets the email of the used vacation.
     * @return The email of the used vacation.
     */
    public Employee getEmail() {
        return email;
    }


    /**
     * Sets the email of the used vacation.
     * @param email the email fo the employee
     */
    public void setEmail(Employee email) {
        this.email = email;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idUsed != null ? idUsed.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof UsedVacations)) {
            return false;
        }
        UsedVacations other = (UsedVacations) object;
        if ((this.idUsed == null && other.idUsed != null) || (this.idUsed != null && !this.idUsed.equals(other.idUsed))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.UsedVacations[ idUsed=" + idUsed + " ]";
    }

}
