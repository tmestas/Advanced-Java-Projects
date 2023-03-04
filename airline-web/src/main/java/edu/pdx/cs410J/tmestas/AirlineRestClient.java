package edu.pdx.cs410J.tmestas;

import com.google.common.annotations.VisibleForTesting;
import edu.pdx.cs410J.ParserException;
import edu.pdx.cs410J.web.HttpRequestHelper;

import java.io.IOException;
import java.io.StringReader;
import java.util.Map;

import static edu.pdx.cs410J.web.HttpRequestHelper.Response;
import static edu.pdx.cs410J.web.HttpRequestHelper.RestException;
import static java.net.HttpURLConnection.*;

/**
 * A helper class for accessing the rest client.
 */
public class AirlineRestClient
{
    private static final String WEB_APP = "airline";
    private static final String SERVLET = "flights";

    private final HttpRequestHelper http;


    /**
     * Creates a client to the airline REST service running on the given host and port
     * @param hostName The name of the host
     * @param port The port
     */
    public AirlineRestClient( String hostName, int port )
    {
        this(new HttpRequestHelper(String.format("http://%s:%d/%s/%s", hostName, port, WEB_APP, SERVLET)));
    }

    @VisibleForTesting
    AirlineRestClient(HttpRequestHelper http) {
      this.http = http;
    }

  /**
   * Returns the definition for the given word
   */

  public Airline getAirline(String airlineName) throws IOException, ParserException{
      Response response;
      try {
          response = http.get(Map.of(AirlineServlet.AIRLINE_NAME_PARAMETER, airlineName));
      }catch(IOException e){
          throw new IOException(e.getMessage());
      }

      throwExceptionIfNotOkayHttpStatus(response);
      String content = response.getContent();
      XmlParser parser = new XmlParser(new StringReader(content));
      return parser.parse();
  }

    /**
     * gets flights between 2 airports
     * @param airlineName airline to search for flights in
     * @param sourceAirport source airport
     * @param destinationAirport destination airport
     * @return airline object containing flights
     * @throws Exception for no flights found, or airline not found
     */
  public Airline getFlightsBetween(String airlineName, String sourceAirport, String destinationAirport) throws Exception {

      Response response = http.get(Map.of(AirlineServlet.AIRLINE_NAME_PARAMETER, airlineName, AirlineServlet.SOURCE_PARAMETER,
              sourceAirport, AirlineServlet.DESTINATION_PARAMETER, destinationAirport));


      throwExceptionIfNotOkayHttpStatus(response); //how to get message from this exception?

      String content = response.getContent();
      XmlParser parser = new XmlParser(new StringReader(content));
      return parser.parse();
  }

    /**
     *
     * @param airlineName name of airline to add flight to
     * @param flight flight object to add
     * @throws IOException for error with IO
     */
  public void addFlight(String airlineName, Flight flight) throws IOException {
    Response response = http.post(Map.of(AirlineServlet.AIRLINE_NAME_PARAMETER, airlineName,
            AirlineServlet.FLIGHT_NUMBER_PARAMETER, String.valueOf(flight.getNumber()),
            AirlineServlet.SOURCE_PARAMETER, flight.getSource(),
            AirlineServlet.DEPART_PARAMETER, flight.getDepartureDateTimeString(),
            AirlineServlet.DESTINATION_PARAMETER, flight.getDestination(),
            AirlineServlet.ARRIVAL_PARAMETER, flight.getArrivalDateTimeString()));

    System.out.println(response.getContent());
    throwExceptionIfNotOkayHttpStatus(response);
  }

  public void removeAllAirlines() throws IOException {
    Response response = http.delete(Map.of());
    throwExceptionIfNotOkayHttpStatus(response);
  }

    /**
     * throws an exception if the http status is not OK
     * @param response http response containing status and message
     */
  private void throwExceptionIfNotOkayHttpStatus(Response response){
    int code = response.getHttpStatusCode();
    if (code != HTTP_OK) {
      String message = response.getContent();
      throw new RestException(code, message);
    }

  }

}
