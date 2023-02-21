package edu.pdx.cs410J.tmestas;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;

public class XmlDumperTest {

    @Test
    public void testParseDate(){
        XmlDumper test = new XmlDumper("");
        String [] value = test.ParseDate("11/22/2023");
        assertThat(value[0], equalTo("11"));
        assertThat(value[1], equalTo("22"));
        assertThat(value[2], equalTo("2023"));
    }

    @Test
    public void testCreateDateElement(){
        XmlDumper test = new XmlDumper("");
        Document doc = test.CreateDocument();
        Element testElement = test.createDateElement(doc, "11/22/2023");
        assertThat(testElement.getAttribute("month"), equalTo("11"));
        assertThat(testElement.getAttribute("day"), equalTo("22"));
        assertThat(testElement.getAttribute("year"), equalTo("2023"));
    }

    @Test
    public void testCreateTimeElement(){
        XmlDumper test = new XmlDumper("");
        Document doc = test.CreateDocument();
        Flight testFlight = new Flight(123, "PDX", "11/22/2022 10:40 AM", "BOI", "11/22/2022 10:40 AM");
        Element testElement = test.createTimeElement(doc, testFlight.getDepartureDateTime());
        assertThat(testElement.getAttribute("hour"), equalTo("10"));
        assertThat(testElement.getAttribute("minute"), equalTo("40"));
    }

    @Test
    @Disabled
    public void testCreateDocument(){
        XmlDumper test = new XmlDumper("");
        String systemID = AirlineXmlHelper.SYSTEM_ID;
        String publicID = AirlineXmlHelper.PUBLIC_ID;
        Document testDoc;
        AirlineXmlHelper helper = new AirlineXmlHelper();

        try{
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setValidating(true);

            DocumentBuilder builder = factory.newDocumentBuilder();
            builder.setErrorHandler(helper);
            builder.setEntityResolver(helper);

            DOMImplementation dom = builder.getDOMImplementation();
            DocumentType docType = dom.createDocumentType("airline", publicID, systemID);
            testDoc = dom.createDocument(null, "airline", docType);

        }catch(ParserConfigurationException e){
            System.out.println("Error building the XML document");
            return;
        }

        Document newDoc = test.CreateDocument();
        assertThat(testDoc, equalTo(newDoc));
    }
}
