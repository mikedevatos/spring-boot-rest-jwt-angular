package com.hotels.example.model;



import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.util.*;



@Entity
@Table(name="customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="first_name")
    @Size(min=4,max = 64)
    private String firstName;

    @Column(name="last_name")
    @Size(min=4,max = 64)
    private String lastName;

    @Column(name="email",unique = true)
    @Size(min=4,max = 64)
    @NotNull

    private String email;


    @Column(name="bill" ,nullable=false)
    @Min(1)
    private float bill;





    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public float getBill() {
        return bill;
    }

    public void setBill(float bill) {
        this.bill = bill;
    }


    @JsonIgnoreProperties("customers")

    @ManyToMany(cascade = {CascadeType.MERGE,CascadeType.REFRESH},fetch = FetchType.LAZY)
   @JoinTable(
            name="customer_room",
            joinColumns={@JoinColumn(name="customer_id",referencedColumnName = "id")},
            inverseJoinColumns={@JoinColumn(name="room_id",referencedColumnName = "id")}
    )

    private Set<Room> rooms = new HashSet<>();


    @JsonIgnoreProperties("customers")
    public Set<Room> getRooms() {
        return rooms;
    }

    @JsonIgnoreProperties("customers")

    public void setRooms(Set<Room> rooms) {
        this.rooms = rooms;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", bill=" + bill +
                ", rooms=" + rooms +
                '}';
    }
}