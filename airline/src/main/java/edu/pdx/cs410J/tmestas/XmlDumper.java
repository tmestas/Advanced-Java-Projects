package edu.pdx.cs410J.tmestas;

import edu.pdx.cs410J.AirlineDumper;

import java.io.IOException;
import java.io.*;
import java.net.*;

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

            Element airlineName = doc.createElement("airlinename");
            airlineName.appendChild(doc.createTextNode(airline.getName()));

            root.appendChild(airlineName);
            //doc.appendChild(root);

            //loop for all flights
            for(Flight f: airline.getFlights()) {

                Element flight = doc.createElement("flight");
                root.appendChild(flight);

                Element flightNum = doc.createElement("flightnum");
                flightNum.appendChild(doc.createTextNode(String.valueOf(f.getNumber())));
                flight.appendChild(flightNum);

                Element sourceAirport = doc.createElement("source");
                sourceAirport.appendChild(doc.createTextNode(f.getSource()));
                flight.appendChild(sourceAirport);

                Element departureDateTime = doc.createElement("departuredatetime");
                departureDateTime.appendChild(doc.createTextNode(f.getDepartureDateTimeString()));
                flight.appendChild(departureDateTime);

                Element destinationAirport = doc.createElement("destination");
                destinationAirport.appendChild(doc.createTextNode(f.getDestination()));
                flight.appendChild(destinationAirport);

                Element arrivalDateTime = doc.createElement("arrivaldatetime");
                arrivalDateTime.appendChild(doc.createTextNode(f.getArrivalDateTimeString()));
                flight.appendChild(arrivalDateTime);

            }
        } //create xml tree
        catch(Exception e){
            System.out.println("Error while creating XML tree");
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
