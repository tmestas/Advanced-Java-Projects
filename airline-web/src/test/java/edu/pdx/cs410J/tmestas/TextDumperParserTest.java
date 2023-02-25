package edu.pdx.cs410J.tmestas;

import edu.pdx.cs410J.ParserException;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.Collections;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class TextDumperParserTest {

  @Test
  @Disabled
  void airlineWithOneFlightCanBeDumpedAndParsed() throws ParserException {
    String airlineName = "Alaska Airlines";
    int flightNumber = 123;
    Airline airline = new Airline(airlineName);
    airline.addFlight(new Flight(flightNumber));
    Airline read = dumpAndParse(airline);
    assertThat(read.getName(), equalTo(airlineName));
    assertThat(read.getFlights().iterator().next().getNumber(), equalTo(flightNumber));
  }

  private Airline dumpAndParse(Airline airline) throws ParserException {
    StringWriter sw = new StringWriter();
    TextDumper dumper = new TextDumper(sw);
    dumper.dump(airline);

    String text = sw.toString();

    TextParser parser = new TextParser(new StringReader(text));
    return parser.parse();
  }
}
