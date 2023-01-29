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

  public TextDumper(Writer writer) {
    this.writer = writer;
  }

  @Override
  public void dump(Airline airline) {

    try (
      PrintWriter pw = new PrintWriter(this.writer)
      ) {
      //pw.println("START AIRLINE");
      pw.println(airline.getName());

      for(Flight f: airline.getFlights()) {
        pw.println();
        pw.println(f.getNumber());
        pw.println(f.getSource());
        pw.println(f.getDepartureString());
        pw.println(f.getDestination());
        pw.println(f.getArrivalString());
        pw.println("END FLIGHT\n");
      }
      pw.println("END AIRLINE\n");
      pw.flush();
    }
    catch(Exception e){
      System.out.println("Error writing to the file");
    }
  }
}
