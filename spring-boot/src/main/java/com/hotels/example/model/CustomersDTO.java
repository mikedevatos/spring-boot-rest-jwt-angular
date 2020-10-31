package com.hotels.example.model;

import java.util.ArrayList;
import java.util.List;

public class CustomersDTO {
    List<Customer> customer = new ArrayList<>();
    long count ;

    public List<Customer> getCustomer() {
        return customer;
    }

    public void setCustomer(List<Customer> customer) {
        this.customer = customer;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }
}
