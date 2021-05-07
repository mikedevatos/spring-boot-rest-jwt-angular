package com.hotels.example.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.hotels.example.model.Customer;
import com.hotels.example.model.Views;
import lombok.Setter;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Setter
@JsonView(Views.ResponseView.class)
public class CustomersDTO implements Serializable {

    List<Customer> customer = new ArrayList<>();
    long count ;

    @JsonView(Views.ResponseView.class)
    public List<Customer> getCustomer() {
        return customer;
    }

    public long getCount() {
        return count;
    }

}
