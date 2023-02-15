package edu.pdx.cs410J.tmestas;

import com.google.common.annotations.VisibleForTesting;
import edu.pdx.cs410J.AirportNames;
import edu.pdx.cs410J.ParserException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.*;
import java.lang.Integer;
import java.text.SimpleDateFormat;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;



/**
 * The main class for the CS410J airline Project
 */
public class Project4 {

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
   * @param args command line input
   * @param startingIndex where the commands start
   * @return list of args without options
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
  //test
  /**
   * To get the provided file path if the user used the -textFile flag
   * @param args args to look for the filepath in
   * @return string with filepath
   */
  @VisibleForTesting //no test
  static String getFilePath(String args[], String toFind){
    String filePath = new String();
    for(int i = 0; i < args.length; ++i){
      if(args[i].equals(toFind)){
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
   * To check the list of airports to see if the one user passed exists
   * @param airportCode 3 letter airport code
   * @return boolean signifying whether the airport exists or not
   */
  @VisibleForTesting
  static boolean doesAirportCodeExist(String airportCode){ //write unit test
    if(AirportNames.getName(airportCode) != null) {return true;}
    else{return false;}
  }

  /**
   * Print contents of an airline to a file in a readable way
   * @param toPrint Airline whose contents must be printed
   * @param writer Writer object
   * @return boolean signifying if it was successful or not
   */
  @VisibleForTesting
  static boolean doPrettyPrint(Airline toPrint, PrintWriter writer){

      try (PrintWriter pw = new PrintWriter(writer)) {

        long timeDiff;
        long hourDiff;
        long minuteDiff;

        pw.println(toPrint.getName());

        for (Flight f : toPrint.getFlights()) {

          SimpleDateFormat duration = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
          DateFormat formatter = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT);
          Date d1 = duration.parse(f.getDepartureDateTimeString());
          Date d2 = duration.parse(f.getArrivalDateTimeString());
          timeDiff = d2.getTime() - d1.getTime();
          hourDiff = (timeDiff / (1000 * 60 * 60)) % 24;
          minuteDiff = (timeDiff / (1000 * 60)) % 60;

          pw.println();
          pw.println("Flight Number: " + f.getNumber());
          pw.println("Departure Date & Time: " + formatter.format(f.getDepartureDateTime()));
          pw.println("Arrival Date & Time: " + formatter.format(f.getArrivalDateTime()));
          pw.println("From " + AirportNames.getName(f.getSource()) + " to " + AirportNames.getName(f.getDestination()));
          if (hourDiff > 0 && minuteDiff > 0) {
            pw.println("Flight Duration: " + hourDiff + " hours and " + minuteDiff + " minutes");
          } else if (hourDiff <= 0 && minuteDiff > 0) {
            pw.println("Flight Duration: " + minuteDiff + " minutes");
          } else if (hourDiff > 0 && minuteDiff <= 0) {
            pw.println("Flight Duration: " + hourDiff + " hours");
          }
          pw.println();

        }

        pw.flush();
      } catch (Exception e) {
        System.out.println(e.getMessage());
        System.out.println("Error writing to the pretty print file FROM FUNCTION");
        return false;
      }


    return true;
  } //needs test

  /**
   * Pretty prints to the console
   * @param airline airline to pretty print
   * @return boolean signifying success or failure
   */
  @VisibleForTesting
  static boolean prettyPrintConsole(Airline airline){
    System.out.println("\n******** PRETTY PRINT ********\n");
    long timeDiff =0;
    long hourDiff =0;
    long minuteDiff =0;
    Date d1;
    Date d2;

    System.out.println(airline.getName());

    for (Flight f : airline.getFlights()) {

      SimpleDateFormat duration = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
      DateFormat formatter = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT);
      try {
        d1 = duration.parse(f.getDepartureDateTimeString());
        d2 = duration.parse(f.getArrivalDateTimeString());
        timeDiff = d2.getTime() - d1.getTime();
        hourDiff = (timeDiff / (1000 * 60 * 60)) % 24;
        minuteDiff = (timeDiff / (1000 * 60)) % 60;
      }
      catch(Exception e){System.out.println("Could not parse time"); return false;}


      System.out.println();
      System.out.println("Flight Number: " + f.getNumber());
      System.out.println("Departure Date & Time: " + formatter.format(f.getDepartureDateTime()));
      System.out.println("Arrival Date & Time: " + formatter.format(f.getArrivalDateTime()));
      System.out.println("From " + AirportNames.getName(f.getSource()) + " to " + AirportNames.getName(f.getDestination()));
      if (hourDiff > 0 && minuteDiff > 0) {
        System.out.println("Flight Duration: " + hourDiff + " hours and " + minuteDiff + " minutes");
      } else if (hourDiff <= 0 && minuteDiff > 0) {
        System.out.println("Flight Duration: " + minuteDiff + " minutes");
      } else if (hourDiff > 0 && minuteDiff <= 0) {
        System.out.println("Flight Duration: " + hourDiff + " hours");
      }
      System.out.println();

    }

    System.out.println("\n******** END PRETTY PRINT ********\n");

    return true;
  }

  @VisibleForTesting
  static boolean isDepartureTimeNotAfterArrivalTime(String depart, String arrive){
    SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
    Date DepartureDate = new Date();
    Date ArrivalDate = new Date();

    try {
      DepartureDate = formatter.parse(depart);
    }catch(Exception e){
      System.out.println("ERROR PARSING DEPARTURE DATE");
    }
    try {
      ArrivalDate = formatter.parse(arrive);
    }catch(Exception e){
      System.out.println("ERROR PARSING ARRIVAL DATE");
    }

    if(DepartureDate.before(ArrivalDate)){return true;}
    else{return false;}
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

    if(value && !isDepartureTimeNotAfterArrivalTime(departDate + " " + departTime, arrivalDate + " " + arrivalTime)){
      System.out.println("\nDeparture time is after arrival time, exiting\n");
      value = false;
    }

    if(!isValidAirportCode(departAirport) || !isValidAirportCode(arrivalAirport)){
      System.out.println("\nAirport Code must be 3 letters and no other characters\n");
      value = false;
    }
    else if(!doesAirportCodeExist(departAirport)){ //maybe write test?
      System.out.println("\n" + departAirport + " is not a known airport, exiting");
      value = false;
    }
    else if(!doesAirportCodeExist(arrivalAirport)) { //maybe write test?
      System.out.println("\n" + arrivalAirport + " is not a known airport, exiting");
      value = false;
    }


    return value;
  }

  /**
   * check for a '-' after the -pretty flag
   * @param args command line arguments
   * @return boolean signifying pretty print to console, or not
   */
  @VisibleForTesting
  public static boolean checkPrettyConsole(String args[]){
    int check = 0;
    int i = 0;
    for(i = 0; i < args.length; ++i){
      if(args[i].equals("-pretty")){
        check = i + 1;
      }
    }

    if(args[check].equals("-") || args[check + 1].equals("-")){
      return true;
    }


    return false;
  }


  /**
   * check if a pretty print path exists
   * @param args command line arguments
   * @return a boolean signifying if there is a path or not
   */
  @VisibleForTesting
  public static boolean checkPrettyPath(String args[]){
    int check = 0;
    for(int i = 0; i < args.length; ++i){
      if(args[i].equals("-pretty")){check = i + 1;}
    }

    if(args[check].equals("-")){
      return false;
    }

    return true;
  }

  /**
   * main method for the program
   * @param args command line arguments
   */
  public static void main(String[] args) {

    if(args.length == 0){
      System.out.println("\n\nNo arguments supplied\n");
      System.out.println("Usage: command <options> [args]");
      System.out.println("Run with -README for more information\n\n");
      return;
    }

    boolean print = false;
    boolean textFile = false;
    boolean xmlFile = false;
    boolean prettyPrint = false;
    boolean prettyConsole = false;
    boolean prettyHasPath = false;
    String textFilePath = new String();
    String prettyFilePath = new String();
    String xmlFilePath = new String();

    List<String> options = new LinkedList<String>();

    for (String arg : args) {
      if(arg.contains("-print") || arg.contains("-README") || arg.contains("-textFile") || arg.contains("-pretty") || arg.contains("-xmlFile")){
        options.add(arg);
      }
    } //add option flags to a list

    for(String option: options){
      if(option.equals("-README")){
        try (InputStream readme = Project4.class.getResourceAsStream("README.txt"))
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
      else if(option.equals("-pretty")){prettyPrint = true;}
      else if(option.equals("-xmlFile")){xmlFile = true;}
      else {
        System.out.println("\nUnrecognized command line option.");
        return;
      }
    } //check for flags

    if(textFile && xmlFile){System.out.println("\n-textFile and -xmlFile cannot be used at the same time, exiting\n"); return;}

    int optionListSize = options.size(); //get the list size, so we know where to start looking for command line args

    if(textFile){
      textFilePath = getFilePath(args, "-textFile");
       ++optionListSize;
    } //get textFile path and add one to optionListSize count

    if(prettyPrint){
      prettyFilePath = getFilePath(args, "-pretty");
      prettyConsole = checkPrettyConsole(args);
      prettyHasPath = checkPrettyPath(args);
      ++optionListSize;
      if(prettyConsole && prettyHasPath){
        ++optionListSize;
      }
    } //get prettyPrint path and add one to optionListSize count

    if(xmlFile){
      xmlFilePath = getFilePath(args, "-xmlFile");
      ++optionListSize;
    }

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

    Airline tempAirline = new Airline("");

    if(textFile){

      boolean fileHasContent = true;
      //get an airline and all its flights from the file

      try{
        FileReader f = new FileReader(textFilePath);
        BufferedReader b = new BufferedReader(f);
        TextParser parser = new TextParser(b);
        tempAirline = parser.parse();
        tempAirline.addFlight(flight); //append new airline to one read from file
        Collections.sort(tempAirline.getFlights());
      }
      catch(FileNotFoundException e){
        System.out.println("-textFile file does not exist, creating file and adding information");
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
          FileWriter f = new FileWriter(textFilePath, false);
          BufferedWriter b = new BufferedWriter(f);
          PrintWriter writer = new PrintWriter(b);
          TextDumper dumper = new TextDumper(writer);
          dumper.dump(tempAirline); //use tempAirline
          //System.out.println("ADDED TEMP");
        }
        catch (Exception e) {
          System.out.println("Error opening the file provided for -textFile");
        }
      }
      else if(fileHasContent && !tempAirline.getName().equals(newAirline.getName())){
        if(print){System.out.println("\n" + temp.toString() + "\n");}
        System.out.println("Airline name did not match text file, information not added");
        return;
      }
      else{
        try {
          FileWriter f = new FileWriter(textFilePath, false);
          BufferedWriter b = new BufferedWriter(f);
          PrintWriter writer = new PrintWriter(b);
          TextDumper dumper = new TextDumper(writer);
          dumper.dump(newAirline);
        }
        catch (Exception e) {
          System.out.println("Could not access -textFile directory");
        }
      }
    } //if textFile option was included

    if(xmlFile){







      XmlDumper newDumper = new XmlDumper(xmlFilePath);
      try{
        newDumper.dump(newAirline);
      }catch(Exception e){
        System.out.println("Error writing to XML file (from main)");
      }


    }

    if(prettyPrint){
      //System.out.println("Pretty Print File Path: " + prettyFilePath);
       try{
        FileWriter f = new FileWriter(prettyFilePath, false);
        BufferedWriter b = new BufferedWriter(f);
        PrintWriter writer = new PrintWriter(b);
        if(tempAirline.getFlights().size() > 0){doPrettyPrint(tempAirline, writer);}
        else {doPrettyPrint(newAirline, writer);}
      } catch(IOException e){
        System.out.println("Could not write to pretty print file");
      }

       if(prettyConsole){
         if(tempAirline.getFlights().size() > 0){prettyPrintConsole(tempAirline);}
         else {prettyPrintConsole(newAirline);}
       }
    } //if prettyPrint option was included

    if(print){System.out.println("\n" + temp.toString() + "\n");} //if the print option was included
  }

}