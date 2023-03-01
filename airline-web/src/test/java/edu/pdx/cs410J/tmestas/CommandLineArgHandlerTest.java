package edu.pdx.cs410J.tmestas;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * A unit test for code in the <code>Project1</code> class.  This is different
 * from <code>Project1IT</code> which is an integration test (and can capture data
 * written to {@link System#out} and the like.
 */
class CommandLineArgHandlerTest {

  @Test
  @Disabled
  void readmeCanBeReadAsResource() throws IOException {
    try (
      InputStream readme = Project5.class.getResourceAsStream("readme.txt")
    ) {
      assertThat(readme, not(nullValue()));
      BufferedReader reader = new BufferedReader(new InputStreamReader(readme));
      String line = reader.readLine();
      assertThat(line, containsString(""));
    }
  }

  @Test
  void testIsValidTimeWithGoodInput1Digit(){
    CommandLineArgHandler test = new CommandLineArgHandler();
    boolean value = test.isValidTime("9:30 PM");
    assertThat(value, equalTo(true));
  }

  @Test
  void testIsValidTimeWithGoodInput2Digits(){
    CommandLineArgHandler test = new CommandLineArgHandler();
    boolean value = test.isValidTime("10:30 PM");
    assertThat(value, equalTo(true));
  }

  @Test
  void testIsValidTimeWithBadInput(){
    CommandLineArgHandler test = new CommandLineArgHandler();
    boolean value = test.isValidTime("030");
    assertThat(value, equalTo(false));
  }

  @Test
  void testIsValidDateWithGoodInput(){
    CommandLineArgHandler test = new CommandLineArgHandler();
    boolean value = test.isValidDate("10/22/2022");
    assertThat(value, equalTo(true));
  }

  @Test
  void testIsValidDateWithBadInput(){
    CommandLineArgHandler test = new CommandLineArgHandler();
    boolean value = test.isValidDate("100/22/2022");
    assertThat(value, equalTo(false));
  }

  @Test
  void testIsValidAirportCodeWithGoodInput(){
    CommandLineArgHandler test = new CommandLineArgHandler();
    boolean value = test.isValidAirportCode("PDX");
    assertThat(value, equalTo(true));
  }

  @Test
  void testIsValidAirportCodeWithBadInput(){
    CommandLineArgHandler test = new CommandLineArgHandler();
    boolean value = test.isValidAirportCode("PD1");
    assertThat(value, equalTo(false));
  }

  @Test
  void testIsValidAirportCodeWithBadInput2(){
    CommandLineArgHandler test = new CommandLineArgHandler();
    boolean value = test.isValidAirportCode("");
    assertThat(value, equalTo(false));
  }

  @Test
  void testCheckValidInputWithGoodInput(){
    String message = "nothing";
    CommandLineArgHandler test = new CommandLineArgHandler();
    try {
      test.checkValidInput("PDX", "10:20 PM", "10/22/2022",
              "BOI", "10:24 PM", "10/22/2022");
    }catch(Exception e){
      message = e.getMessage();
    }

    assertThat(message, equalTo("nothing"));
  }

  @Test
  void testCheckValidInputWithBadArrivalTime(){
    String message = null;
    CommandLineArgHandler test = new CommandLineArgHandler();
    try {
      test.checkValidInput("PDX", "10:20 PM", "10/22/2022",
              "BOI", "10:242 PM", "10/22/2022");
    }catch(Exception e) {
      message = e.getMessage();
    }

    assertThat(message, equalTo("\n10:242 PM is not a valid arrival time.\nValid Format: hh:mm\n"));
  }

  @Test
  void testCheckValidInputWithBadDepartureTime(){
    String message = null;
    CommandLineArgHandler test = new CommandLineArgHandler();
    try {
      test.checkValidInput("PDX", "10:242 PM", "10/22/2022",
              "BOI", "11:24 PM", "10/22/2022");
    }catch(Exception e) {
      message = e.getMessage();
    }

    assertThat(message, equalTo("\n10:242 PM is not a valid departure time.\nValid Format: hh:mm\n"));
  }

  @Test
  void testCheckValidInputWithBadSourceCode(){
    String message = null;
    CommandLineArgHandler test = new CommandLineArgHandler();
    try {
      test.checkValidInput("PD", "10:24 PM", "10/22/2022",
              "BOI", "11:24 PM", "10/22/2022");
    }catch(Exception e) {
      message = e.getMessage();
    }

    assertThat(message, equalTo("\nAirport Code must be 3 letters and no other characters\n"));
  }

  @Test
  void testCheckValidInputWithUnknownSourceCode(){
    String message = null;
    CommandLineArgHandler test = new CommandLineArgHandler();
    try {
      test.checkValidInput("AYO", "10:24 PM", "10/22/2022",
              "BOI", "11:24 PM", "10/22/2022");
    }catch(Exception e) {
      message = e.getMessage();
    }

    assertThat(message, equalTo("\nAYO is not a known airport, exiting"));
  }

  @Test
  void testCheckValidInputWithUnknownDestinationCode(){
    String message = null;
    CommandLineArgHandler test = new CommandLineArgHandler();
    try {
      test.checkValidInput("BOI", "10:24 PM", "10/22/2022",
              "AYO", "11:24 PM", "10/22/2022");
    }catch(Exception e) {
      message = e.getMessage();
    }

    assertThat(message, equalTo("\nAYO is not a known airport, exiting"));
  }

  @Test
  void testCheckValidInputWithBadDepartureDate(){
    String message = null;
    CommandLineArgHandler test = new CommandLineArgHandler();
    try {
      test.checkValidInput("PDX", "10:24 PM", "80/22/2022",
              "BOI", "11:24 PM", "10/22/2022");
    }catch(Exception e) {
      message = e.getMessage();
    }

    assertThat(message, equalTo("\n80/22/2022 is not a valid departure date.\nValid Format: mm/dd/yyyy\n"));
  }

  @Test
  void testCheckValidInputWithBadArrivalDate(){
    String message = null;
    CommandLineArgHandler test = new CommandLineArgHandler();
    try {
      test.checkValidInput("PDX", "10:24 PM", "10/22/2022",
              "BOI", "11:24 PM", "80/22/2022");
    }catch(Exception e) {
      message = e.getMessage();
    }

    assertThat(message, equalTo("\n80/22/2022 is not a valid arrival date.\nValid Format: mm/dd/yyyy\n"));
  }

  @Test
  void testCheckValidInputWithDepartAfterArrive(){
    String message = null;
    CommandLineArgHandler test = new CommandLineArgHandler();
    try {
      test.checkValidInput("PDX", "11:24 PM", "10/22/2022",
              "BOI", "9:24 PM", "10/22/2022");
    }catch(Exception e) {
      message = e.getMessage();
    }

    assertThat(message, equalTo("\nDeparture time is after arrival time, exiting\n"));
  }



  @Test
  void testDoesAirportExistYes(){
    CommandLineArgHandler test = new CommandLineArgHandler();
    boolean value = test.doesAirportCodeExist("PDX");
    assertThat(value, equalTo(true));
  }

  @Test
  void testDoesAirportExistNo(){
    CommandLineArgHandler test = new CommandLineArgHandler();
    boolean value = test.doesAirportCodeExist("OOO");
    assertThat(value, equalTo(false));

  }

  @Test
  void testIsDepartureTimeNotAfterArrivalTimeYes(){
    CommandLineArgHandler test = new CommandLineArgHandler();
    String depart = "10/22/2022 10:30 PM";
    String arrive = "10/23/2022 10:30 PM";
    assertThat(test.isDepartureTimeNotAfterArrivalTime(depart, arrive), equalTo(true));
  }

  @Test
  void testIsDepartureTimeNotAfterArrivalTimeNo(){
    CommandLineArgHandler test = new CommandLineArgHandler();
    String depart = "10/23/2022 10:30 PM";
    String arrive = "10/22/2022 10:30 PM";
    assertThat(test.isDepartureTimeNotAfterArrivalTime(depart, arrive), equalTo(false));
  }



}
