package edu.pdx.cs410J.tmestas;

import com.google.common.annotations.VisibleForTesting;
import edu.pdx.cs410J.ParserException;

import java.io.*;
import java.text.ParseException;
import java.util.*;
import java.lang.Integer;
import java.text.SimpleDateFormat;


/**
 * The main class for the CS410J airline Project
 */
public class Project3 {

  /**
   * A method to check if the user entered time is in valid format
   * @param Time string containing the users input
   * @return a boolean signifying if the passed in time is valid
   */
  @VisibleForTesting
  static boolean isValidTime(String Time){
    try {
      SimpleDateFormat format = new SimpleDateFormat("hh:mm a");
      format.setLenient(false);
      format.parse(Time);
      return true;
    } catch (ParseException e) {
      return false;
    }

  }

  /**
   * A method to check if the user entered date is in valid format
   * @param Date a string containing the user input for date
   * @return a boolean signifying if the user entered date is in valid format
   */
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

  /**
   * A method to check if the user entered airport code is in valid format
   * @param airportCode a string containing the user entered airport code
   * @return a boolean signifying if the user entered airport code is in valid format
   */
  @VisibleForTesting
  static boolean isValidAirportCode(String airportCode){
    boolean hasNonLetter = false;
    char [] array = airportCode.toCharArray();

    for(char c: array){
      if(!Character.isLetter(c)){
        System.out.println("\nNon letter found in airport code");
        hasNonLetter = true;
      }
    }

    if(airportCode.length() > 3){
      System.out.println("\nAirport code too long");
    }
    else if(airportCode.length() < 3){
      System.out.println("\nAirport code too short");
    }

    if(hasNonLetter || airportCode.length() != 3){
      return false;
    }
    else{
      return true;
    }
  }

  /** A method to seperate command line options from arguments, and check their validity
   *
   * @param args
   * @param startingIndex
   * @return
   */
  @VisibleForTesting
  static List<String> separateArguments(String args[], int startingIndex){
    List<String> newArgs = new LinkedList<String>();
    int size = args.length;

    for(int i = startingIndex; i < size; ++i){
      newArgs.add(args[i]);
    }

    return newArgs;
  }

  /**
   * To get the provided file path if the user used the -textFile flag
   * @param args args to look for the filepath in
   * @return string with filepath
   */
  @VisibleForTesting //no test
  static String getFilePath(String args[]){
    String filePath = new String();
    for(int i = 0; i < args.length; ++i){
      if(args[i].equals("-textFile")){
          filePath = (args[i + 1]);
      }
    }
    return filePath;
  }

  /**
   * To check if the provided filepath is valid
   * @param filePath provided filepath from the command line
   * @return true or false
   */
  @VisibleForTesting
  static boolean isValidFilePath(String filePath) {
     File check = new File(filePath);
     return check.exists();
  }

  /**
   * A method to run all input check functions
   * @param flightNum user entered flight number
   * @param departAirport user entered departure airport code
   * @param departTime user entered departure time
   * @param departDate user entered departure date
   * @param arrivalAirport user entered arrival airport code
   * @param arrivalTime user entered arrival time
   * @param arrivalDate user entered arrival date
   * @return a boolean signifying whether the program should continue, or if there was an error
   * parsing the input
   */
  @VisibleForTesting
  static boolean checkValidInput(Integer flightNum, String departAirport, String departTime, String departDate,
                                 String arrivalAirport, String arrivalTime, String arrivalDate)
  {
    boolean value = true;

    if(!isValidTime(departTime)){
      System.out.println("\n" + departTime+" is not a valid departure time.\nValid Format: hh:mm\n");
      value = false;
    }

    if(!isValidTime(arrivalTime)){
      System.out.println("\n" + arrivalTime+" is not a valid arrival time.\nValid Format: hh:mm\n");
      value = false;
    }

    if(!isValidDate(departDate)){
      System.out.println("\n" + departDate +" is not a valid departure date.\nValid Format: mm/dd/yyyy\n");
      value = false;
    }

    if(!isValidDate(arrivalDate)){
    System.out.println("\n" +arrivalDate +" is not a valid arrival date.\nValid Format: mm/dd/yyyy\n");
    value = false;
    }

    if(!isValidAirportCode(departAirport) || !isValidAirportCode(arrivalAirport)){
      System.out.println("\nAirport Code must be 3 letters and no other characters\n");
      value = false;
    }

    return value;
  }

