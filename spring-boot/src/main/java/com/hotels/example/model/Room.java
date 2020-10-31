package com.hotels.example.model;


import com.fasterxml.jackson.annotation.JsonIgnore;


import javax.persistence.*;
import javax.validation.constraints.Min;
import java.util.*;



@Entity
@Table(name="room")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Override
    public String toString() {
        return "Room{" +
                "id=" + id +
                ", price=" + price +
               /// ", customers=" + customers +
                '}';
    }

    @Column(name="price")
    @Min(1)
    private float price;

    @JsonIgnore

    public Set<Customer> getCustomers() {
        return customers;
    }

    @JsonIgnore

    public void setCustomers(Set<Customer> customers) {
        this.customers = customers;
    }

    @JsonIgnore
    @ManyToMany(mappedBy = "rooms",fetch = FetchType.LAZY,cascade = {CascadeType.MERGE,CascadeType.REFRESH})
    Set<Customer> customers;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setPrice(float price) {
        this.price = price;
    }


    public float getPrice() {
        return price;
    }

}
