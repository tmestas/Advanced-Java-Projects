package edu.pdx.cs410J.tmestas;

import edu.pdx.cs410J.AbstractAirline;

import java.util.Collection;

/**
 * Stores information about an airline
 */
public class Airline extends AbstractAirline<Flight> {
  private final String name;

  private Flight NewFlight;

  /**
   * Constructs an airline object with a specified name
   * @param name name of the airline being constructed
   */
  public Airline(String name) {
    this.name = name;
  }

  /**
   * Gets the name of the airline for the calling method
   * @return a string containing the name of the airline
   */
  @Override
  public String getName() {
    return this.name;
  }

  /**
   * Adds a flight to the airline
   * @param flight a flight object to be added to the airline
   */
  @Override
  public void addFlight(Flight flight) {
    this.NewFlight = flight;
  }

  /**
   * Not implemented yet
   * @return Throws an exception
   */

  @Override
  public Collection<Flight> getFlights() {
    throw new UnsupportedOperationException("This method is not implemented yet");
  }

  /**
   * Gets the flight object for the calling method
   * @return a flight object
   */
  public Flight getFlight(){
    return NewFlight;
  }
}
