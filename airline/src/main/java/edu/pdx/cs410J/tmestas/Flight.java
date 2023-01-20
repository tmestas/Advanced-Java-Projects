package edu.pdx.cs410J.tmestas;

import edu.pdx.cs410J.AbstractFlight;
import java.util.*;
import java.sql.Time;


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

    return 42;
  }

  @Override
  public String getSource() {
    throw new UnsupportedOperationException("This method is not implemented yet");
  }

  @Override
  public String getDepartureString() {
    throw new UnsupportedOperationException("This method is not implemented yet");
  }

  @Override
  public String getDestination() { throw new UnsupportedOperationException("This method is not implemented yet"); }

  @Override
    public String getArrivalString() {
    throw new UnsupportedOperationException("This method is not implemented yet");
  }

  public void displayAll(){
    System.out.println();
    System.out.println("FLIGHT #" + FlightNumber + ": ");
    System.out.println();
    System.out.println("Source Airport: " + Source);
    System.out.println("Depart Date: " + this.DepartureDate);
    System.out.println("Depart Time: " + this.DepartureTime);
    System.out.println("Destination Airport: " + Destination);
    System.out.println("Arrival Date: " + this.ArrivalDate);
    System.out.println("Arrival Time: " + this.ArrivalTime);
    System.out.println();
  }
}
