package edu.pdx.cs410J.tmestas;

import com.google.common.annotations.VisibleForTesting;

import java.text.ParseException;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.lang.Integer;
import java.time.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * The main class for the CS410J airline Project
 */
public class Project1 {


  @VisibleForTesting
  static boolean isValidDateAndTime(String dateAndTime) {
    return true;
  }
  @VisibleForTesting
  static boolean isValidTime(String Time){
    try {
      String[] time = Time.split(":");
      return  Integer.parseInt(time[0]) < 24 && Integer.parseInt(time[1]) < 60;
    } catch (Exception e) {
      return false;
    }

  }
  @VisibleForTesting
  static boolean isValidDate(String Date){
    try {
      SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
      format.setLenient(false);
      format.parse(Date);
      return true;
    } catch (ParseException e) {
      return false;
    }
  }

  public static void main(String[] args) {

    boolean print = false;
    String readme = "README STUFF";

    if(args == null) //if there are no args
    {
      //Not recognizing when no args are included, figure this out
      System.err.println("There are no args included (probably put usage here in the future)");
    }

    List<String> options = new LinkedList<String>();

    for (String arg : args) {
      if(arg.contains("-")){
        options.add(arg);
      }
    } //add option flags to a list

    for(String option: options){
      if(option.equals("-README")){System.out.println(readme); return;}
      if(option.equals("-print")){print = true;}
    } //check for flags

    int listSize = options.size(); //get the list size, so we know where to start looking for command line args

    try {
      if(args.length != 9){
        throw new RuntimeException();
      }
    }catch(Exception e){ //there are not enough arguments in the command line
      System.err.println("\nNOT ENOUGH ARGUMENTS INCLUDED\n\n" +
              "\nUSAGE:\njava -jar target/airline-2023.0.0.jar [options] \"Airline Name\" " +
              "FlightNumber Source DepartureTime DepartureDate Destination ArrivalTime ArrivalDate");
      return;
    } //check if there are enough arguments

    //Error check times and dates

    String departTime = args[listSize + 3];
    String departDate = args[listSize + 4];
    String arrivalTime = args[listSize + 6];
    String arrivalDate = args[listSize + 7];


    //String departDateTime = args[listSize + 3] + " " + args[listSize + 4];
    //String arrivalDateTime = args[listSize + 6] + " " + args[listSize + 7];

    //boolean test1 = isValidDateAndTime(departDateTime);
    //boolean test2 = isValidDateAndTime(arrivalDateTime);

    boolean test1 = isValidDate(departDate);
    boolean test2 = isValidDate(arrivalDate);


    /*boolean test1 = isValidTime(departTime);
    boolean test2 = isValidTime(arrivalTime);
    boolean date1 = isValidTime(departDate);
    boolean date2 = isValidTime(arrivalDate);
    */


    if(!test1){
      System.out.println("departure date format invalid");
    }

    if(!test2){
      System.out.println("arrival date format invalid");
    }

    Flight flight = new Flight(Integer.parseInt(args[listSize + 1]), args[listSize + 2], args[listSize + 3], args[listSize + 4], args[listSize + 5], args[listSize + 6], args[listSize + 7]); //create new flight object
    Airline newAirline = new Airline(args[listSize]);
    newAirline.addFlight(flight);

    //Flight Number = args[listSize + 1]
    //Departure Airport Code = args[listSize + 2]
    //Depart time = args[listSize + 3]
    //Depart date = args[listSize + 4]
    //Arrival Airport Code = args[listSize + 5]
    //Arrival time = args[listSize + 6]
    //Arrival date = args[listSize + 7]

    if(print){newAirline.getFlight().displayAll(); return;} //only for testing purposes
    //System.out.println();
    //newAirline.addFlight(flight);
  }

}