package edu.pdx.cs410J.tmestas;

import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.LinkedList;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Unit tests for the {@link Airline} class.
 */
public class AirlineTest {
    @Test
    void testGetName(){
        Airline airline = new Airline("Alaska Airlines");
        assertThat(airline.getName(), equalTo("Alaska Airlines"));
    }

    @Test
    void testAddFlight(){
        Airline airline = new Airline("Alaska Airlines");
        Flight flight = new Flight(123, "PDX", "11/22/2022 10:40 AM", "BOI", "11/22/2022 10:40 AM");
        airline.addFlight(flight);
        Flight temp = new Flight();
        for(Flight f: airline.getFlights()){
            temp = f;
        }
        assertThat(temp.toString(), equalTo("Flight 123 departs PDX at Tue Nov 22 10:40:00 PST 2022 arrives BOI at Tue Nov 22 10:40:00 PST 2022"));
    }

    @Test
    void testGetFlights(){
        Airline airline = new Airline("Alaska Airlines");
        Flight flight = new Flight(123, "PDX", "11/22/2022 10:40 AM", "BOI", "11/22/2022 10:40 AM");
        airline.addFlight(flight);
        Flight temp = new Flight();
        for(Flight f: airline.getFlights()){
            temp = f;
        }
        assertThat(temp, equalTo(flight));
    }


}
