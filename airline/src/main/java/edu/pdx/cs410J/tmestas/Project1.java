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

    boolean print = false;
    String readme = "README STUFF";

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

    for(String option: options){
      if(option.equals("-README")){System.out.println(readme); return;}
      if(option.equals("-print")){print = true;}
    }

    int listSize = options.size(); //get the list size so we know where to start looking for command line args

    try {
       String test = args[listSize + 4];
    }catch(Exception e){ //there are not enough arguments in the command line
      System.err.println("NOT ENOUGH ARGUMENTS INCLUDED\n" +
              "USAGE: java -jar target/airline-2023.0.0.jar [options] \"Airline Name\" " +
              "FlightNumber Source \"Departure Time and Date\" Destination \"Arrival Time and Date\"");
      return;
    }

    Flight flight = new Flight(Integer.parseInt(args[listSize]), args[listSize + 1], args[listSize + 2], args[listSize + 3], args[listSize + 4]); //create new flight object

    if(print){flight.displayAll(); return;} //only for testing purposes

    System.out.println(flight.toString());

    /*
    Airline newAirline = new Airline(args[0]);
    System.out.println(newAirline.getName()); //successfully adds name to newAirline object
    */


  }

}