package edu.pdx.cs410J.tmestas;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PrettyPrinterTest {
    @Test
    void testDumpBad(){
        Airline test = new Airline();
        StringWriter sr = new StringWriter();
        PrintWriter writer = new PrintWriter(sr);
        PrettyPrinter prettyPrinter = new PrettyPrinter(writer);
        assertThrows(IOException.class, () -> prettyPrinter.dump(test));
    }

    @Test
    void testDumpGood(){
        Airline test = new Airline("Seipp Airlines");
        Flight testFlight = new Flight(1234, "PDX", "10/22/2022 10:00 PM",
                "BOI", "10/22/2022 11:00 PM");
        test.addFlight(testFlight);
        StringWriter sr = new StringWriter();
        PrintWriter writer = new PrintWriter(sr);
        PrettyPrinter prettyPrinter = new PrettyPrinter(writer);
        try{
            prettyPrinter.dump(test);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        assertThat(sr.toString(), containsString("Seipp Airlines"));
        assertThat(sr.toString(), containsString("1234"));
        assertThat(sr.toString(), containsString("Portland"));
        assertThat(sr.toString(), containsString("Boise"));
        assertThat(sr.toString(), containsString("hours"));
    }

    @Test
    void testDumpGood2(){
        Airline test = new Airline("Seipp Airlines");
        Flight testFlight = new Flight(1234, "PDX", "10/22/2022 10:00 PM",
                "BOI", "10/22/2022 11:30 PM");
        test.addFlight(testFlight);
        StringWriter sr = new StringWriter();
        PrintWriter writer = new PrintWriter(sr);
        PrettyPrinter prettyPrinter = new PrettyPrinter(writer);
        try{
            prettyPrinter.dump(test);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        assertThat(sr.toString(), containsString("Seipp Airlines"));
        assertThat(sr.toString(), containsString("1234"));
        assertThat(sr.toString(), containsString("Portland"));
        assertThat(sr.toString(), containsString("Boise"));
        assertThat(sr.toString(), containsString("minutes"));
    }

    @Test
    void testDumpGood3(){
        Airline test = new Airline("Seipp Airlines");
        Flight testFlight = new Flight(1234, "PDX", "10/22/2022 10:00 PM",
                "BOI", "10/22/2022 10:59 PM");
        test.addFlight(testFlight);
        StringWriter sr = new StringWriter();
        PrintWriter writer = new PrintWriter(sr);
        PrettyPrinter prettyPrinter = new PrettyPrinter(writer);
        try{
            prettyPrinter.dump(test);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        assertThat(sr.toString(), containsString("Seipp Airlines"));
        assertThat(sr.toString(), containsString("1234"));
        assertThat(sr.toString(), containsString("Portland"));
        assertThat(sr.toString(), containsString("Boise"));
        assertThat(sr.toString(), containsString("minutes"));
    }
}
