package edu.pdx.cs410J.tmestas;

import edu.pdx.cs410J.AbstractAirline;

import java.util.ArrayList;
import java.util.List;

/**
 * Stores information about an airline
 */
public class Airline extends AbstractAirline<Flight> {
  private final String name;

  private List<Flight> flightList;
  private Flight NewFlight;

  /**
   * constructor for testing purposes
   */
  public Airline(){
    this.name = null;
    new ArrayList<Flight>();
  }
  /**
   * Constructs an airline object with a specified name
   * @param name name of the airline being constructed
   */
  public Airline(String name) {
    this.name = name;
    flightList = new ArrayList<Flight>();
  }

  public void setList(List<Flight> toSet){
    this.flightList = toSet;
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
    flightList.add(flight);
  }

  /**
   * Returns a list of flights for the airline
   * @return list of flights
   */

  @Override
  public List<Flight> getFlights() {
    return flightList;
  }


}
