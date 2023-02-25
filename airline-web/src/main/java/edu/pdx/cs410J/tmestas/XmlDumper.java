package edu.pdx.cs410J.tmestas;

import edu.pdx.cs410J.AirlineDumper;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Date;


/**
 * The class that controls the XmlDumper for Project #4
 */
public class XmlDumper implements AirlineDumper<Airline> {

    private final Writer writer;

    /**
     * Constructor
     * @param writer to write to character stream with
     */
    XmlDumper(Writer writer){
        this.writer = writer;
    }

    /**
     * gets the individual components of a date
     * @param date date to parse
     * @return array containing components of the date
     */
    public String [] ParseDate(String date){
        String [] array1 = date.split(" ");
        String parsedDate = array1[0];
        return parsedDate.split("/");
    }

    /**
     * function to create a date element to be sent to the xml file
     * @param doc document object
     * @param dateString date to be converted
     * @return an XML element
     */
    public Element createDateElement(Document doc, String dateString){
        Element date = doc.createElement("date");
        String [] parsedDate = ParseDate(dateString);
        date.setAttribute("day", String.valueOf(parsedDate[1]));
        date.setAttribute("month", String.valueOf(parsedDate[0]));
        date.setAttribute("year", String.valueOf(parsedDate[2]));
        return date;
    }

    /**
     * Function to create a time element
     * @param doc document object
     * @param date date object that contains the time to be converted
     * @return an XML element
     */
    public Element createTimeElement(Document doc, Date date){
        Element time = doc.createElement("time");
        time.setAttribute("hour", String.valueOf(date.getHours()));
        time.setAttribute("minute", String.valueOf(date.getMinutes()));
        return time;
    }

    /**
     * function to create the document to be build the XML tree on
     * @return document
     */
    public Document CreateDocument(){
        String systemID = AirlineXmlHelper.SYSTEM_ID;
        String publicID = AirlineXmlHelper.PUBLIC_ID;
        Document doc;
        AirlineXmlHelper helper = new AirlineXmlHelper();

        try{
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setValidating(true);

            DocumentBuilder builder = factory.newDocumentBuilder();
            builder.setErrorHandler(helper);
            builder.setEntityResolver(helper);

            DOMImplementation dom = builder.getDOMImplementation();
            DocumentType docType = dom.createDocumentType("airline", publicID, systemID);
            doc = dom.createDocument(null, "airline", docType);

        }catch(ParserConfigurationException e){
            System.out.println("Error building the XML document");
            return null;
        }

        return doc;
    }

    /**
     * function that writes the XML tree to a stream
     * @param doc the document containing the XML tree
     */
    public void WriteToStream(Document doc){
        try {
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "YES");
            transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, AirlineXmlHelper.SYSTEM_ID);
            transformer.setOutputProperty(OutputKeys.DOCTYPE_PUBLIC, AirlineXmlHelper.PUBLIC_ID);
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            transformer.setOutputProperty(OutputKeys.ENCODING, "ASCII");

            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(writer);
            transformer.transform(source, result);
        }
        catch(Exception e){
            System.out.println("Error dumping xml");
        }
    }

    /**
     * function to dump contents of an airline to the xml file
     * @param airline airline to dump
     * @throws IOException error writing the airline
     */
    @Override
    public void dump(Airline airline) throws IOException {

        Document doc = CreateDocument();
        if(doc == null){return;}

        //happens once
        Element root = doc.getDocumentElement();
        Element airlineName = doc.createElement("name");
        airlineName.appendChild(doc.createTextNode(airline.getName()));
        root.appendChild(airlineName);

        //loop for all flights
        for(Flight f: airline.getFlights()) {

            Element flight = doc.createElement("flight");
            root.appendChild(flight);

            Element flightNum = doc.createElement("number");
            flightNum.appendChild(doc.createTextNode(String.valueOf(f.getNumber())));
            flight.appendChild(flightNum);

            Element sourceAirport = doc.createElement("src");
            sourceAirport.appendChild(doc.createTextNode(f.getSource()));
            flight.appendChild(sourceAirport);

            //start departure stuff
            Element departureDateTime = doc.createElement("depart");
            Element departureDate = createDateElement(doc, f.getDepartureDateTimeString());
            Element departureTime = createTimeElement(doc, f.getDepartureDateTime());
            departureDateTime.appendChild(departureDate);
            departureDateTime.appendChild(departureTime);
            flight.appendChild(departureDateTime);
            //end departure stuff

            Element destinationAirport = doc.createElement("dest");
            destinationAirport.appendChild(doc.createTextNode(f.getDestination()));
            flight.appendChild(destinationAirport);

            //start arrival stuff
            Element arrivalDateTime = doc.createElement("arrive");
            Element arrivalDate = createDateElement(doc, f.getArrivalDateTimeString());
            Element arrivalTime = createTimeElement(doc, f.getArrivalDateTime());
            arrivalDateTime.appendChild(arrivalDate);
            arrivalDateTime.appendChild(arrivalTime);
            flight.appendChild(arrivalDateTime);
            //end arrival stuff
        }

        WriteToStream(doc);
    }
}
