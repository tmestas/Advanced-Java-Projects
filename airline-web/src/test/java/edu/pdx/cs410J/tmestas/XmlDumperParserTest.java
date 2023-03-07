package edu.pdx.cs410J.tmestas;

import edu.pdx.cs410J.ParserException;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.StringReader;
import java.io.StringWriter;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class XmlDumperParserTest {

    @Test
    void airlineWithOneFlightCanBeDumpedAndParsed() throws ParserException {
        String airlineName = "Alaska Airlines";
        int flightNumber = 123;
        String src = "PDX";
        String depart = "1/23/2022 10:40 PM";
        String dest = "BOI";
        String arrive = "1/23/2022 11:40 PM";

        Airline airline = new Airline(airlineName);
        airline.addFlight(new Flight(flightNumber, src, depart, dest, arrive));
        Airline read = dumpAndParse(airline);
        assertThat(read.getName(), equalTo(airlineName));
        assertThat(read.getFlights().iterator().next().getNumber(), equalTo(flightNumber));
    }

    private Airline dumpAndParse(Airline airline) throws ParserException {
        StringWriter sw = new StringWriter();
        XmlDumper dumper = new XmlDumper(sw);

        try {
            dumper.dump(airline);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }

        String text = sw.toString();

        XmlParser parser = new XmlParser(new StringReader(text));
        return parser.parse();
    }
}
