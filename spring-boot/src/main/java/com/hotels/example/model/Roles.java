package com.hotels.example.model;


import javax.persistence.*;
import java.io.Serializable;

@Entity
//@Setter
public class Roles implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

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


}
