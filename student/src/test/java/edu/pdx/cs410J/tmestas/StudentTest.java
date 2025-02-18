package edu.pdx.cs410J.tmestas;

import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * Unit tests for the Student class.  In addition to the JUnit annotations,
 * they also make use of the <a href="http://hamcrest.org/JavaHamcrest/">hamcrest</a>
 * matchers for more readable assertion statements.
 */
public class StudentTest
{

  @Test
  void studentNamedPatIsNamedPat() {
    String name = "Pat";
    var pat = new Student(name, new ArrayList<>(), 0.0, "Doesn't matter");
    assertThat(pat.getName(), equalTo(name));
  }
  @Test
  void allStudentsSayThisClassIsTooMuchWork(){
    Student student = new Student("Tanner", new ArrayList<>(), 0.0, "IDK");
    assertThat(student.says(), equalTo("This class is too much work!"));
  }

  @Test
  @Disabled
  void daveStudentSaysWhatIsExpected(){
    ArrayList<String> classes = new ArrayList<>();
    classes.add("Algorithm");
    classes.add("Operating Systems");
    classes.add("Java");
    Student dave = new Student("Dave", classes, 3.64, "Male");
    String daveString = dave.toString();

    assertThat(daveString, equalTo("Dave has a GPA of 3.64 and is taking 3 classes: Algorithms, Operating Systems, and Java. He says \"This class is too much work\"."));
  }

}
