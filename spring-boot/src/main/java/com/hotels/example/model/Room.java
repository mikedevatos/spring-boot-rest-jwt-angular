package com.hotels.example.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


import javax.persistence.*;
import javax.validation.constraints.Min;
import java.io.Serializable;
import java.util.*;

@Entity
@Table(name="room")
@NoArgsConstructor
//@Setter
public class Room implements Serializable {


    public Room(Integer id) {
        this.id = id;
    }

    @JsonView(Views.EntityOnly.class)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @Override
    public String toString() {
        return "Room{" +
                "id=" + id +
                ", price=" + price +
                ", roomCapacity=" + roomCapacity +
                '}';
    }

    @JsonView(Views.EntityOnly.class)
    @Column(name="price")
    @Min(1)
    private float price;


    @JsonView(Views.EntityOnly.class)
    @Column(name="room_capacity")
    @Min(1)
    private int roomCapacity;

    @JsonView(Views.ResponseView.class)
    @JsonIgnoreProperties("room")
    @OneToMany(mappedBy="room",fetch = FetchType.LAZY,cascade = {CascadeType.MERGE,CascadeType.REFRESH})
    Set<Customer> customers;

    public void setCustomers(Set<Customer> customers) {
        this.customers = customers;
    }

    @JsonIgnoreProperties("room")
    public Set<Customer> getCustomers() {
        return customers;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setPrice(float price) {
        this.price = price;
    }


    public float getPrice() {
        return price;
    }

    public int getRoomCapacity() {
        return roomCapacity;
    }

    public void setRoomCapacity(int roomCapacity) {
        this.roomCapacity = roomCapacity;
    }

}
