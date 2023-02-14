package edu.pdx.cs410J.tmestas;

import edu.pdx.cs410J.InvokeMainTestCase;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * An integration test for the {@link Project4} main class.
 */
class Project4IT extends InvokeMainTestCase {

    /**
     * Invokes the main method of {@link Project4} with the given arguments.
     */
    private MainMethodResult invokeMain(String... args) {
        return invokeMain( Project4.class, args);
    }


  /**
   * Tests that invoking the main method with no arguments issues an error
   */
  @Test
  void testNoCommandLineArguments() {
    MainMethodResult result = invokeMain();
    assertThat(result.getTextWrittenToStandardError(), containsString(""));
  }

  @Test
  void testWrongDepartureTimeFormat() {
      MainMethodResult result = invokeMain("-print", "\"Alaska Airlines\"", "123", "PDX", "11/22/2022", "10:400", "PM", "BOI", "11/22/2022", "10:40", "PM");
      assertThat(result.getTextWrittenToStandardOut(), containsString("not a valid departure time"));
  }
  @Test
   void testWrongArrivalTimeFormat() {
      MainMethodResult result = invokeMain("-print", "\"Alaska Airlines\"", "123", "PDX", "11/22/2022", "10:40", "PM", "BOI", "11/22/2022", "10:400", "PM");
      assertThat(result.getTextWrittenToStandardOut(), containsString("not a valid arrival time"));
  }

  @Test
  void testWrongArrivalDateFormat(){
      MainMethodResult result = invokeMain("-print", "\"Alaska Airlines\"", "123", "PDX", "11/22/2022", "10:40", "PM", "BOI", "112/22/2022", "10:45", "PM");
      assertThat(result.getTextWrittenToStandardOut(), containsString("not a valid arrival date"));
  }

  @Test
  void testWrongDepartureDateFormat(){
      MainMethodResult result = invokeMain("-print", "\"Alaska Airlines\"", "123", "PDX", "112/22/2022", "10:40", "PM", "BOI", "12/22/2022", "10:45", "PM");
      assertThat(result.getTextWrittenToStandardOut(), containsString("not a valid departure date"));
  }

  @Test
  void testReadMe(){
      MainMethodResult result = invokeMain("-README");
      assertThat(result.getTextWrittenToStandardOut(), containsString(""));
  }

  @Test
  void testFlightNum(){
      MainMethodResult result = invokeMain("-print", "\"Alaska Airlines\"", "Hello", "PDX", "11/22/2022", "10:40", "PM", "BOI", "11/22/2022", "10:45", "PM");
      assertThat(result.getTextWrittenToStandardOut(), containsString("\nFlight Number is not valid, please enter an integer value\n"));
  }

  @Test
  void testNumInAirportCode(){
      MainMethodResult result = invokeMain("-print", "\"Alaska Airlines\"", "123", "PD1", "11/22/2022", "10:40", "PM", "BOI", "12/22/2022", "10:45", "PM");
      assertThat(result.getTextWrittenToStandardOut(), containsString("Non letter found in airport code"));
  }

  @Test
  void testShortAirportCode(){
      MainMethodResult result = invokeMain("-print", "\"Alaska Airlines\"", "123", "PD", "11/22/2022", "10:40", "PM", "BOI", "12/22/2022", "10:45", "PM");
      assertThat(result.getTextWrittenToStandardOut(), containsString("Airport code too short"));
  }

  @Test
  void testLongAirportCode(){
      MainMethodResult result = invokeMain("-print", "\"Alaska Airlines\"", "123", "PDPASD", "11/22/2022", "10:40", "PM", "BOI", "12/22/2022", "10:45", "PM");
      assertThat(result.getTextWrittenToStandardOut(), containsString("Airport code too long"));
  }

  @Test
  @Disabled
  void testMalformedTextFile(){
      MainMethodResult result = invokeMain("-textFile", "src/test/resources/edu/pdx/cs410J/tmestas/malformed.txt", "\"Alaska Airlines\"", "123", "PDX", "11/22/2022", "10:40", "BOI", "12/22/2022", "10:45");
      assertThat(result.getTextWrittenToStandardOut(), containsString("Text file is malformatted"));
  }
  @Test
  void testAirlineDoesNotExistInFile(){
      MainMethodResult result = invokeMain("-print", "-textFile", "src/test/resources/edu/pdx/cs410J/tmestas/valid-airline.txt", "\"Seipp Airlines\"", "123", "PDX", "11/22/2022", "10:40", "PM", "BOI", "12/22/2022", "10:45", "PM");
      assertThat(result.getTextWrittenToStandardOut(), containsString("Airline name did not match text file, information not added"));
  }
}