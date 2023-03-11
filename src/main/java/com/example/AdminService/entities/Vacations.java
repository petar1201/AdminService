/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.AdminService.entities;

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
 *
 * @author petar
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
    @EmbeddedId
    protected VacationsPK vacationsPK;
    @Basic(optional = false)
    @Nonnull
    @Column(name = "totalDays")
    private long totalDays;
    @Basic(optional = false)
    @Nonnull
    @Column(name = "usedDays")
    private long usedDays;
    @JoinColumn(name = "email", referencedColumnName = "email", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Employee employee;

    public Vacations() {
    }

    public Vacations(VacationsPK vacationsPK) {
        this.vacationsPK = vacationsPK;
    }

    public Vacations(VacationsPK vacationsPK, long totalDays, long usedDays) {
        this.vacationsPK = vacationsPK;
        this.totalDays = totalDays;
        this.usedDays = usedDays;
    }

    public Vacations(long year, String email) {
        this.vacationsPK = new VacationsPK(year, email);
    }

    public VacationsPK getVacationsPK() {
        return vacationsPK;
    }

    public void setVacationsPK(VacationsPK vacationsPK) {
        this.vacationsPK = vacationsPK;
    }

    public long getTotalDays() {
        return totalDays;
    }

    public void setTotalDays(long totalDays) {
        this.totalDays = totalDays;
    }

    public long getUsedDays() {
        return usedDays;
    }

    public void setUsedDays(long usedDays) {
        this.usedDays = usedDays;
    }

    public Employee getEmployee() {
        return employee;
    }

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
        // TODO: Warning - this method won't work in the case the id fields are not set
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
