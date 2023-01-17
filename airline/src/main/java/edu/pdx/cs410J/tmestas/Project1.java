package edu.pdx.cs410J.tmestas;

import com.google.common.annotations.VisibleForTesting;

/**
 * The main class for the CS410J airline Project
 */
public class Project1 {

  @VisibleForTesting
  static boolean isValidDateAndTime(String dateAndTime) {
    return true;
  }

  public static void main(String[] args) {
    Flight flight = new Flight();  // Refer to one of Dave's classes so that we can be sure it is on the classpath

    if(args[0] == null) //if there are no args
    {
      System.err.println("There are no args included (probably put usage here in the future");
    }

    for (String arg : args) {
      System.out.println(arg);
    }
  }

}