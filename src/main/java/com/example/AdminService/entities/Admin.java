/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.AdminService.entities;

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
 *
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
    @Id
    @Basic(optional = false)
    @Nonnull
    //@Size(min = 1, max = 40)
    @Column(name = "username")
    private String username;
    @Basic(optional = false)
    @Nonnull
   // @Size(min = 1, max = 40)
    @Column(name = "password")
    private String password;

    public Admin() {
    }

    public Admin(String username) {
        this.username = username;
    }

    public Admin(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

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
        // TODO: Warning - this method won't work in the case the id fields are not set
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