  /**
   * main method for the program
   * @param args command line arguments
   */
  public static void main(String[] args) {

    boolean print = false;
    boolean textFile = false;
    String filePath = new String();

    List<String> options = new LinkedList<String>();

    for (String arg : args) {
      if(arg.contains("-print") || arg.contains("-README") || arg.contains("-textFile")){
        options.add(arg);
      }
    } //add option flags to a list

    for(String option: options){
      if(option.equals("-README")){
        try (InputStream readme = Project3.class.getResourceAsStream("README.txt"))
        {
          BufferedReader reader = new BufferedReader(new InputStreamReader(readme));
          String line;
          while((line=reader.readLine())!=null){
            System.out.println(line);
          }
        }
        catch(Exception e)
        {
          return;
        }

        return;
      }
      else if(option.equals("-print")){print = true;}
      else if (option.equals("-textFile")) {textFile = true;}
      else {
        System.out.println("\nUnrecognized command line option.");
        return;
      }
    } //check for flags

    int optionListSize = options.size(); //get the list size, so we know where to start looking for command line args

    if(textFile){
      filePath = getFilePath(args);
       ++optionListSize;
    } //get textFile path and add one to optionListSize count

    List<String> argsListSize = separateArguments(args, optionListSize); //to get a true count of args

    if(argsListSize.size() < 10){
      System.err.println("\n\nNOT ENOUGH ARGUMENTS INCLUDED\n" +
              "Run with -README for instructions");
      return;
    } //if there are too many args
    else if(argsListSize.size() > 10){
      System.err.println("\n\nTOO MANY ARGUMENTS INCLUDED\n" +
              "Run with -README for instructions");
      return;
    } //if there are not enough args

    try{
      Integer.parseInt(args[optionListSize + 1]);
    } //make sure flight num is an integer
    catch(Exception e){
      System.out.println("\nFlight Number is not valid, please enter an integer value\n");
      return;
    }

    String airlineName = args[optionListSize];
    Integer flightNum = Integer.parseInt(args[optionListSize + 1]);
    String departAirport = args[optionListSize + 2];
    String departureDate = args[optionListSize + 3];
    String departureTime = args[optionListSize + 4] + " " + args[optionListSize + 5];
    String departureDateTime = args[optionListSize + 3] + " " + args[optionListSize + 4] + " " + args[optionListSize + 5];
    String arrivalAirport = args[optionListSize + 6];
    String arrivalDate = args[optionListSize + 7];
    String arrivalTime = args[optionListSize + 8] + " " + args[optionListSize + 9];
    String arrivalDateTime = args[optionListSize + 7] + " " + args[optionListSize + 8] + " " + args[optionListSize + 9];

    if(!checkValidInput(flightNum, departAirport, departureTime, departureDate, arrivalAirport, arrivalTime, arrivalDate)){return;} //Error check times and dates

    Flight flight = new Flight(flightNum, departAirport, departureDateTime, arrivalAirport, arrivalDateTime);
    Airline newAirline = new Airline(airlineName);
    newAirline.addFlight(flight); //for command line flight


    Flight temp = new Flight();
    for(Flight f: newAirline.getFlights()){
      temp = f;
    } //get flight from newly created airline (redundant because flight already stores it)


    if(textFile){

      boolean fileHasContent = true;
      //get an airline and all its flights from the file
      Airline tempAirline = new Airline("");

      try{
        FileReader f = new FileReader(filePath);
        BufferedReader b = new BufferedReader(f);
        TextParser parser = new TextParser(b);
        tempAirline = parser.parse();
        tempAirline.addFlight(flight); //append new airline to one read from file
      }
      catch(FileNotFoundException e){
        System.out.println("File does not exist, creating file and adding information");
        fileHasContent = false;
      }
      catch(ParserException e){
        System.out.println("Text file is malformatted");
        return;
        //fileHasContent = false;
      }
      catch(NumberFormatException e){
        System.out.println("Text file is malformatted");
        return;
      }

      if(fileHasContent && tempAirline.getName().equals(newAirline.getName())){
        try {
          FileWriter f = new FileWriter(filePath, false);
          BufferedWriter b = new BufferedWriter(f);
          PrintWriter writer = new PrintWriter(b);
          TextDumper dumper = new TextDumper(writer);
          dumper.dump(tempAirline); //use tempAirline
          //System.out.println("ADDED TEMP");
        }
        catch (Exception e) {
          System.out.println("Error opening the file");
        }
      }
      else if(fileHasContent && !tempAirline.getName().equals(newAirline.getName())){
        if(print){System.out.println("\n" + temp.toString() + "\n");}
        System.out.println("Airline name did not match text file, information not added");
        return;
      }
      else{
        try {
          FileWriter f = new FileWriter(filePath, false);
          BufferedWriter b = new BufferedWriter(f);
          PrintWriter writer = new PrintWriter(b);
          TextDumper dumper = new TextDumper(writer);
          dumper.dump(newAirline);
        }
        catch (Exception e) {
          System.out.println("Could not access directory");
        }
      }
    } //if textFile option was included


    if(print){System.out.println("\n" + temp.toString() + "\n");} //if the print option was included
  }

}