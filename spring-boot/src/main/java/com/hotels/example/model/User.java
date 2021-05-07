package com.hotels.example.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Entity
@Table(name="users")
//@Setter
public class User implements UserDetails {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;


    @JsonIgnore
    @ManyToOne(cascade ={CascadeType.MERGE,CascadeType.REFRESH},fetch = FetchType.EAGER)
    @JoinColumn(name = "roles_id")
    private Roles role;

    @Column(name="username", unique = true  ,nullable = false)
    @Size(min=4,max = 64)
    private String username;

    @Column(name="pass",nullable = false)
    @Size(min=4,max = 64)
    private String password;

    @Column(name="first_name")
    @Size(min=4,max = 64)
    private String firstName;

    @Column(name="last_name")
    @Size(min=4,max = 64)
    private String lastName;

    @JsonIgnoreProperties("users")
    public Roles getRole() {
        return role;
    }



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }




    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public void setRoles(Roles role) {
        this.role = role;
    }



    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();

            GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_"+ this.getRole().getType_role());
            authorities.add(authority);

        return authorities;
    }

    @Override

    public String getPassword() {
        return password;
    }

    @Override

    public String getUsername() {
        return username;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    @Transient

    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    @Transient

    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    @Transient
    public boolean isEnabled() {
        this.setActive();
        return this.getActive() == 1;
    }



    @JsonIgnore
    @Transient
    private int active;

    @JsonIgnore
    @Transient
    public int getActive() {
        return active;
    }

    @JsonIgnore
    @Transient
    public void setActive() {
        this.active = 1;
    }




}
