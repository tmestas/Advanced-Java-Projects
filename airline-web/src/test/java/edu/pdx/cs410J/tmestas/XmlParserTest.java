package edu.pdx.cs410J.tmestas;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

import java.io.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class XmlParserTest {
    @Test
    public void testGetDocumentBadFormat(){

        StringReader sr = new StringReader(" ");
        XmlParser test = new XmlParser(sr);
        SAXException thrown = assertThrows(SAXException.class, () -> test.GetDocument(),
                "XML file format does not conform to dtd");
        assertTrue(thrown.getMessage().contentEquals("XML file format does not conform to dtd"));
    }

    @Test
    public void testConvert24HourTime(){
        StringReader reader = new StringReader(" ");
        XmlParser test = new XmlParser(reader);
        assertThat(test.Convert24HourTime("23:23"), equalTo("11:23 PM"));
    }


    @Test
    public void testParseInvalidFile(){
        StringReader reader = new StringReader(" ");

        Airline testAirline;
        XmlParser test = new XmlParser(reader);
        try {
            testAirline = test.parse();
        }catch(Exception e){
            System.out.println(e.getMessage());
            return;
        }

        assertThat(testAirline, equalTo(null));
    }


}
