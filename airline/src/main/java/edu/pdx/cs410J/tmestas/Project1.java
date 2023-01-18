package edu.pdx.cs410J.tmestas;

import com.google.common.annotations.VisibleForTesting;
import java.util.*; //is this allowed?

/**
 * The main class for the CS410J airline Project
 */
public class Project1 {

  @VisibleForTesting
  static boolean isValidDateAndTime(String dateAndTime) {
    return true;
  }

  public static void main(String[] args) {

    //Flight flight = new Flight();  // Refer to one of Dave's classes so that we can be sure it is on the classpath

    if(args == null) //if there are no args
    {
      //Not recognizing when no args are included, figure this out
      System.err.println("There are no args included (probably put usage here in the future");
    }

    List<String> options = new LinkedList<String>();

    for (String arg : args) {  //add option flags to a list
      if(arg.contains("-")){
        options.add(arg);
      }
    }

    int listSize = options.size(); //get the list size so we know where to start looking for command line args

    int flightNum = 99; //hard coded cus dont know how to convert string->int rn
    String source = args[listSize + 1]; //set them equal to where they should be located in command line output
    String departureString = args[listSize + 2];
    String destination = args[listSize + 3];
    String arrivalString = args[listSize + 4];

    Flight flight = new Flight(flightNum, source, departureString, destination, arrivalString); //create new flight object

    flight.displayAll(); //only for testing purposes

    /*
    Airline newAirline = new Airline(args[0]);
    System.out.println(newAirline.getName()); //successfully adds name to newAirline object
    */


  }

}