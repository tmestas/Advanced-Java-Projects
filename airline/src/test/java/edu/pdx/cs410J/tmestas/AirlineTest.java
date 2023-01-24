package edu.pdx.cs410J.tmestas;

import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;

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
        Flight flight = new Flight(123, "PDX", "10:40", "11/22/2022", "BOI", "10:45", "11/22/2022");
        airline.addFlight(flight);
        assertThat(airline.getFlight().toString(), equalTo("Flight 123 departs PDX at 10:40 11/22/2022 arrives BOI at 10:45 11/22/2022"));
    }

    @Test
    void testGetFlight(){
        Airline airline = new Airline("Alaska Airlines");
        Flight flight = new Flight(123, "PDX", "10:40", "11/22/2022", "BOI", "10:45", "11/22/2022");
        airline.addFlight(flight);
        assertThat(airline.getFlight(), equalTo(flight));
    }

    @Test
    void testGetFlights(){
        Airline airline = new Airline("Alaska Airlines");
        assertThrows(UnsupportedOperationException.class, airline::getFlights);
    }


}
