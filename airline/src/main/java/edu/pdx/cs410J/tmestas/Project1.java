package edu.pdx.cs410J.tmestas;

import com.google.common.annotations.VisibleForTesting;
import java.util.*; //is this allowed?
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

    //Flight flight = new Flight();  // Refer to one of Dave's classes so that we can be sure it is on the classpath

    if(args == null) //if there are no args
    {
      //Not recognizing when no args are included, figure this out
      System.err.println("There are no args included (probably put usage here in the future)");
    }

    List<String> options = new LinkedList<String>();

    for (String arg : args) {  //add option flags to a list
      if(arg.contains("-")){
        options.add(arg);
      }
    }

    int listSize = options.size(); //get the list size so we know where to start looking for command line args

    try {
       String test = args[listSize + 4];
    }catch(Exception IntegerOutOfRangeException){
      System.err.println("THERE ARE NOT ENOUGH THINGS PASSED BY COMMAND LINE");
      return;
    }

    Flight flight = new Flight(Integer.parseInt(args[listSize]), args[listSize + 1], args[listSize + 2], args[listSize + 3], args[listSize + 4]); //create new flight object
    flight.displayAll(); //only for testing purposes

    /*
    Airline newAirline = new Airline(args[0]);
    System.out.println(newAirline.getName()); //successfully adds name to newAirline object
    */


  }

}