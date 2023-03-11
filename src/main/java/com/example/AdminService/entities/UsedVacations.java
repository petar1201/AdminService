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
 *
 * @author petar
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
    @Id
    @GeneratedValue
    @Basic(optional = false)
    @Nonnull
    @Column(name = "id_used")
    private Long idUsed;
    @Basic(optional = false)
    @Nonnull
    @Column(name = "start_date")
    @Temporal(TemporalType.DATE)
    private Date startDate;
    @Basic(optional = false)
    @Nonnull
    @Column(name = "end_date")
    @Temporal(TemporalType.DATE)
    private Date endDate;
    @JoinColumn(name = "email", referencedColumnName = "email")
    @ManyToOne(optional = false)
    private Employee email;

    public UsedVacations() {
    }

    public UsedVacations(Long idUsed) {
        this.idUsed = idUsed;
    }

    public UsedVacations(Long idUsed, Date startDate, Date endDate) {
        this.idUsed = idUsed;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Long getIdUsed() {
        return idUsed;
    }

    public void setIdUsed(Long idUsed) {
        this.idUsed = idUsed;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Employee getEmail() {
        return email;
    }

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
        // TODO: Warning - this method won't work in the case the id fields are not set
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
