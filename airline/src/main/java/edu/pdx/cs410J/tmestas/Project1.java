package edu.pdx.cs410J.tmestas;

import com.google.common.annotations.VisibleForTesting;
import java.text.ParseException;
import java.util.*;
import java.lang.Integer;
import java.text.SimpleDateFormat;
import java.util.regex.*;

/**
 * The main class for the CS410J airline Project
 */
public class Project1 {

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
      SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
      format.setLenient(false);
      format.parse(Date);
      return true;
    } catch (ParseException e) {
      return false;
    }
  }

  @VisibleForTesting
  static boolean isValidAirportCode(String airportCode){

    /*
    //Pattern p = Pattern.compile("\\d+");
    Matcher m = p.matcher(airportCode);
    boolean hasNumber = m.find();

    if(!hasNumber || airportCode.length() > 3){
      return false;
    }
    else{
      return true;
    }
    */
    boolean hasNonLetter = false;
    char [] array = airportCode.toCharArray();

    for(char c: array){
      if(!Character.isLetter(c)){
        hasNonLetter = true;
      }
    }

    if(hasNonLetter || airportCode.length() != 3){
      return false;
    }
    else{
      return true;
    }


  }

  public static void main(String[] args) {

    boolean print = false;
    String readme = "README STUFF";

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
      System.err.println("\n\nNOT ENOUGH ARGUMENTS INCLUDED\n" +
              "\nUSAGE:\njava -jar target/airline-2023.0.0.jar [options] \"Airline Name\" " +
              "FlightNumber Source DepartureTime DepartureDate Destination ArrivalTime ArrivalDate");
      return;
    } //check if there are enough arguments

    //Error check times and dates
    String airlineName = args[listSize];
    Integer flightNum = Integer.parseInt(args[listSize + 1]);
    String departAirport = args[listSize + 2];
    String departTime = args[listSize + 3];
    String departDate = args[listSize + 4];
    String arrivalAirport = args[listSize + 5];
    String arrivalTime = args[listSize + 6];
    String arrivalDate = args[listSize + 7];

    //Error check times and dates
    if(!isValidTime(departTime) || !isValidTime(arrivalTime)){
      System.out.println("Invalid Time Format\nValid Format: hh:mm\n");
      return;
    }

    if(!isValidDate(departDate) || !isValidDate(arrivalDate)){
      System.out.println("Invalid Date Format\nValid Format: mm/dd/yyyy\n");
      return;
    }

    if(!isValidAirportCode(departAirport)){
      System.out.println("Invalid Depart Airport Code Format\nValid Format: PDX\n");
    }

    if(!isValidAirportCode(arrivalAirport)){
      System.out.println("Invalid Arrival Airport Code Format\nValid Format: PDX\n");
    }

    Flight flight = new Flight(flightNum, departAirport, departTime, departDate, arrivalAirport, arrivalTime, arrivalDate); //create new flight object
    Airline newAirline = new Airline(airlineName);
    newAirline.addFlight(flight);

    if(print){System.out.println("\n" + newAirline.getFlight().toString() + "\n"); return;}
  }

}