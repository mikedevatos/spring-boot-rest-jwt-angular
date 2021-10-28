package com.hotels.example;

import com.hotels.example.Controllers.BookingControllerTest;
import com.hotels.example.Controllers.CustomerControllerTest;
import com.hotels.example.Controllers.EmployeeControllerTest;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

@SelectClasses({CustomerControllerTest.class, EmployeeControllerTest.class, BookingControllerTest.class})
@RunWith(JUnitPlatform.class)
public class DemoApplicationTests {


}
