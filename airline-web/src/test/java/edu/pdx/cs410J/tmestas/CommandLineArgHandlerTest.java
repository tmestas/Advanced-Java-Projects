package edu.pdx.cs410J.tmestas;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

  @Test
  void testHandleArgsFlightNumNotInt(){
    CommandLineArgHandler test = new CommandLineArgHandler();
    String [] args = new String[]{"Airline Name", "notnum", "PDX", "10/22/2022", "10:00", "PM", "BOI", "10/22/2022", "11:00", "PM"};
    assertThrows(Exception.class, () -> test.handleArgs(args, 0));
  }
  @Test
  void testHandleArgsNoSearch1(){
    CommandLineArgHandler test = new CommandLineArgHandler();
    String [] args = new String[]{"Airline Name", "1234"};
    assertThrows(Exception.class, () -> test.handleArgs(args, 0));
  }
  @Test
  void testHandleArgsNoSearch2(){
    CommandLineArgHandler test = new CommandLineArgHandler();
    String [] args = new String[]{"Airline Name"};
    assertThrows(Exception.class, () -> test.handleArgs(args, 0));
  }
  @Test
  void testHandleArgsNoSearch3(){
    CommandLineArgHandler test = new CommandLineArgHandler();
    String [] args = new String[]{"Airline Name", "1234"};
    assertThrows(Exception.class, () -> test.handleArgs(args, 0));
  }
  @Test
  void testHandleArgsNoSearch4(){
    CommandLineArgHandler test = new CommandLineArgHandler();
    String [] args = new String[]{"Airline Name", "1234", "PDX"};
    assertThrows(Exception.class, () -> test.handleArgs(args, 0));
  }
  @Test
  void testHandleArgsNoSearch5(){
    CommandLineArgHandler test = new CommandLineArgHandler();
    String [] args = new String[]{"Airline Name", "1234", "PDX", "10/22/2022"};
    assertThrows(Exception.class, () -> test.handleArgs(args, 0));
  }
  @Test
  void testHandleArgsNoSearch6(){
    CommandLineArgHandler test = new CommandLineArgHandler();
    String [] args = new String[]{"Airline Name", "1234", "PDX", "10/22/2022", "10:00", "PM"};
    assertThrows(Exception.class, () -> test.handleArgs(args, 0));
  }

  @Test
  void testHandleArgsNoSearch7(){
    CommandLineArgHandler test = new CommandLineArgHandler();
    String [] args = new String[]{"Airline Name", "1234", "PDX", "10/22/2022", "10:00", "PM", "BOI"};
    assertThrows(Exception.class, () -> test.handleArgs(args, 0));
  }

  @Test
  void testHandleArgsNoSearch8(){
    CommandLineArgHandler test = new CommandLineArgHandler();
    String [] args = new String[]{"Airline Name", "1234", "PDX", "10/22/2022", "10:00", "PM", "BOI", "10/22/2022"};
    assertThrows(Exception.class, () -> test.handleArgs(args, 0));
  }

  @Test
  void testHandleArgsNoSearchGood(){
    CommandLineArgHandler test = new CommandLineArgHandler();
    String [] args = new String[]{"Airline Name", "1234", "PDX", "10/22/2022", "10:00", "PM", "BOI", "10/22/2022", "11:00", "PM"};
    try {
      test.handleArgs(args, 0);
    }catch(Exception e){
      System.out.println(e.getMessage());
    }
    assertThat(test.AirlineName, equalTo("Airline Name"));
    assertThat(test.FlightNumberAsString, equalTo("1234"));
    assertThat(test.Source, equalTo("PDX"));
    assertThat(test.DepartureDate, equalTo("10/22/2022"));
    assertThat(test.DepartureTime, equalTo("10:00 PM"));
    assertThat(test.Destination, equalTo("BOI"));
    assertThat(test.ArrivalDate, equalTo("10/22/2022"));
    assertThat(test.ArrivalTime, equalTo("11:00 PM"));
  }

  @Test
  void testHandleArgsCheckValidInputThrowsException(){
    CommandLineArgHandler test = new CommandLineArgHandler();
    String [] args = new String[]{"Airline Name", "1234", "PDX", "10/22/2022", " ", "PM", "BOI", "10/22/2022", "11:00", "PM"};
    assertThrows(Exception.class, () -> test.handleArgs(args, 0));
  }

  @Test
  void testHandleArgsMissingAirlineName(){
    CommandLineArgHandler test = new CommandLineArgHandler();
    String [] args = new String[]{};
    assertThrows(Exception.class, () -> test.handleArgs(args, 0));
  }

  @Test
  void testHandleArgsWithSearch1(){
    CommandLineArgHandler test = new CommandLineArgHandler();
    test.Search = true;
    String [] args = new String[]{};
    assertThrows(Exception.class, () -> test.handleArgs(args, 0));
  }

  @Test
  void testHandleArgsWithSearch2(){
    CommandLineArgHandler test = new CommandLineArgHandler();
    test.Search = true;
    String [] args = new String[]{"Airline Name"};
    try {
      test.handleArgs(args, 0);
    }catch(Exception e){
      System.out.println(e.getMessage());
    }
    assertThat(test.AirlineName, equalTo("Airline Name"));
    assertThat(test.Source, equalTo(null));
    assertThat(test.Destination, equalTo(null));
  }

  @Test
  void testHandleOptions1(){
    CommandLineArgHandler test = new CommandLineArgHandler();
    String [] args = new String[]{"-host"};

    assertThrows(Exception.class, () -> test.handleOptions(args));
  }

  @Test
  void testHandleOptions2(){
    CommandLineArgHandler test = new CommandLineArgHandler();
    String [] args = new String[]{"-host", "localhost"};

    assertThrows(Exception.class, () -> test.handleOptions(args));
  }

  @Test
  void testHandleOptions3(){
    CommandLineArgHandler test = new CommandLineArgHandler();
    String [] args = new String[]{"-host", "localhost", "-port"};

    assertThrows(Exception.class, () -> test.handleOptions(args));
  }

  @Test
  void testHandleOptions4(){
    CommandLineArgHandler test = new CommandLineArgHandler();
    String [] args = new String[]{"-host", "localhost", "-port", "8080"};
    try {
      test.handleOptions(args);
    }catch(Exception e){
      System.out.println(e.getMessage());
    }

    assertThat(test.HostName, equalTo("localhost"));
    assertThat(test.PortString, equalTo("8080"));
  }

  @Test
  void testHandleOptions5(){
    CommandLineArgHandler test = new CommandLineArgHandler();
    String [] args = new String[]{"-host", "localhost", "-port", "idk"};

    assertThrows(Exception.class, () -> test.handleOptions(args));
  }

  @Test
  void testHandleOptions6(){
    CommandLineArgHandler test = new CommandLineArgHandler();
    String [] args = new String[]{"-host", "localhost", "-port", "8080", "-README"};
    int value;
    try {
      value = test.handleOptions(args);
    }catch(Exception e){
      System.out.println(e.getMessage());
      return;
    }
    assertThat(value, equalTo(-1));
  }

  @Test
  void testHandleOptions7(){
    CommandLineArgHandler test = new CommandLineArgHandler();
    String [] args = new String[]{"-host", "localhost", "-port", "8080", "-search"};
    try {
      test.handleOptions(args);
    }catch(Exception e){
      System.out.println(e.getMessage());
    }

    assertThat(test.HostName, equalTo("localhost"));
    assertThat(test.PortString, equalTo("8080"));
    assertThat(test.Search, equalTo(true));
  }

  @Test
  void testHandleOptions8(){
    CommandLineArgHandler test = new CommandLineArgHandler();
    String [] args = new String[]{"-host", "localhost", "-port", "8080", "-print"};
    try {
      test.handleOptions(args);
    }catch(Exception e){
      System.out.println(e.getMessage());
    }

    assertThat(test.HostName, equalTo("localhost"));
    assertThat(test.PortString, equalTo("8080"));
    assertThat(test.Print, equalTo(true));
  }

  @Test
  void testParseGood(){
    CommandLineArgHandler test = new CommandLineArgHandler();
    String [] args = new String[]{"-host", "localhost", "-port", "8080", "Airline Name", "1234", "PDX", "10/22/2022",
            "10:00", "PM", "BOI", "10/22/2022", "11:00", "PM"};
    try {
      test.parse(args);
    }catch(Exception e){
      System.out.println(e.getMessage());
    }
  }

  @Test
  void testParseReadmeTrue(){
    CommandLineArgHandler test = new CommandLineArgHandler();
    String [] args = new String[]{"-host", "localhost", "-port", "8080", "-README", "Airline Name", "1234", "PDX", "10/22/2022",
            "10:00", "PM", "BOI", "10/22/2022", "11:00", "PM"};
    try {
      test.parse(args);
    }catch(Exception e){
      System.out.println(e.getMessage());
    }

    assertThat(test.Readme, equalTo(true));
  }




}
