package edu.pdx.cs410J.tmestas;

import edu.pdx.cs410J.InvokeMainTestCase;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * An integration test for the {@link Project2} main class.
 */
class Project2IT extends InvokeMainTestCase {

    /**
     * Invokes the main method of {@link Project2} with the given arguments.
     */
    private MainMethodResult invokeMain(String... args) {
        return invokeMain( Project2.class, args);
    }


  /**
   * Tests that invoking the main method with no arguments issues an error
   */
  @Test
  void testNoCommandLineArguments() {
    MainMethodResult result = invokeMain();
    assertThat(result.getTextWrittenToStandardError(), containsString("\n\nNOT ENOUGH ARGUMENTS INCLUDED\n" +
            "\nUSAGE:\njava -jar target/airline-2023.0.0.jar [options] \"Airline Name\" " +
            "FlightNumber Source DepartureTime DepartureDate Destination ArrivalTime ArrivalDate"));
  }

  @Test
  void testWrongDepartureTimeFormat() {
      MainMethodResult result = invokeMain("-print", "\"Alaska Airlines\"", "123", "PDX", "11/22/2022", "1024:40", "BOI", "112/22/2022", "10:45");
      assertThat(result.getTextWrittenToStandardOut(), containsString("not a valid departure time"));
  }
  @Test
   void testWrongArrivalTimeFormat() {
      MainMethodResult result = invokeMain("-print", "\"Alaska Airlines\"", "123", "PDX", "11/22/2022", "10:45", "BOI", "112/22/2022", "1024:40");
      assertThat(result.getTextWrittenToStandardOut(), containsString("not a valid arrival time"));
  }

  @Test
  void testWrongArrivalDateFormat(){
      MainMethodResult result = invokeMain("-print", "\"Alaska Airlines\"", "123", "PDX", "11/22/2022", "10:40", "BOI", "112/22/2022", "10:45");
      assertThat(result.getTextWrittenToStandardOut(), containsString("not a valid arrival date"));
  }

  @Test
  void testWrongDepartureDateFormat(){
      MainMethodResult result = invokeMain("-print", "\"Alaska Airlines\"", "123", "PDX", "112/22/2022", "10:40", "BOI", "12/22/2022", "10:45");
      assertThat(result.getTextWrittenToStandardOut(), containsString("not a valid departure date"));
  }

  @Test
  void testReadMe(){
      MainMethodResult result = invokeMain("-README");
      assertThat(result.getTextWrittenToStandardOut(), containsString(""));
  }

  @Test
  void testFlightNum(){
      MainMethodResult result = invokeMain("-print", "\"Alaska Airlines\"", "Hello", "PDX", "11/22/2022", "10:40", "BOI", "112/22/2022", "10:45");
      assertThat(result.getTextWrittenToStandardOut(), containsString("\nFlight Number is not valid, please enter an integer value\n"));
  }

  @Test
  void testNumInAirportCode(){
      MainMethodResult result = invokeMain("-print", "\"Alaska Airlines\"", "123", "PD1", "11/22/2022", "10:40", "BOI", "12/22/2022", "10:45");
      assertThat(result.getTextWrittenToStandardOut(), containsString("Non letter found in airport code"));
  }

  @Test
  void testShortAirportCode(){
      MainMethodResult result = invokeMain("-print", "\"Alaska Airlines\"", "123", "PD", "11/22/2022", "10:40", "BOI", "12/22/2022", "10:45");
      assertThat(result.getTextWrittenToStandardOut(), containsString("Airport code too short"));
  }

  @Test
  void testLongAirportCode(){
      MainMethodResult result = invokeMain("-print", "\"Alaska Airlines\"", "123", "PDPASD", "11/22/2022", "10:40", "BOI", "12/22/2022", "10:45");
      assertThat(result.getTextWrittenToStandardOut(), containsString("Airport code too long"));
  }

}