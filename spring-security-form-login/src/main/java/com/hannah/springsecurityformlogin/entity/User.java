/*
 * Copyright (c) Rakuten, Inc. All Rights Reserved.
 *
 * This program is the information assets which are handled as "Strictly Confidential".
 * Permission of Use is only admitted in Rakuten Inc Development Department.
 * If you don't have permission,
 * MUST not be published, broadcast, rewritten for broadcast or publication or redistributed
 * directly or indirectly in any medium.
 *
 */
package com.hannah.springsecurityformlogin.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.List;

/**
 * @author yihan.a.chen
 */
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String password;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(
        name = "user_role",
        joinColumns = {@JoinColumn(name = "user_id",
            referencedColumnName = "id")},
        inverseJoinColumns = {@JoinColumn(name = "role_id",
            referencedColumnName = "id")})
    private List<Role> roles;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public List<Role> getRoles() {
        return roles;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}
