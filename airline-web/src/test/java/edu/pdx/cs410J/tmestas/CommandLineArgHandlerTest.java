package edu.pdx.cs410J.tmestas;

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

/*
  @Test
  void readmeCanBeReadAsResource() throws IOException {
    try (
      InputStream readme = Project5.class.getResourceAsStream("README.txt")
    ) {
      assertThat(readme, not(nullValue()));
      BufferedReader reader = new BufferedReader(new InputStreamReader(readme));
      String line = reader.readLine();
      assertThat(line, containsString(""));
    }
  }

  @Test
  void testIsValidTimeWithGoodInput1Digit(){
    Project4 test = new Project4();
    boolean value = test.isValidTime("1:30 PM");
    assertThat(value, equalTo(true));
  }

  @Test
  void testIsValidTimeWithGoodInput2Digits(){
    Project4 test = new Project4();
    boolean value = test.isValidTime("10:30 PM");
    assertThat(value, equalTo(true));
  }

  @Test
  void testIsValidTimeWithBadInput(){
    Project4 test = new Project4();
    boolean value = test.isValidTime("030");
    assertThat(value, equalTo(false));
  }

  @Test
  void testIsValidDateWithGoodInput(){
    Project4 test = new Project4();
    boolean value = test.isValidDate("10/22/2022");
    assertThat(value, equalTo(true));
  }

  @Test
  void testIsValidDateWithBadInput(){
    Project4 test = new Project4();
    boolean value = test.isValidDate("100/22/2022");
    assertThat(value, equalTo(false));
  }

  @Test
  void testIsValidAirportCodeWithGoodInput(){
    Project4 test = new Project4();
    boolean value = test.isValidAirportCode("PDX");
    assertThat(value, equalTo(true));
  }

  @Test
  void testIsValidAirportCodeWithBadInput(){
    Project4 test = new Project4();
    boolean value = test.isValidAirportCode("PD1");
    assertThat(value, equalTo(false));
  }

  @Test
  void testIsValidAirportCodeWithBadInput2(){
    Project4 test = new Project4();
    boolean value = test.isValidAirportCode("");
    assertThat(value, equalTo(false));
  }

  @Test
  void testCheckValidInputWithGoodInput(){
    Project4 test = new Project4();
    boolean value = test.checkValidInput(1234, "PDX", "10:20 PM", "10/22/2022",
            "BOI", "10:24 PM", "10/22/2022");
    assertThat(value, equalTo(true));
  }

  @Test
  void testCheckValidInputWithBadInput(){
    Project4 test = new Project4();
    boolean value = test.checkValidInput(1234, "PDX", "10:20 PM", "10/22/2022",
            "BOI", "10:242 PM", "10/22/2022");
    assertThat(value, equalTo(false));
  }

  @Test
  void testSeparateArgumentsTooFew(){
    Project4 test = new Project4();
    String[] args = new String[7];

    args[0] = "Alaska Airlines";
    args[1] = "1234";
    args[2] = "PDX";
    args[3] = "10/22/2022";
    args[4] = "10:20";
    args[5] = "BOI";
    args[6] = "10/22/2022";


    List<String> value = test.separateArguments(args, 0);
    assertThat(value.size(), equalTo(7));
  }
  @Test
  void testSeparateArgumentsTooMany(){
    Project4 test = new Project4();
    String[] args = new String[12];

    args[0] = "Alaska Airlines";
    args[1] = "1234";
    args[2] = "PDX";
    args[3] = "10/22/2022";
    args[4] = "10:20";
    args[5] = "BOI";
    args[6] = "10/22/2022";
    args[7] = "10:24";
    args[8] = "seipp";
    args[9] = " ";
    args[10] = " ";
    args[11] = " ";

    List<String> value = test.separateArguments(args, 0);
    assertThat(value.size(), equalTo(12));
  }

  @Test
  void testGetFilePath(){
    Project4 test = new Project4();
    String[] args = new String[11];

    args[0] = "Alaska Airlines";
    args[1] = "1234";
    args[2] = "PDX";
    args[3] = "10/22/2022";
    args[4] = "10:20";
    args[5] = "BOI";
    args[6] = "10/22/2022";
    args[7] = "-textFile";
    args[8] = "here/directory/seipp.txt";
    args[9] = " ";
    args[10] = " ";

    String value = test.getFilePath(args, "-textFile");
    assertThat(value, equalTo("here/directory/seipp.txt"));
  }


  @Test
  void testIsValidFilePathGood(){
    Project4 test = new Project4();
    boolean value = test.isValidFilePath("src");
    assertThat(value, equalTo(true));
  }

  @Test
  void testIsValidFilePathBad(){
    Project4 test = new Project4();
    boolean value = test.isValidFilePath("here/directory?!..seipp.txt");
    assertThat(value, equalTo(false));
  }

  @Test
  void testDoesAirportExistYes(){
    Project4 test = new Project4();
    boolean value = test.doesAirportCodeExist("PDX");
    assertThat(value, equalTo(true));
  }

  @Test
  void testDoesAirportExistNo(){
    Project4 test = new Project4();
    boolean value = test.doesAirportCodeExist("OOO");
    assertThat(value, equalTo(false));

  }

  @Test
  void testDoPrettyPrintSuccess(){
    Project4 test = new Project4();
    Flight flight = new Flight(123, "PDX", "11/22/2022 10:40 AM", "BOI", "11/22/2022 10:40 AM");
    Airline testAirline = new Airline("Alaska Airlines");
    testAirline.addFlight(flight);
    boolean value = false;

    try {
      FileWriter f = new FileWriter("src/test/resources/edu/pdx/cs410J/tmestas/prettytest.txt", false);
      BufferedWriter b = new BufferedWriter(f);
      PrintWriter writer = new PrintWriter(b);
      value = test.doPrettyPrint(testAirline, writer);
    }catch(Exception e){}

    assertThat(value, equalTo(true));
  }

  @Test
  void testDoPrettyPrintFailure(){
    Project4 test = new Project4();
    Flight flight = new Flight();
    Airline testAirline = new Airline("Alaska Airlines");
    testAirline.addFlight(flight);
    boolean value = false;

    try {
      FileWriter f = new FileWriter("src/test/resources/edu/pdx/cs410J/tmestas/prettytest.txt", false);
      BufferedWriter b = new BufferedWriter(f);
      PrintWriter writer = new PrintWriter(b);
      value = test.doPrettyPrint(testAirline, writer);
    }catch(Exception e){}

    assertThat(value, equalTo(false));
  }
  @Test
  void testCheckPrettyConsoleYes(){
    Project4 test = new Project4();
    String [] args = {"-pretty", "-"};
    assertThat(test.checkPrettyConsole(args), equalTo(true));
  }

  @Test
  void testCheckPrettyConsoleNo(){
      Project4 test = new Project4();
      String [] args = {"-pretty", " ", " ", " "};
      assertThat(test.checkPrettyConsole(args), equalTo(false));
  }

  @Test
  void testPrettyPrintToConsoleGood(){
    Project4 test = new Project4();
    Airline testAirline = new Airline("Alaska Airlines");
    Flight testFlight = new Flight(123, "PDX", "02/20/2022 10:40 PM", "PDX", "02/20/2022 10:40 PM");
    testAirline.addFlight(testFlight);
    assertThat(test.prettyPrintConsole(testAirline), equalTo(true));
  }


  @Test
  void testPrettyHasPathYes(){
    Project4 test = new Project4();
    String [] args = {"-pretty", "seipp/seipp.txt", "-"};
    assertThat(test.checkPrettyPath(args), equalTo(true));
  }

  @Test
  void testPrettyHasPathNo(){
    Project4 test = new Project4();
    String [] args = {"-pretty", "-"};
    assertThat(test.checkPrettyPath(args), equalTo(false));
  }

  @Test
  void testIsDepartureTimeNotAfterArrivalTimeYes(){
    Project4 test = new Project4();
    String depart = "10/22/2022 10:30 PM";
    String arrive = "10/23/2022 10:30 PM";
    assertThat(test.isDepartureTimeNotAfterArrivalTime(depart, arrive), equalTo(true));
  }

  @Test
  void testIsDepartureTimeNotAfterArrivalTimeNo(){
    Project4 test = new Project4();
    String depart = "10/23/2022 10:30 PM";
    String arrive = "10/22/2022 10:30 PM";
    assertThat(test.isDepartureTimeNotAfterArrivalTime(depart, arrive), equalTo(false));
  }
  */


}
