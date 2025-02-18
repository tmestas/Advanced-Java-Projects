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

  /**
   * Constructor for the TextParser class
   * @param reader reader that is being used to parse the text file
   */
  public TextParser(Reader reader) {
    this.reader = reader;
  }

  /**
   * Parses the text file and returns an airline
   * @return returns the airline found in the text file
   * @throws ParserException for errors parsing the file
   */
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
      String departureDateTime;
      String destination;
      String arrivalDateTime;

      while(!br.readLine().equals("END AIRLINE")){
        flightNum = br.readLine();
        source = br.readLine();
        departureDateTime = br.readLine();
        destination = br.readLine();
        arrivalDateTime = br.readLine();
        toAdd = new Flight(Integer.parseInt(flightNum), source, departureDateTime, destination, arrivalDateTime);
        newAirline.addFlight(toAdd);
        br.readLine();
      }

      return newAirline;

    } catch (IOException e) {

      throw new ParserException("While parsing airline text", e);
    }
  }
}
