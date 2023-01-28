package edu.pdx.cs410J.tmestas;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * A unit test for code in the <code>Project1</code> class.  This is different
 * from <code>Project1IT</code> which is an integration test (and can capture data
 * written to {@link System#out} and the like.
 */
class Project1Test {


  @Test
  void readmeCanBeReadAsResource() throws IOException {
    try (
      InputStream readme = Project1.class.getResourceAsStream("README.txt")
    ) {
      assertThat(readme, not(nullValue()));
      BufferedReader reader = new BufferedReader(new InputStreamReader(readme));
      String line = reader.readLine();
      assertThat(line, containsString(""));
    }
  }

  @Test
  void testIsValidTimeWithGoodInput1Digit(){
    Project1 test = new Project1();
    boolean value = test.isValidTime("1:30");
    assertThat(value, equalTo(true));
  }

  @Test
  void testIsValidTimeWithGoodInput2Digits(){
    Project1 test = new Project1();
    boolean value = test.isValidTime("10:30");
    assertThat(value, equalTo(true));
  }

  @Test
  void testIsValidTimeWithBadInput(){
    Project1 test = new Project1();
    boolean value = test.isValidTime("030");
    assertThat(value, equalTo(false));
  }

  @Test
  void testIsValidDateWithGoodInput(){
    Project1 test = new Project1();
    boolean value = test.isValidDate("10/22/2022");
    assertThat(value, equalTo(true));
  }

  @Test
  void testIsValidDateWithBadInput(){
    Project1 test = new Project1();
    boolean value = test.isValidDate("100/22/2022");
    assertThat(value, equalTo(false));
  }

  @Test
  void testIsValidAirportCodeWithGoodInput(){
    Project1 test = new Project1();
    boolean value = test.isValidAirportCode("PDX");
    assertThat(value, equalTo(true));
  }

  @Test
  void testIsValidAirportCodeWithBadInput(){
    Project1 test = new Project1();
    boolean value = test.isValidAirportCode("PD1");
    assertThat(value, equalTo(false));
  }

  @Test
  void testIsValidAirportCodeWithBadInput2(){
    Project1 test = new Project1();
    boolean value = test.isValidAirportCode("");
    assertThat(value, equalTo(false));
  }

  @Test
  void testCheckValidInputWithGoodInput(){
    Project1 test = new Project1();
    boolean value = test.checkValidInput(1234, "PDX", "10:20", "10/22/2022",
            "BOI", "10:24", "10/22/2022");
    assertThat(value, equalTo(true));
  }

  @Test
  void testCheckValidInputWithBadInput(){
    Project1 test = new Project1();
    boolean value = test.checkValidInput(1234, "PDX", "10:20", "10/22/2022",
            "BOI", "10:242", "10/22/2022");
    assertThat(value, equalTo(false));
  }

  @Test
  void testSeparateArgumentsTooFew(){
    Project1 test = new Project1();
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
    Project1 test = new Project1();
    String[] args = new String[9];

    args[0] = "Alaska Airlines";
    args[1] = "1234";
    args[2] = "PDX";
    args[3] = "10/22/2022";
    args[4] = "10:20";
    args[5] = "BOI";
    args[6] = "10/22/2022";
    args[7] = "10:24";
    args[8] = "seipp";

    List<String> value = test.separateArguments(args, 0);
    assertThat(value.size(), equalTo(9));
  }


}
