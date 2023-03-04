package edu.pdx.cs410J.tmestas;

import com.google.common.annotations.VisibleForTesting;
import edu.pdx.cs410J.AirportNames;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * class to handle pretty printing
 */
public class PrettyPrinter {
  private final Writer writer;

  /**
   * constructor
   * @param writer writer to set for class
   */
  public PrettyPrinter(Writer writer) {
    this.writer = writer;
  }

  /**
   * pretty prints an airline
   * @param airline airline to pretty print
   * @throws IOException for printing errors
   */
  public void dump(Airline airline) throws IOException {
    try (PrintWriter pw = new PrintWriter(writer)) {

      long timeDiff;
      long hourDiff;
      long minuteDiff;

      pw.println(airline.getName());

      for (Flight f : airline.getFlights()) {


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
      throw new IOException("Error pretty printing");
      //System.out.println(e.getMessage());
      //System.out.println("Error pretty printing");
    }
  }
}
