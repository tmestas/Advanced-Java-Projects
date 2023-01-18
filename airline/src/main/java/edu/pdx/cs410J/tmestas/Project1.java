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

    //String[] options = new String[];
    //List<String> options = new List<String>();

    List<String> options = new LinkedList<String>();

    for (String arg : args) { //is printing all args individually, recognizes quotes as 1 arg
      if(arg.contains("-")){
        options.add(arg);
      }
      //System.out.println(arg);
    }
    int listSize = options.size() + 1;
/*
    int flightNum = 0;
    String source = null;
    String departureString = null;
    String destination = null;
    String arrivalString = null;

    int i = listSize;


    while(i < args.length){
      //if(i == listSize){flightNum = 99;}
      if(i == listSize + 1){source = args[i];}
      if(i == listSize + 2){departureString = args[i];}
      if(i == listSize + 3){destination = args[i];}
      if(i == options.size() + 4){arrivalString = args[i];}
      ++i;
    }
    */


    //int flightNum = args[listSize].to();
    int flightNum = 99;
    String source = args[listSize + 1];
    String departureString = args[listSize + 2];
    String destination = args[listSize + 3];
    String arrivalString = args[listSize + 4];

    Flight flight = new Flight(flightNum, source, departureString, destination, arrivalString);
    flight.displayAll();

    /*
    Airline newAirline = new Airline(args[0]);
    System.out.println(newAirline.getName()); //successfully adds name to newAirline object
    */


  }

}