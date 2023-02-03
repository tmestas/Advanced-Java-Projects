package edu.pdx.cs410J.tmestas;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Unit tests for the {@link Flight} class.
 *
 * You'll need to update these unit tests as you build out you program.
 */
public class FlightTest {

  @Test
  void getDepartureStringNeedsToBeImplemented(){
    Flight flight = new Flight(123, "PDX", "11/22/2022 10:40 AM", "BOI", "11/22/2022 10:40 AM");
    assertThat(flight.getDepartureString(), equalTo("11/22/22, 10:40 AM"));
  }
  @Test
  void getArrivalStringNeedsToBeImplemented() {
    Flight flight = new Flight(123, "PDX", "11/22/2022 10:40 AM", "BOI", "11/22/2022 10:40 AM");
    assertThat(flight.getArrivalString(), equalTo("11/22/22, 10:40 AM"));
  }
  @Test
  void testGetNumber() {
    Flight flight = new Flight(123, "PDX", "11/22/2022 10:40 AM", "BOI", "11/22/2022 10:40 AM");
    assertThat(flight.getNumber(), equalTo(123));
  }

  @Test
  void testGetDestination(){
    Flight flight = new Flight(123, "PDX", "11/22/2022 10:40 AM", "BOI", "11/22/2022 10:40 AM");
    assertThat(flight.getDestination(), equalTo("BOI"));
  }
  @Test
  void testGetSource(){
    Flight flight = new Flight(123, "PDX", "11/22/2022 10:40 AM", "BOI", "11/22/2022 10:40 AM");
    assertThat(flight.getSource(), equalTo("PDX"));
  }
  @Test
  void forProject1ItIsOkayIfGetDepartureTimeReturnsNull() {
    Flight flight = new Flight();
    assertThat(flight.getDeparture(), is(nullValue()));
  }
  @Test
  void testToString(){
    Flight flight = new Flight(123, "PDX", "11/22/2022 10:40 AM", "BOI", "11/22/2022 10:40 AM");
    assertThat(flight.toString(), equalTo("Flight 123 departs PDX at 11/22/22, 10:40 AM arrives BOI at 11/22/22, 10:40 AM"));
  }

}
