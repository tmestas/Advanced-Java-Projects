package edu.pdx.cs410J.tmestas;

import edu.pdx.cs410J.AbstractFlight;

/**
 * Stores information about a flight
 */
public class Flight extends AbstractFlight {
  private int FlightNumber;
  private String Source;
  private String DepartureTime;
  private String DepartureDate;
  private String Destination;
  private String ArrivalTime;
  private String ArrivalDate;

  /**
   * Constructs a default object flight
   */
  public Flight(){}

  /**
   * This constructs a flight with a specified flight number, source airport, departure time, departure date,
   * destination airport, arrival time and arrival date
   * @param flightNumber flight number
   * @param source 3 letter source airport code
   * @param departureTime source airport departure time in format hh:mm
   * @param departureDate source airport departure date in format mm/dd/yyyy
   * @param destination 3 letter destination airport code
   * @param arrivalTime destination airport arrival time in format hh:mm
   * @param arrivalDate destination airport arrival date in format mm/dd/yyyy
   */
  public Flight(int flightNumber, String source, String departureTime, String departureDate, String destination, String arrivalTime, String arrivalDate){
    this.FlightNumber = flightNumber;
    this.Source = source;
    this.DepartureTime = departureTime;
    this.DepartureDate = departureDate;
    this.Destination = destination;
    this.ArrivalTime = arrivalTime;
    this.ArrivalDate = arrivalDate;
  }

  /**
   * Gets the calling method the flight number
   * @return the flight number
   */
  @Override
  public int getNumber() {
    return this.FlightNumber;
  }

  /**
   * Gets the calling method the source airport code
   * @return the source airport code
   */
  @Override
  public String getSource() {
    return Source;
  }

  /**
   * Gets the calling method the departure date and time in a string
   * @return a string combining the departure date and time
   */
  @Override
  public String getDepartureString() {
    return DepartureTime + " " + DepartureDate;
  }

  /**
   * Gets the calling method the destination airport code
   * @return the destination airport code
   */
  @Override
  public String getDestination() { return Destination; }

  /**
   * Gets the calling method the arrival date and time in a string
   * @return the arrival time and arrival date combined in one string
   */
  @Override
  public String getArrivalString(){
    return ArrivalTime + " " + ArrivalDate;
  }

  public String getDepartureDate(){
    return this.DepartureDate;
  }
  public String getDepartureTime(){
    return this.DepartureTime;
  }
  public String getArrivalDate(){
    return this.ArrivalDate;
  }
  public String getArrivalTime(){
    return this.ArrivalTime;
  }


}
