package edu.pdx.cs410J.tmestas;

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

  /**
   * This unit test will need to be modified (likely deleted) as you implement
   * your project.
   */
  @Test
  void getArrivalStringNeedsToBeImplemented() {
    Flight flight = new Flight();
    assertThrows(UnsupportedOperationException.class, flight::getArrivalString);
  }

  /**
   * This unit test will need to be modified (likely deleted) as you implement
   * your project.
   */
  @Test
  void testGetFlightNumber() {
    Flight flight = new Flight(123, "PDX", "10:40", "11/22/2022", "BOI", "10:45", "11/22/2022");
    assertThat(flight.getNumber(), equalTo(123));
  }

  @Test
  void forProject1ItIsOkayIfGetDepartureTimeReturnsNull() {
    Flight flight = new Flight();
    assertThat(flight.getDeparture(), is(nullValue()));
  }
  
}
