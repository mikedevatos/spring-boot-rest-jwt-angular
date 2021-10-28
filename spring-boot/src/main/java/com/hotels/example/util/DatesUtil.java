package com.hotels.example.util;


import lombok.extern.slf4j.Slf4j;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class DatesUtil {

    public static List<LocalDate> getDates(LocalDate startDate, LocalDate endDate){

        List<LocalDate> dates = new ArrayList<>();

        while (!startDate.isAfter(endDate))
        {
            dates.add( startDate  );
            startDate = startDate.plusDays(1);
        }
        return dates;
    }


    public static boolean isDateBooked(LocalDate date, LocalDate startDate, LocalDate endDate ){
        if (  (date.isBefore(endDate) || date.equals(endDate) )
             && (date.isAfter(startDate) || date.equals(startDate) ) ) {

            log.debug("date  "+ date.toString() + " is booked " );
            return true;
        }
       return false;
    }


}
