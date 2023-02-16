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
        String parsedDate = array1[0];
        return parsedDate.split("/");
    }

    public Element createDateElement(Document doc, String dateString){
        Element date = doc.createElement("date");
        String [] parsedDate = ParseDate(dateString);
        date.setAttribute("day", String.valueOf(parsedDate[1]));
        date.setAttribute("month", String.valueOf(parsedDate[0]));
        date.setAttribute("year", String.valueOf(parsedDate[2]));
        return date;
    }

    public Element createTimeElement(Document doc, Date date){
        Element time = doc.createElement("time");
        time.setAttribute("hour", String.valueOf(date.getHours()));
        time.setAttribute("minute", String.valueOf(date.getMinutes()));
        return time;
    }

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

    public void WriteToFile(Document doc){
        try {
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "YES");
            transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, AirlineXmlHelper.SYSTEM_ID);
            //transformer.setOutputProperty(OutputKeys.DOCTYPE_PUBLIC, AirlineXmlHelper.PUBLIC_ID);
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            transformer.setOutputProperty(OutputKeys.ENCODING, "ASCII");

            FileOutputStream outputStream = new FileOutputStream(this.FilePath);
            StreamResult result = new StreamResult(outputStream);
            transformer.transform(new DOMSource(doc), result);
            outputStream.close();
        }catch(TransformerConfigurationException e){
            System.out.println("\nTransformer could not be configured");
        }
        catch(IOException e){
            System.out.println("\nCould not access XML file");
        }
        catch(TransformerException e){
            System.out.println("\nError with the transformer");
        }
    }
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

        WriteToFile(doc);
    }
}
