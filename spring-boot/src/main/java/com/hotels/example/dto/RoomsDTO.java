package com.hotels.example.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.hotels.example.model.Room;
import com.hotels.example.model.Views;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@JsonView(Views.EntityOnly.class)
@Getter
@Setter
public class RoomsDTO  implements Serializable {

    List<Room> rooms = new ArrayList<>();

    long count ;

}
