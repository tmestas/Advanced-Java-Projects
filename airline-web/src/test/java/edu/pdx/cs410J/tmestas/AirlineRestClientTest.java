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
  @Disabled
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
