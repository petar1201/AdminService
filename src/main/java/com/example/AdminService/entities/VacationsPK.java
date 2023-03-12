/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.AdminService.entities;

import java.io.Serializable;

import jakarta.annotation.Nonnull;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Table;

/**
 * The VacationsPK class is an Embeddable object that represents the primary key for the Vacations entity.
 * It consists of the year and email attributes.
 */
@Embeddable
public class VacationsPK implements Serializable {

    /**
     * The year attribute represents the year of the vacation.
     */
    @Basic(optional = false)
    @Nonnull
    @Column(name = "year")
    private long year;


    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation

    /**
     * The email attribute represents the email address of the employee requesting the vacation.
     */
    @Basic(optional = false)
    @Nonnull
    //@Size(min = 1, max = 40)
    @Column(name = "email")
    private String email;

    /**
     * Default constructor for VacationsPK class.
     */
    public VacationsPK() {
    }

    /**
     * Constructor for VacationsPK class.
     * @param year The year of the vacation.
     * @param email The email address of the employee requesting the vacation.
     */
    public VacationsPK(long year, String email) {
        this.year = year;
        this.email = email;
    }

    /**
     * Returns value of year attribute
     * @return year attribute
     * */
    public long getYear() {
        return year;
    }

    /**
     * Sets value of year attribute
     * @param year value to be assigned
     * */
    public void setYear(long year) {
        this.year = year;
    }

    /**
     * Returns email
     * @return email attribute
     * */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the argument as an email attribute
     * @param email email attribute to be assigned
     * */
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) year;
        hash += (email != null ? email.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof VacationsPK)) {
            return false;
        }
        VacationsPK other = (VacationsPK) object;
        if (this.year != other.year) {
            return false;
        }
        if ((this.email == null && other.email != null) || (this.email != null && !this.email.equals(other.email))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.VacationsPK[ year=" + year + ", email=" + email + " ]";
    }

}
