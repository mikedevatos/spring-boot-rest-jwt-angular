package com.hotels.example.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="customer")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@NoArgsConstructor
public class Customer implements Serializable {

    @JsonView(Views.ResponseView.class)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @JsonView(Views.ResponseView.class)
    @Column(name="bill")
    @NotNull
    private float bill;


    @JsonView(Views.ResponseView.class)
    @JoinColumn(name="account_type")
    @Size(min=3,max = 64)
    private   String accountType;


    @JsonView(Views.ResponseView.class)
    @JsonIgnoreProperties("customer")
    @OneToMany( mappedBy = "customer"  ,fetch = FetchType.LAZY,cascade ={CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH,CascadeType.REMOVE } )
    Set<Person> persons  = new HashSet<>();


    @JsonIgnoreProperties("customer")
    @OneToOne(cascade = {CascadeType.ALL},fetch = FetchType.LAZY)
    @JsonView(Views.ResponseView.class)
    private Booking booking;


    @JsonView(Views.ResponseView.class)
    @JsonIgnoreProperties("customers")
    @ManyToOne(cascade = {CascadeType.MERGE,CascadeType.REFRESH},fetch = FetchType.LAZY)
    private Room room ;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public float getBill() {
        return bill;
    }


    public void setBill(float bill) {
        this.bill=bill;
    }


    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }


    @JsonIgnoreProperties("customers")
    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    @JsonIgnoreProperties("customer")
    public Set<Person> getPersons() {
        return persons;
    }

    public void setPersons(Set<Person> persons) {
        this.persons = persons;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", bill=" + bill +
                ", rooms=" + room +
                '}';
    }
}