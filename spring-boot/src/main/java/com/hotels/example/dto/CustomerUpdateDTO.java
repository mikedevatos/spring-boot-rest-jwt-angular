package com.hotels.example.dto;

import com.hotels.example.model.Room;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.Min;
import java.io.Serializable;
import java.util.Optional;

@NoArgsConstructor
public class CustomerUpdateDTO implements Serializable {


    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    private  Optional<@Min(0) Float>  bill = Optional.empty();

    private  Optional<Integer>   roomId =Optional.empty();

    private Optional<@Length(min=2,max = 64,message = "account type must be between 2 and 64 ")   String> accountType =Optional.empty();




    public Optional<Integer> getRoomId() {
        return roomId;
    }

    public void setRoom(Optional<Room> room) {
        this.roomId = roomId;
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
