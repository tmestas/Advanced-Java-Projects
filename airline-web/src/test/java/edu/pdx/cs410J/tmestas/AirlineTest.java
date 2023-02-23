package edu.pdx.cs410J.tmestas;

import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

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
        assertThat(temp.toString(), equalTo("Flight 123 departs PDX at 11/22/22, 10:40 AM arrives BOI at 11/22/22, 10:40 AM"));
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
