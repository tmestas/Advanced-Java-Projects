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

    @Override
    public void dump(Airline airline) throws IOException {
        String publicID = null;
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
            Element root = doc.createElement("airline");
            root.appendChild(doc.createTextNode("Seipp Airlines"));
            doc.appendChild(root);

            //happens multiple times

            Element flight = doc.createElement("flight");
            root.appendChild(flight);

            Element flightNum = doc.createElement("flightnum");
            flightNum.appendChild(doc.createTextNode("1111"));
            flight.appendChild(flightNum);

            Element sourceAirport = doc.createElement("source");
            flightNum.appendChild(doc.createTextNode("PDX"));
            flight.appendChild(sourceAirport);

            Element departureDateTime = doc.createElement("departuredatetime");
            flightNum.appendChild(doc.createTextNode("11/11/1111 11:11 PM"));
            flight.appendChild(departureDateTime);

            Element destinationAirport = doc.createElement("destination");
            flightNum.appendChild(doc.createTextNode("PDX"));
            flight.appendChild(destinationAirport);

            Element arrivalDateTime = doc.createElement("arrivaldatetime");
            flightNum.appendChild(doc.createTextNode("11/11/1111 11:11 PM"));
            flight.appendChild(arrivalDateTime);
        }
        catch(Exception e){
            System.out.println("ERROR WHILE MAKING TREE");
            e.printStackTrace();
            e.getCause();
        }


    }
}
