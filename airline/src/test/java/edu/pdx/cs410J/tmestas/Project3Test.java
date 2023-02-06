package edu.pdx.cs410J.tmestas;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.security.spec.ECField;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * A unit test for code in the <code>Project1</code> class.  This is different
 * from <code>Project1IT</code> which is an integration test (and can capture data
 * written to {@link System#out} and the like.
 */
class Project3Test {


  @Test
  void readmeCanBeReadAsResource() throws IOException {
    try (
      InputStream readme = Project3.class.getResourceAsStream("README.txt")
    ) {
      assertThat(readme, not(nullValue()));
      BufferedReader reader = new BufferedReader(new InputStreamReader(readme));
      String line = reader.readLine();
      assertThat(line, containsString(""));
    }
  }

  @Test
  void testIsValidTimeWithGoodInput1Digit(){
    Project3 test = new Project3();
    boolean value = test.isValidTime("1:30 PM");
    assertThat(value, equalTo(true));
  }

  @Test
  void testIsValidTimeWithGoodInput2Digits(){
    Project3 test = new Project3();
    boolean value = test.isValidTime("10:30 PM");
    assertThat(value, equalTo(true));
  }

  @Test
  void testIsValidTimeWithBadInput(){
    Project3 test = new Project3();
    boolean value = test.isValidTime("030");
    assertThat(value, equalTo(false));
  }

  @Test
  void testIsValidDateWithGoodInput(){
    Project3 test = new Project3();
    boolean value = test.isValidDate("10/22/2022");
    assertThat(value, equalTo(true));
  }

  @Test
  void testIsValidDateWithBadInput(){
    Project3 test = new Project3();
    boolean value = test.isValidDate("100/22/2022");
    assertThat(value, equalTo(false));
  }

  @Test
  void testIsValidAirportCodeWithGoodInput(){
    Project3 test = new Project3();
    boolean value = test.isValidAirportCode("PDX");
    assertThat(value, equalTo(true));
  }

  @Test
  void testIsValidAirportCodeWithBadInput(){
    Project3 test = new Project3();
    boolean value = test.isValidAirportCode("PD1");
    assertThat(value, equalTo(false));
  }

  @Test
  void testIsValidAirportCodeWithBadInput2(){
    Project3 test = new Project3();
    boolean value = test.isValidAirportCode("");
    assertThat(value, equalTo(false));
  }

  @Test
  void testCheckValidInputWithGoodInput(){
    Project3 test = new Project3();
    boolean value = test.checkValidInput(1234, "PDX", "10:20 PM", "10/22/2022",
            "BOI", "10:24 PM", "10/22/2022");
    assertThat(value, equalTo(true));
  }

  @Test
  void testCheckValidInputWithBadInput(){
    Project3 test = new Project3();
    boolean value = test.checkValidInput(1234, "PDX", "10:20 PM", "10/22/2022",
            "BOI", "10:242 PM", "10/22/2022");
    assertThat(value, equalTo(false));
  }

  @Test
  void testSeparateArgumentsTooFew(){
    Project3 test = new Project3();
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
    Project3 test = new Project3();
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
    Project3 test = new Project3();
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
    Project3 test = new Project3();
    boolean value = test.isValidFilePath("src");
    assertThat(value, equalTo(true));
  }

  @Test
  void testIsValidFilePathBad(){
    Project3 test = new Project3();
    boolean value = test.isValidFilePath("here/directory?!..seipp.txt");
    assertThat(value, equalTo(false));
  }

  @Test
  void testDoesAirportExistYes(){
    Project3 test = new Project3();
    boolean value = test.doesAirportCodeExist("PDX");
    assertThat(value, equalTo(true));
  }

  @Test
  void testDoesAirportExistNo(){
    Project3 test = new Project3();
    boolean value = test.doesAirportCodeExist("OOO");
    assertThat(value, equalTo(false));

  }

  @Test
  void testDoPrettyPrintSuccess(){
    Project3 test = new Project3();
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
    Project3 test = new Project3();
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

}
