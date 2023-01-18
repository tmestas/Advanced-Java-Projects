package edu.pdx.cs410J.tmestas;

import edu.pdx.cs410J.AbstractFlight;

public class Flight extends AbstractFlight {
  private int FlightNumber;
  private String Source;
  private String DepartureString; //date and time
  private String Destination;
  private String ArrivalString;   //date and time

  public Flight(){

  }
  public Flight(int flightNumber, String source, String departureString, String destination, String arrivalString){
    this.FlightNumber = flightNumber;
    this.Source = source;
    this.DepartureString = departureString;
    this.Destination = destination;
    this.ArrivalString = arrivalString;
  }
  @Override
  public int getNumber() {
    return this.FlightNumber;
  }

  @Override
  public String getSource() {
    return this.Source;
  }

  @Override
  public String getDepartureString() {
    return this.DepartureString;
  }

  @Override
  public String getDestination() { return this.Destination; }

  @Override
  public String getArrivalString() {
    return this.ArrivalString;
  }

  public void displayAll(){
    System.out.println("FLIGHT #" + FlightNumber + ": ");
    System.out.println();
    System.out.println("Source Airport: " + Source);
    System.out.println("Depart Date and Time: " + DepartureString);
    System.out.println("Destination Airport: " + Destination);
    System.out.println("Arrival Date and Time: " + ArrivalString);
    System.out.println();
  }
}
