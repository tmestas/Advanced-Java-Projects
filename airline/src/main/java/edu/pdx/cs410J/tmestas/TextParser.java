package edu.pdx.cs410J.tmestas;

import edu.pdx.cs410J.AirlineParser;
import edu.pdx.cs410J.ParserException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

/**
 * A skeletal implementation of the <code>TextParser</code> class for Project 2.
 */
public class TextParser implements AirlineParser<Airline> {
  private final Reader reader;

  public TextParser(Reader reader) {
    this.reader = reader;
  }

  @Override
  public Airline parse() throws ParserException {
    try (
      BufferedReader br = new BufferedReader(this.reader)
    ) {

      String airlineName = br.readLine();

      if (airlineName == null){
        throw new ParserException("No airline name found");
      }

      Airline newAirline = new Airline(airlineName);
      Flight toAdd;
      String flightNum;
      String source;
      String departureDate;
      String departureTime;
      String destination;
      String arrivalDate;
      String arrivalTime;

      while(!br.readLine().equals("END AIRLINE")){
        flightNum = br.readLine();
        source = br.readLine();
        departureDate = br.readLine();
        departureTime = br.readLine();
        destination = br.readLine();
        arrivalDate = br.readLine();
        arrivalTime = br.readLine();
        toAdd = new Flight(Integer.parseInt(flightNum), source, departureDate, departureTime, destination, arrivalDate, arrivalTime);
        newAirline.addFlight(toAdd);
        br.readLine();
      }

      return newAirline;

    } catch (IOException e) {

      throw new ParserException("While parsing airline text", e);
    }
  }
}
