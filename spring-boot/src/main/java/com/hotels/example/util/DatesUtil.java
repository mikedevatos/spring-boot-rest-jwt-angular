package com.hotels.example.util;

import com.hotels.example.model.Booking;
import com.hotels.example.model.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class DatesUtil {
     static Logger  log = LoggerFactory.getLogger(DatesUtil.class);

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
