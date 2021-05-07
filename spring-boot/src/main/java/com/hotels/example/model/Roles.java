package com.hotels.example.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
//@Setter
public class Roles implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @OneToMany(    mappedBy = "role", fetch=FetchType.EAGER,cascade={CascadeType.MERGE,CascadeType.REFRESH})
    private Set<User> users;

    @Column(name="type_role")
    private String type;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getType_role() {
        return type;
    }

    public void setType_role(String typeTole) {
        this.type = typeTole;
    }


    @JsonIgnoreProperties("roles")
    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

}
