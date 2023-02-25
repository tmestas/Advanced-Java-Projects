package edu.pdx.cs410J.tmestas;

import edu.pdx.cs410J.ParserException;
import org.checkerframework.checker.units.qual.C;

import java.io.*;
import java.util.Map;

/**
 * The main class that parses the command line and communicates with the
 * Airline server using REST.
 */
public class Project5 {
    //testing new branch for Project5
    public static final String MISSING_ARGS = "Missing command line arguments";
    
    public static void main(String... args) {

        CommandLineArgHandler handler = new CommandLineArgHandler();

        try {
            handler.parse(args);
        }catch(Exception e){
            System.out.println(e.getMessage());
            return;
        }

        boolean readme = handler.Readme;
        if(readme){
            //do readme and exit
            System.out.println("README!");
            return;
        }

        String hostName = handler.HostName;
        int port = Integer.parseInt(handler.PortString);
        boolean print = handler.Print;
        boolean search = handler.Search;

        AirlineRestClient client = new AirlineRestClient(hostName, port);

        String airlineName = handler.AirlineName;
        String flightNumberAsString = handler.FlightNumberAsString;
        String sourceAirport = handler.Source;
        String DepartureDateTime = handler.DepartureDate + " " + handler.DepartureTime;
        String destinationAirport = handler.Destination;
        String ArrivalDateTime = handler.ArrivalDate + " " + handler.ArrivalTime;


        if(!search){
            //adding an airline
            System.out.println("ADDING!");
            try {
                Flight newFlight = new Flight(Integer.parseInt(flightNumberAsString), sourceAirport, DepartureDateTime,
                        destinationAirport, ArrivalDateTime);
                client.addFlight(airlineName, newFlight);
            }catch(Exception e){
                error("While contacting server: " + e.getMessage());
            }
        }
        else{
            System.out.println("\nSEARCHING!\n");
            try {
                Airline airline = client.getAirline(airlineName);
                PrintWriter writer = new PrintWriter(System.out);
                PrettyPrinter prettyPrinter = new PrettyPrinter(writer);
                prettyPrinter.dump(airline);

                //next figure out how to search by parameters
            }catch(Exception e){
                error("While contacting server: " + e.getMessage());
            }
        }
    }

















    private static void error( String message )
    {
        PrintStream err = System.err;
        err.println("** " + message);
    }

    /**
     * Prints usage information for this program and exits
     * @param message An error message to print
     */
    private static void usage( String message )
    {
        PrintStream err = System.err;
        err.println("** " + message);
        err.println();
        err.println("usage: java Project5 host port [word] [definition]");
        err.println("  host         Host of web server");
        err.println("  port         Port of web server");
        err.println("  word         Word in dictionary");
        err.println("  definition   Definition of word");
        err.println();
        err.println("This simple program posts words and their definitions");
        err.println("to the server.");
        err.println("If no definition is specified, then the word's definition");
        err.println("is printed.");
        err.println("If no word is specified, all dictionary entries are printed");
        err.println();
    }
}