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
 *
 * @author petar
 */
@Embeddable
public class VacationsPK implements Serializable {

    @Basic(optional = false)
    @Nonnull
    @Column(name = "year")
    private long year;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Basic(optional = false)
    @Nonnull
    //@Size(min = 1, max = 40)
    @Column(name = "email")
    private String email;

    public VacationsPK() {
    }

    public VacationsPK(long year, String email) {
        this.year = year;
        this.email = email;
    }

    public long getYear() {
        return year;
    }

    public void setYear(long year) {
        this.year = year;
    }

    public String getEmail() {
        return email;
    }

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
        // TODO: Warning - this method won't work in the case the id fields are not set
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
