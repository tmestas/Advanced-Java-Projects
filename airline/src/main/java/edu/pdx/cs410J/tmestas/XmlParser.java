package edu.pdx.cs410J.tmestas;

import edu.pdx.cs410J.AirlineParser;
import edu.pdx.cs410J.ParserException;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class XmlParser implements AirlineParser<Airline> {

    public String FilePath;

    XmlParser(String filePath){
        this.FilePath = filePath;
    }

    public Document GetDocument() throws ParserConfigurationException, SAXException, IOException{
        Document doc;
        AirlineXmlHelper helper = new AirlineXmlHelper();

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setValidating(true);
            DocumentBuilder builder = factory.newDocumentBuilder();
            builder.setErrorHandler(helper);
            builder.setEntityResolver(helper);

            doc = builder.parse(FilePath);

        }catch(ParserConfigurationException e){
            throw new ParserConfigurationException("Parser could not be configured");
        }
        catch(SAXException e){
            throw new SAXException("XML file format does not conform to dtd");
        }
        catch(IOException e){
            throw new IOException("XML file does not exist");
        }

        return doc;
    }

    public String Convert24HourTime(String oldTime){
        String newTime;

        try {
            SimpleDateFormat _24HourSDF = new SimpleDateFormat("HH:mm");
            SimpleDateFormat _12HourSDF = new SimpleDateFormat("hh:mm a");
            Date _24HourDt = _24HourSDF.parse(oldTime);
            newTime = _12HourSDF.format(_24HourDt);
        }catch(Exception e){
            System.out.println("Error converting time");
            return null;
        }

        return newTime;
    }
    @Override
    public Airline parse() throws ParserException {
        Document doc;

        try {
            doc = GetDocument();
        }catch(ParserConfigurationException | SAXException | IOException e) {
            System.out.println(e.getMessage());
            return null;
        }

        Element root = (Element) doc.getChildNodes().item(1);
        Element AirlineName = (Element) root.getChildNodes().item(1);
        String name = AirlineName.getTextContent();
        Airline newAirline = new Airline(name);
        NodeList flightInfo;
        NodeList departInfo;
        NodeList arrivalInfo;
        Element dateInfo;
        Element timeInfo;
        String date;
        String time;
        String flightNumber;
        String source;
        String destination;
        String departureDateTime;
        String arrivalDateTime;
        NodeList flights = root.getChildNodes();

        int i;
        for(i = 3; i < flights.getLength(); i = i + 2){
            flightInfo = flights.item(i).getChildNodes();

            flightNumber = flightInfo.item(1).getTextContent();

            source = flightInfo.item(3).getTextContent();

            departInfo = flightInfo.item(5).getChildNodes();
            dateInfo = (Element) departInfo.item(1);
            date = dateInfo.getAttribute("month") + "/" + dateInfo.getAttribute("day") + "/"
                    + dateInfo.getAttribute("year");
            timeInfo =(Element) departInfo.item(3);
            time = timeInfo.getAttribute("hour") + ":" + timeInfo.getAttribute("minute");
            departureDateTime = date + " " + Convert24HourTime(time);

            destination = flightInfo.item(7).getTextContent();

            arrivalInfo = flightInfo.item(9).getChildNodes();
            dateInfo = (Element) arrivalInfo.item(1);
            date = dateInfo.getAttribute("month") + "/" + dateInfo.getAttribute("day") + "/"
                    + dateInfo.getAttribute("year");
            timeInfo =(Element) arrivalInfo.item(3);
            time = timeInfo.getAttribute("hour") + ":" + timeInfo.getAttribute("minute");
            arrivalDateTime = date + " " + Convert24HourTime(time);

            newAirline.addFlight(new Flight(Integer.parseInt(flightNumber), source, departureDateTime, destination,
                    arrivalDateTime));
        }

        return newAirline;
    }
}
