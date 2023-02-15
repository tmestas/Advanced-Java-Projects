package edu.pdx.cs410J.tmestas;

import edu.pdx.cs410J.AirlineDumper;

import java.io.IOException;
import java.io.*;
import java.net.*;
import java.util.Date;

import javax.print.attribute.Attribute;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import org.w3c.dom.*;

public class XmlDumper implements AirlineDumper<Airline> {
    public String FilePath;

    XmlDumper(String filePath){
        this.FilePath = filePath;
    }

    public String [] ParseDate(String date){
        String [] array1 = date.split(" ");
        //System.out.println("ARRAY1[0]: " + array1[0]);
        //System.out.println("ARRAY1[1]: " + array1[1]);
        String parsedDate = array1[0];
        return parsedDate.split("/");

    }

    public Element createDateElement(Document doc, String dateString){
        Element date = doc.createElement("date");
        String [] parsedDate = ParseDate(dateString);
        date.setAttribute("day", String.valueOf(parsedDate[0]));
        date.setAttribute("month", String.valueOf(parsedDate[1]));
        date.setAttribute("year", String.valueOf(parsedDate[2]));
        return date;
    }

    public Element createTimeElement(Document doc, Date date){
        Element time = doc.createElement("time");
        time.setAttribute("hours", String.valueOf(date.getHours()));
        time.setAttribute("minutes", String.valueOf(date.getMinutes()));
        return time;
    }
    @Override
    public void dump(Airline airline) throws IOException {

        String systemID = null;
        Document doc;

        try{
            File dtd = new File("http://www.cs.pdx.edu/˜whitlock/dtds/airline.dtd");
            systemID = dtd.toURL().toString();
        }catch(MalformedURLException e){
            System.out.println("BAD URL");
        }

        try{
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

            factory.setValidating(true);

            DocumentBuilder builder = factory.newDocumentBuilder();

            DOMImplementation dom = builder.getDOMImplementation();

            DocumentType docType = dom.createDocumentType("airline", null, systemID);

            doc = dom.createDocument(null, "airline", docType);

            //happens once
            Element root = doc.getDocumentElement();

            Element airlineName = doc.createElement("name");
            airlineName.appendChild(doc.createTextNode(airline.getName()));

            root.appendChild(airlineName);
            //doc.appendChild(root);

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
        } //create xml tree
        catch(Exception e){
            System.out.println("Error while creating XML tree");
            System.out.print(e.getMessage());
            return;
        }

        try {
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "YES");
            FileOutputStream outputStream = new FileOutputStream(this.FilePath);
            StreamResult result = new StreamResult(outputStream);
            transformer.transform(new DOMSource(doc), result);
            outputStream.close();
        }catch(Exception e){
            System.out.println("\nCould not access XML file");
        }
    }
}
