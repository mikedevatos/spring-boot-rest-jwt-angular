package com.hotels.example.Controllers;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hotels.example.TestUtil;
import com.hotels.example.errors.RoomIsBookedException;
import com.hotels.example.model.Booking;
import com.hotels.example.service.RoomServiceImpl;
import lombok.SneakyThrows;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.io.File;
import java.util.List;


import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class BookingControllerTest {

    @Before
    public void before() {
    }

    @After
    public void after()  {
    }


    @Autowired
    private MockMvc mockMvc;


    private MockMvc getMockMvc() {
        return mockMvc;
    }


    @Test
    @SneakyThrows
    public void updateBookingWithBookedDatesAndExpectBadRequest(){

        String token = TestUtil.getToken("user1", "pass1", getMockMvc());
        final ObjectMapper objectMapper = new ObjectMapper();

        List<Booking> bookings =
                objectMapper.readValue(new File(new ClassPathResource("/src/test/resources/booking_files/booked_bookings.json").getPath() ),
                        new TypeReference<List<Booking>>(){} );

        bookings.forEach(x -> System.out.println(x.toString() ) );

        for(Booking  b  : bookings) {
            mockMvc.perform(put("/api/booking")
                            .contentType(MediaType.APPLICATION_JSON)
                            .header("Authorization", "Bearer " + token)
                            .content(asJsonString(b))
                    )
                    .andExpect(status().isBadRequest() )
                    .andExpect(result -> assertTrue(result.getResolvedException() instanceof RoomIsBookedException));
        }

    }


    @Test
    @SneakyThrows
    public void updateBookingWithNoBookedDatesAndExpectAccepted(){

        String token = TestUtil.getToken("user1", "pass1", getMockMvc());

        final ObjectMapper objectMapper = new ObjectMapper();

        List<Booking> bookings =
                objectMapper.readValue(new File(new ClassPathResource("/src/test/resources/booking_files/non_booked_bookings.json").getPath() ),
                                       new TypeReference<List<Booking>>(){} );

        bookings.forEach(x -> System.out.println(x.toString() ) );

        for(Booking  b  : bookings) {
            mockMvc.perform(put("/api/booking")
                            .contentType(MediaType.APPLICATION_JSON)
                            .header("Authorization", "Bearer " + token)
                            .content(asJsonString(b))
                    )
                    .andExpect(status().isAccepted());
        }
    }




    static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
