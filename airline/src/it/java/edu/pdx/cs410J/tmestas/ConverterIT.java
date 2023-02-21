package edu.pdx.cs410J.tmestas;

import edu.pdx.cs410J.InvokeMainTestCase;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;

public class ConverterIT extends InvokeMainTestCase {

    private MainMethodResult invokeMain(String... args) {
        return invokeMain(Converter.class, args);
    }

    @Test
    void testNoCommandLineArguments() {
        MainMethodResult result = invokeMain();
        assertThat(result.getTextWrittenToStandardOut(), containsString("Not enough arguments included, run -README for instructions"));
    }

    @Test
    void testTooManyCommandLineArguments() {
        MainMethodResult result = invokeMain(" ", " ", " ");
        assertThat(result.getTextWrittenToStandardOut(), containsString("Too many arguments included, run -README for instructions"));
    }

    @Test
    void testTextFileDoesNotExist() {
        MainMethodResult result = invokeMain("this.txt", "src/test/resources/edu/pdx/cs410J/tmestas/new-airline.xml");
        assertThat(result.getTextWrittenToStandardOut(), containsString("Text file does not exist at this location, exiting"));
    }

    @Test
    void testCantAccessXMLDir() {
        MainMethodResult result = invokeMain("this.txt", "fakedir/new-airline.xml");
        assertThat(result.getTextWrittenToStandardOut(), containsString("Text file does not exist at this location, exiting"));
    }

    @Test
    void testBadTextFileFormat() {
        MainMethodResult result = invokeMain("src/test/resources/edu/pdx/cs410J/tmestas/malformed.txt", "fakedir/new-airline.xml");
        assertThat(result.getTextWrittenToStandardOut(), containsString("Text file is malformed"));
    }
}
