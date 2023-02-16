package edu.pdx.cs410J.tmestas;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class XmlParserTest {

    @Test
    public void testGetDocumentBadFormat(){
        XmlParser test = new XmlParser("src/test/resources/edu/pdx/cs410J/tmestas/invalid-airline.xml");
        SAXException thrown = assertThrows(SAXException.class, () -> test.GetDocument(),
                "XML file format does not conform to dtd");
        assertTrue(thrown.getMessage().contentEquals("XML file format does not conform to dtd"));
    }

    @Test
    public void testGetDocumentDoesNotExist(){
        XmlParser test = new XmlParser("src/test/resources/edu/pdx/cs410J/tmestas/fake-airline.xml");
        IOException thrown = assertThrows(IOException.class, () -> test.GetDocument(),
                "XML file does not exist");
        assertTrue(thrown.getMessage().contentEquals("XML file does not exist"));
    }

    @Test
    public void testConvert24HourTime(){
        XmlParser test = new XmlParser("");
        assertThat(test.Convert24HourTime("23:23"), equalTo("11:23 PM"));
    }

    @Test
    public void testParse(){
        Airline testAirline;
        XmlParser test = new XmlParser("src/test/resources/edu/pdx/cs410J/tmestas/valid-airline.xml");
        try {
            testAirline = test.parse();
        }catch(Exception e){
            System.out.println(e.getMessage());
            System.out.println(e.getStackTrace());
            return;
        }

        assertThat(testAirline.getName(), equalTo("Valid Airlines"));
    }
}
