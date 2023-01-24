package edu.pdx.cs410J.tmestas;

import edu.pdx.cs410J.InvokeMainTestCase;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * An integration test for the {@link Project1} main class.
 */
class Project1IT extends InvokeMainTestCase {

    /**
     * Invokes the main method of {@link Project1} with the given arguments.
     */
    private MainMethodResult invokeMain(String... args) {
        return invokeMain( Project1.class, args);
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
  void testWrongTimeFormat() {
      MainMethodResult result = invokeMain("-print", "\"Alaska Airlines\"", "123", "PDX", "1024:40", "11/22/2022", "BOI", "10:45", "112/22/2022");
      assertThat(result.getTextWrittenToStandardOut(), containsString("Invalid Time"));
  }

  @Test
  void testWrongDateFormat(){
      MainMethodResult result = invokeMain("-print", "\"Alaska Airlines\"", "123", "PDX", "10:40", "11/22/2022", "BOI", "10:45", "112/22/2022");
      assertThat(result.getTextWrittenToStandardOut(), containsString("Invalid Date"));
  }

  @Test
  void testReadMe(){
      MainMethodResult result = invokeMain("-README");
      assertThat(result.getTextWrittenToStandardOut(), containsString(""));
  }

  @Test
  void testFlightNum(){
      MainMethodResult result = invokeMain("-print", "\"Alaska Airlines\"", "Hello", "PDX", "10:40", "11/22/2022", "BOI", "10:45", "112/22/2022");
      assertThat(result.getTextWrittenToStandardOut(), containsString("\nFlight Number is not valid, please enter an integer value\n"));
  }

  @Test
  void testAirportCode(){
      MainMethodResult result = invokeMain("-print", "\"Alaska Airlines\"", "123", "PD1", "10:40", "11/22/2022", "BOI", "10:45", "12/22/2022");
      assertThat(result.getTextWrittenToStandardOut(), containsString("Invalid Airport Code"));
  }

}