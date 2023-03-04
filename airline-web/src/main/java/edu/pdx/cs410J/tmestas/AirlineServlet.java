package edu.pdx.cs410J.tmestas;

import com.google.common.annotations.VisibleForTesting;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * This servlet ultimately provides a REST API for working with an
 * <code>Airline</code>.
 */
public class AirlineServlet extends HttpServlet {
  static final String AIRLINE_NAME_PARAMETER = "airline";
  static final String FLIGHT_NUMBER_PARAMETER = "flightNumber";
  static final String SOURCE_PARAMETER = "source";
  static final String DEPART_PARAMETER = "depart";
  static final String DESTINATION_PARAMETER = "destination";
  static final String ARRIVAL_PARAMETER = "arrive";

  private final Map<String, Airline> airlines = new HashMap<>();

  /**
   * Handles an HTTP GET request from a client by writing the definition of the
   * word specified in the "word" HTTP parameter to the HTTP response.  If the
   * "word" parameter is not specified, all of the entries in the dictionary
   * are written to the HTTP response.
   */
  @Override
  protected void doGet( HttpServletRequest request, HttpServletResponse response ) throws IOException
  {
      response.setContentType( "text/plain" );

      String airlineName = getParameter(AIRLINE_NAME_PARAMETER, request );
      String source = getParameter(SOURCE_PARAMETER, request);           //will be null if not passed in
      String destination = getParameter(DESTINATION_PARAMETER, request); //will be null if not passed in

      if (airlineName != null && (source == null || destination == null)) {
          writeAirline(airlineName, response);
      }
      else if(airlineName != null && source != null && destination != null){

          writeFlights(airlineName, source, destination, response);

      }else{
          throw new IOException("Airline name is required");
      }
  }

  /**
   * Handles an HTTP POST request by storing the dictionary entry for the
   * "word" and "definition" request parameters.  It writes the dictionary
   * entry to the HTTP response.
   */
  @Override
  protected void doPost( HttpServletRequest request, HttpServletResponse response ) throws IOException
  {
      response.setContentType( "text/plain" );

      String airlineName = getParameter(AIRLINE_NAME_PARAMETER, request );
      if (airlineName == null) {
          missingRequiredParameter(response, AIRLINE_NAME_PARAMETER);
          return;
      }

      String flightNumberAsString = getParameter(FLIGHT_NUMBER_PARAMETER, request );
      if ( flightNumberAsString == null) {
          missingRequiredParameter( response, FLIGHT_NUMBER_PARAMETER);
          return;
      }

      String source = getParameter(SOURCE_PARAMETER, request );
      if (source == null) {
          missingRequiredParameter(response, SOURCE_PARAMETER);
          return;
      }

      String departureDateTime = getParameter(DEPART_PARAMETER, request );
      if (departureDateTime == null) {
          missingRequiredParameter(response, DEPART_PARAMETER);
          return;
      }

      String destination = getParameter(DESTINATION_PARAMETER, request );
      if (destination == null) {
          missingRequiredParameter(response, DESTINATION_PARAMETER);
          return;
      }

      String arrivalDateTime = getParameter(ARRIVAL_PARAMETER, request);
      if (arrivalDateTime == null) {
          missingRequiredParameter(response, ARRIVAL_PARAMETER);
          return;
      }

      Airline airline = this.airlines.get(airlineName);
      if(airline == null){
          airline = new Airline(airlineName);
          this.airlines.put(airlineName, airline);
      }

      airline.addFlight(new Flight(Integer.parseInt(flightNumberAsString), source, departureDateTime, destination, arrivalDateTime));

      PrintWriter pw = response.getWriter();
      pw.println(Messages.addedFlightToAirline(airlineName, flightNumberAsString));
      pw.flush();

      response.setStatus( HttpServletResponse.SC_OK);
  }

  /**
   * Handles an HTTP DELETE request by removing all dictionary entries.  This
   * behavior is exposed for testing purposes only.  It's probably not
   * something that you'd want a real application to expose.
   */
  @Override
  protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
      response.setContentType("text/plain");

      this.airlines.clear();

      PrintWriter pw = response.getWriter();
      pw.println(Messages.allDictionaryEntriesDeleted());
      pw.flush();

      response.setStatus(HttpServletResponse.SC_OK);
  }

  /**
   * Writes an error message about a missing parameter to the HTTP response.
   *
   * The text of the error message is created by {@link Messages#missingRequiredParameter(String)}
   */
  private void missingRequiredParameter( HttpServletResponse response, String parameterName )
      throws IOException
  {
      String message = Messages.missingRequiredParameter(parameterName);
      response.sendError(HttpServletResponse.SC_PRECONDITION_FAILED, message);
  }

  /**
   * Writes the definition of the given airline to the HTTP response.
   * The text of the message is formatted with {@link XmlDumper}
   */
  private void writeAirline(String airlineName, HttpServletResponse response) throws IOException {
    Airline airline = this.airlines.get(airlineName);

    if (airline == null) {
        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        PrintWriter pw = response.getWriter();
        pw.write(airlineName + " does not exist");
    } else {
      PrintWriter pw = response.getWriter();
      XmlDumper dumper = new XmlDumper(pw);
      dumper.dump(airline);
      pw.flush();
      response.setStatus(HttpServletResponse.SC_OK);
    }
  }

    /**
     * writes flights that match criteria to a new airline object
     * @param airlineName airline to search for
     * @param source source airport to search for
     * @param destination destination airport to search for
     * @param response http response containing info returned or an error message
     * @throws IOException for IO issues
     */
  private void writeFlights(String airlineName, String source, String destination, HttpServletResponse response)throws
          IOException{
      Airline airline = this.airlines.get(airlineName);
      Airline newAirline = new Airline(airlineName);

      if (airline == null) {
          response.setStatus(HttpServletResponse.SC_NOT_FOUND);
          PrintWriter pw = response.getWriter();
          pw.write(airlineName + " does not exist");
      }
      else {
          PrintWriter pw = response.getWriter();
          XmlDumper dumper = new XmlDumper(pw);

          for(Flight f: airline.getFlights()){
              if(f.getSource().equals(source) && f.getDestination().equals(destination)){
                  newAirline.addFlight(f);
              }
          }

          if(newAirline.getFlights().size() > 0) {
              dumper.dump(newAirline);
              pw.flush();
              response.setStatus(HttpServletResponse.SC_OK);
          }else{
              response.setStatus(HttpServletResponse.SC_NOT_FOUND);
              pw.write(airlineName + " does not contain any flights between " + source + " and " + destination);
          }
      }
  }


  /**
   * Returns the value of the HTTP request parameter with the given name.
   * @return <code>null</code> if the value of the parameter is
   *         <code>null</code> or is the empty string
   */
  private String getParameter(String name, HttpServletRequest request) {
    String value = request.getParameter(name);
    if (value == null || "".equals(value)) {
      return null;

    } else {
      return value;
    }
  }

    /**
     * gets airline object
     * @param airlineName airline to get
     * @return airline object
     */
  @VisibleForTesting
  Airline getAirline(String airlineName) {
      return this.airlines.get(airlineName);
  }
}
