package edu.pdx.cs410J.tmestas;

import edu.pdx.cs410J.AbstractAirline;

import java.util.Collection;

public class Airline extends AbstractAirline<Flight> {
  private final String name;

  private Flight NewFlight;

  public Airline(String name) {
    this.name = name;
  }

  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public void addFlight(Flight flight) {
    this.NewFlight = flight;
  }

  @Override
  public Collection<Flight> getFlights() {
    throw new UnsupportedOperationException("This method is not implemented yet");
  }

  public Flight getFlight(){
    return NewFlight;
  }
}
