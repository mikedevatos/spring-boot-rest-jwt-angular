package com.hotels.example.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Roles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @ManyToMany(fetch=FetchType.EAGER,cascade={CascadeType.MERGE,CascadeType.REFRESH})
    @JoinTable(
            name="user_roles",
            joinColumns={@JoinColumn(name="roles_id",referencedColumnName = "id")},
            inverseJoinColumns={@JoinColumn(name="user_id",referencedColumnName = "id")}
    )
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
        this.type = type;
    }


    @JsonIgnoreProperties("roles")
    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

}
