package edu.pdx.cs410J.tmestas;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

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
  void testIsValidTime(){
    Project1 test = new Project1();
    boolean value = test.isValidTime("1:30");
    assertThat(value, equalTo(true));
  }

  @Test
  void testIsValidDate(){
    Project1 test = new Project1();
    boolean value = test.isValidDate("10/22/2022");
    assertThat(value, equalTo(true));
  }

  @Test
  void testIsValidAirportCode(){
    Project1 test = new Project1();
    boolean value = test.isValidAirportCode("PDX");
    assertThat(value, equalTo(true));
  }

  @Test
  void testCheckValidInput(){
    Project1 test = new Project1();
    boolean value = test.checkValidInput(1234, "PDX", "10:20", "10/22/2022",
            "BOI", "10:24", "10/22/2022");
    assertThat(value, equalTo(true));
  }

}
