package com.hotels.example.dto;

import com.hotels.example.model.Room;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.Min;
import java.io.Serializable;
import java.util.Optional;
@NoArgsConstructor
public class CustomerUpdateDTO implements Serializable {


    private Integer Id;

    private  Optional<@Min(0) Float>  bill = Optional.empty();

    private  Optional<Room>   room =Optional.empty();

    private Optional<@Length(min=2,max = 64,message = "account type must be between 2 and 64 ")   String> accountType =Optional.empty();

    public Optional<Room> getRoom() {
        return room;
    }

    public void setRoom(Optional<Room> room) {
        this.room = room;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public Optional<Float> getBill() {
        return bill;
    }

    public void setBill(Optional<Float> bill) {
        this.bill = bill;
    }

    public Optional<String> getAccountType() {
        return accountType;
    }

    public void setAccountType(Optional<String> accountType) {
        this.accountType = accountType;
    }

}
