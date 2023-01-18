package edu.pdx.cs410J.tmestas;

import edu.pdx.cs410J.AbstractFlight;
import java.util.*;
import java.sql.Time;


public class Flight extends AbstractFlight {
  private int FlightNumber;
  private String Source;
  //private String DepartureString; //date and time

  private String DepartureTime;
  private String DepartureDate;
  private String Destination;
  //private String ArrivalString;   //date and time
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
    return this.Source;
  }

  @Override
  public String getDepartureString() {
    return this.DepartureDate + " " +  this.DepartureTime;
  }

  @Override
  public String getDestination() { return this.Destination; }

  @Override
    public String getArrivalString() {
    return this.ArrivalDate + " " +  this.ArrivalTime;
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
