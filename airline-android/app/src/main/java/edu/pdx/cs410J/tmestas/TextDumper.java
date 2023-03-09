package edu.pdx.cs410J.tmestas;

import edu.pdx.cs410J.AirlineDumper;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;

/**
 * A skeletal implementation of the <code>TextDumper</code> class for Project 2.
 */
public class TextDumper implements AirlineDumper<Airline> {
  private final Writer writer;

  /**
   * constructor for the TextDumper class
   * @param writer writer object to write to the text file
   */
  public TextDumper(Writer writer) {
    this.writer = writer;
  }

  /**
   * Function to dump contents of an airline to a text file
   * @param airline airline to be dumped to the file
   */
  @Override
  public void dump(Airline airline) {

    try (
      PrintWriter pw = new PrintWriter(this.writer)
      ) {

      pw.println(airline.getName());

      for(Flight f: airline.getFlights()) {
        pw.println();
        pw.println(f.getNumber());
        pw.println(f.getSource());
        pw.println(f.getDepartureDateTimeString());
        pw.println(f.getDestination());
        pw.println(f.getArrivalDateTimeString());
        pw.println();
      }
      pw.println("END AIRLINE");
      pw.flush();

    }
    catch(Exception e){
      System.out.println("Error writing to the file");
    }
  }
}
