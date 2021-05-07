package com.hotels.example.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Table(name="person")
@NoArgsConstructor
public class Person implements Serializable {


    @JsonView(Views.ResponseView.class)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @JsonView(Views.ResponseView.class)
    @Column(name="first_name")
    @Size(min=4,max = 64)
    private String firstName;


    @JsonView(Views.ResponseView.class)
    @Column(name="last_name")
    @Size(min=4,max = 64)
    private String lastName;

    @JsonView(Views.ResponseView.class)
    @Column(name="email",unique = true)
    @Size(min=4,max = 128)
    @NotNull
    private String email;

    @JsonView(Views.ResponseView.class)
    @JsonIgnoreProperties("persons")
    @ManyToOne( cascade ={CascadeType.MERGE,CascadeType.REFRESH,CascadeType.PERSIST },fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    @JsonIgnoreProperties("persons")
    public Customer getCustomer() {
        return customer;
    }


    public String getFirstName() {
        return firstName;
    }


    public String getLastName() {
        return lastName;
    }


    public String getEmail() {
        return email;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

}
