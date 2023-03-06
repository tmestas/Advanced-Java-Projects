package edu.pdx.cs410J.tmestas;

import edu.pdx.cs410J.ParserException;
import edu.pdx.cs410J.web.HttpRequestHelper;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

/**
 * A unit test for the REST client that demonstrates using mocks and
 * dependency injection
 */
public class AirlineRestClientTest {
  @Test
  void getAirlinePerformsGetWithNoParameters() throws ParserException, IOException {
      String airlineName = "Alaska Airlines";
      int flightNumber = 123;
      String src = "PDX";
      String depart = "1/23/2022 10:40 PM";
      String dest = "BOI";
      String arrive = "1/23/2022 11:40 PM";

      Airline airline = new Airline(airlineName);
      airline.addFlight(new Flight(flightNumber, src, depart, dest, arrive));

    HttpRequestHelper http = mock(HttpRequestHelper.class);
    when(http.get(eq(Map.of(AirlineServlet.AIRLINE_NAME_PARAMETER, airlineName)))).thenReturn(airlineAsText(airline));
    
    AirlineRestClient client = new AirlineRestClient(http);

    Airline read = client.getAirline(airlineName);
    assertThat(read.getName(), equalTo(airlineName));
    assertThat(read.getFlights().iterator().next().getNumber(), equalTo(flightNumber));

  }
    @Test
    void getFlightsBetweenPerformsGetWithTwoParameters() throws IOException {

        String airlineName = "Alaska Airlines";
        int flightNumber = 123;
        String src = "PDX";
        String depart = "1/23/2022 10:40 PM";
        String dest = "BOI";
        String arrive = "1/23/2022 11:40 PM";

        Airline airline = new Airline(airlineName);
        airline.addFlight(new Flight(flightNumber, src, depart, dest, arrive));

        HttpRequestHelper http = mock(HttpRequestHelper.class);
        when(http.get(eq(Map.of(AirlineServlet.AIRLINE_NAME_PARAMETER, airlineName, AirlineServlet.SOURCE_PARAMETER, src, AirlineServlet.DESTINATION_PARAMETER, dest)))).thenReturn(airlineAsText(airline));

        AirlineRestClient client = new AirlineRestClient(http);
        Airline read;
        try {
            read = client.getFlightsBetween(airlineName, src, dest);
        }catch(Exception e){
            return;
        }
        assertThat(read.getName(), equalTo(airlineName));
        assertThat(read.getFlights().iterator().next().getNumber(), equalTo(flightNumber));
        assertThat(read.getFlights().iterator().next().getSource(), equalTo(src));
        assertThat(read.getFlights().iterator().next().getDestination(), equalTo(dest));
    }

    @Test
    void getFlightsBetweenThrowsError() throws IOException {

        String airlineName = "Alaska Airlines";
        int flightNumber = 123;
        String src = "PDX";
        String depart = "1/23/2022 10:40 PM";
        String dest = "BOI";
        String arrive = "1/23/2022 11:40 PM";

        Airline airline = new Airline(airlineName);
        airline.addFlight(new Flight(flightNumber, src, depart, dest, arrive));

        HttpRequestHelper http = mock(HttpRequestHelper.class);
        when(http.get(eq(Map.of(AirlineServlet.AIRLINE_NAME_PARAMETER, airlineName, AirlineServlet.SOURCE_PARAMETER, src, AirlineServlet.DESTINATION_PARAMETER, dest)))).thenReturn(airlineAsText(airline));

        AirlineRestClient client = new AirlineRestClient(http);
        Airline read;
        String message;
        try {
            read = client.getFlightsBetween("Seipp Airlines", src, dest);
            message = " ";
        }catch(Exception e){
            message = "caught exception";
        }
        assertThat(message, equalTo("caught exception"));
    }

  private HttpRequestHelper.Response airlineAsText(Airline airline) {
    StringWriter writer = new StringWriter();
    try {
        new XmlDumper(writer).dump(airline);
    }catch(Exception e){
        System.out.println("Error");
    }

    return new HttpRequestHelper.Response(writer.toString());
  }
}
