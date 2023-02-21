package edu.pdx.cs410J.tmestas;

import edu.pdx.cs410J.AirlineParser;
import edu.pdx.cs410J.ParserException;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * class to control the Xml Parser
 */
public class XmlParser implements AirlineParser<Airline> {

    public String FilePath;

    /**
     * constructor
     * @param filePath filepath to parse
     */
    XmlParser(String filePath){
        this.FilePath = filePath;
    }

    /**
     * build document to be used in parsing
     * @return document to parse
     * @throws ParserConfigurationException error configuring the parser
     * @throws SAXException when the format of the xml file does not conform to dtd
     * @throws IOException when the xml file does not exist
     */
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

        }
        catch(ParserConfigurationException e){
            throw new SAXException("Could not configure parser");
        }
        catch(SAXException e){
            throw new SAXException("XML file format does not conform to dtd");
        }
        catch(IOException e){
            throw new IOException("XML file does not exist");
        }

        return doc;
    }

    /**
     * convert 24 hour time to 12 hour time
     * @param oldTime 24 hour time to convert
     * @return 24 hour time converted to 12 hour time
     */
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

    /**
     * parse the document
     * @return an airline constructed from xml
     * @throws ParserException when there is a parsing error
     */
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
        Element newFlights = (Element) root.getChildNodes();

        String AirlineName = GetTextElement(newFlights.getElementsByTagName("name"));
        Airline newAirline = new Airline(AirlineName);

        NodeList flightList = newFlights.getElementsByTagName("flight");
        Element currentFlight;

        NodeList currentNumber;
        String flightNum = null;
        NodeList currentSource;
        String source = null;
        NodeList depart;
        NodeList depart2 = null;
        Element departDate = null;
        String currentDepartDateString;
        Element departTime = null;
        String currentDepartTimeString;
        String currentDepartDateTimeString;
        NodeList currentDestination;
        String destination;
        NodeList arrive;
        NodeList arrive2 = null;
        Element arriveDate = null;
        String currentArriveDateString;
        Element arriveTime = null;
        String currentArriveTimeString;
        String currentArriveDateTimeString;


        int i = 0;
        int j = 0;

        for(i = 0; i < flightList.getLength(); ++i){
            currentFlight = (Element) flightList.item(i);

            currentNumber = currentFlight.getElementsByTagName("number");
            flightNum = GetTextElement(currentNumber);

            currentSource = currentFlight.getElementsByTagName("src");
            source = GetTextElement(currentSource);


            depart = currentFlight.getElementsByTagName("depart");
            for(j = 0; j<depart.getLength(); ++j){
                if(depart.item(j).getNodeType() == Node.ELEMENT_NODE){
                    depart2 = depart.item(j).getChildNodes();
                }
            }
            for(j = 0; j < depart2.getLength(); ++j){
                if(depart2.item(j).getNodeName().equals("date")){
                    departDate = (Element) depart2.item(j);
                }
                if(depart2.item(j).getNodeName().equals("time")){
                    departTime = (Element) depart2.item(j);
                }
            }
            currentDepartDateString = departDate.getAttribute("month") + "/" + departDate.getAttribute("day") + "/"
                    + departDate.getAttribute("year");
            currentDepartTimeString = departTime.getAttribute("hour") + ":" + departTime.getAttribute("minute");
            currentDepartDateTimeString = currentDepartDateString + " " + Convert24HourTime(currentDepartTimeString);

            currentDestination = currentFlight.getElementsByTagName("dest");
            destination = GetTextElement(currentDestination);

            arrive = currentFlight.getElementsByTagName("arrive");
            for(j = 0; j<arrive.getLength(); ++j){
                if(arrive.item(j).getNodeType() == Node.ELEMENT_NODE){
                    arrive2 = arrive.item(j).getChildNodes();
                }
            }
            for(j = 0; j < arrive2.getLength(); ++j){
                if(arrive2.item(j).getNodeName().equals("date")){
                    arriveDate = (Element) arrive2.item(j);
                }
                if(arrive2.item(j).getNodeName().equals("time")){
                    arriveTime = (Element) arrive2.item(j);
                }
            }
            currentArriveDateString = arriveDate.getAttribute("month") + "/" + arriveDate.getAttribute("day") + "/"
                    + arriveDate.getAttribute("year");
            currentArriveTimeString = arriveTime.getAttribute("hour") + ":" + arriveTime.getAttribute("minute");
            currentArriveDateTimeString = currentArriveDateString + " " + Convert24HourTime(currentArriveTimeString);


            newAirline.addFlight(new Flight(Integer.parseInt(flightNum), source, currentDepartDateTimeString, destination,
                    currentArriveDateTimeString));
        }

        return newAirline;
    }

    /**
     * get text from an element
     * @param list NodeList to search
     * @return text from element
     */
    public String GetTextElement(NodeList list){
        int j = 0;
        for(j = 0; j < list.getLength(); ++j){
            if(list.item(j).getNodeType() == Node.ELEMENT_NODE){
                return list.item(j).getTextContent();
            }
        }
        return null;
    }
}
