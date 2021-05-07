package com.hotels.example.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Date;

@Entity
@Table(name="booking")
@NoArgsConstructor
@Setter
public class Booking implements Serializable {

    @Override
    public String toString() {
        return "Booking{" +
                "id=" + id +
                ", startBooking=" + startBooking +
                ", endBooking=" + endBooking +
                '}';
    }


    @JsonView(Views.ResponseView.class)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JsonView(Views.ResponseView.class)
    @Column(name="start_booking")
    @NotNull
    private Date startBooking;

    @JsonView(Views.ResponseView.class)
    @Column(name="end_booking")
    @NotNull
    private Date endBooking;


    @JsonIgnoreProperties("booking")
    @OneToOne(mappedBy="booking",fetch = FetchType.LAZY,cascade = {CascadeType.ALL})
    Customer customer;


    public Integer getId() {
        return id;
    }

    public Date getStartBooking() {
        return startBooking;
    }


    public Date getEndBooking() {
        return endBooking;
    }

    @JsonIgnoreProperties("booking")
    public Customer getCustomer() {
        return customer;
    }





}
