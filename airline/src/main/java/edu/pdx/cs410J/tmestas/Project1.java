package edu.pdx.cs410J.tmestas;

import com.google.common.annotations.VisibleForTesting;
import java.util.*;
import java.lang.Integer;

/**
 * The main class for the CS410J airline Project
 */
public class Project1 {


  @VisibleForTesting
  static boolean isValidDateAndTime(String dateAndTime) {
    return true;
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
    }

    //Error check times and dates?


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