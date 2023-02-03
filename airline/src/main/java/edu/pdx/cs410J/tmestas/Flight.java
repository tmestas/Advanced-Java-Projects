package edu.pdx.cs410J.tmestas;

import edu.pdx.cs410J.AbstractFlight;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * Stores information about a flight
 */
public class Flight extends AbstractFlight implements Comparable<Flight> {
  private int FlightNumber;
  private String Source;
  private Date DepartureDateTime;
  private String DepartureDateTimeString;
  private String Destination;
  private Date ArrivalDateTime;
  private String ArrivalDateTimeString;

  /**
   * Constructs a default object flight
   */
  public Flight(){}

  /**
   * This constructs a flight with a specified flight number, source airport, departure time, departure date,
   * destination airport, arrival time and arrival date
   * @param flightNumber flight number
   * @param source 3 letter source airport code
   * @param departureDateTime string containing departure date and time to be converted to date
   * @param destination 3 letter destination airport code
   * @param arrivalDateTime string containing arrival date and time to be converted to date
   */
  public Flight(int flightNumber, String source, String departureDateTime, String destination, String arrivalDateTime){

    SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm a");

    this.FlightNumber = flightNumber;
    this.Source = source;
    this.DepartureDateTimeString = departureDateTime;
    try { //convert string to date
      this.DepartureDateTime = formatter.parse(departureDateTime);
    }catch(Exception e){
      System.out.println("ERROR PARSING DEPARTURE DATE TIME");
    }

    this.Destination = destination;
    this.ArrivalDateTimeString = arrivalDateTime;
    try { //convert string to date
      this.ArrivalDateTime = formatter.parse(arrivalDateTime);
    }catch(Exception e){
      System.out.println("ERROR PARSING ARRIVAL DATE TIME");
    }

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
    DateFormat formatter = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT);
    String formatted = formatter.format(DepartureDateTime);
    return formatted;
    //return DepartureDateTime.toString();
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
    DateFormat formatter = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT);
    String formatted = formatter.format(ArrivalDateTime);
    return formatted;
    //return ArrivalDateTime.toString();
  }

  public String getDepartureDateTimeString(){
    return DepartureDateTimeString;
  }

  public String getArrivalDateTimeString(){
    return ArrivalDateTimeString;
  }

  public Date getDepartureDateTime(){
    return DepartureDateTime;
  }
  @Override
  public int compareTo(Flight toCompare) {
    int comparison = 0;
    comparison = this.getSource().compareTo(toCompare.getSource());
    if(comparison == 0){
      comparison = this.getDepartureDateTime().compareTo(toCompare.getDepartureDateTime());
    }
    return comparison;
  }
}
