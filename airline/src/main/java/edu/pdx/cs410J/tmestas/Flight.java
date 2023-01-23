package edu.pdx.cs410J.tmestas;

import edu.pdx.cs410J.AbstractFlight;

public class Flight extends AbstractFlight {
  private int FlightNumber;
  private String Source;
  private String DepartureTime;
  private String DepartureDate;
  private String Destination;
  private String ArrivalTime;
  private String ArrivalDate;

  public Flight(){

  }
  public Flight(int flightNumber, String source, String departureTime, String departureDate, String destination, String arrivalTime, String arrivalDate){
    this.FlightNumber = flightNumber;
    this.Source = source;
    this.DepartureTime = departureTime;
    this.DepartureDate = departureDate;
    this.Destination = destination;
    this.ArrivalTime = arrivalTime;
    this.ArrivalDate = arrivalDate;
  }
  @Override
  public int getNumber() {
    return this.FlightNumber;
  }
  @Override
  public String getSource() {
    return Source;
  }
  @Override
  public String getDepartureString() {
    return DepartureTime + " " + DepartureDate;
  }
  @Override
  public String getDestination() { return Destination; }

  @Override
  public String getArrivalString(){
    return ArrivalTime + " " + ArrivalDate;
  }
}
