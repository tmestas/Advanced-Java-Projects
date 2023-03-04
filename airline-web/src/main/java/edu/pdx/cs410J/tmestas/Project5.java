package edu.pdx.cs410J.tmestas;

import com.google.common.annotations.VisibleForTesting;

import java.io.*;

/**
 * The main class that parses the command line and communicates with the
 * Airline server using REST.
 */
public class Project5 {
    //testing new branch for Project5
    public static final String MISSING_ARGS = "Missing command line arguments";
    
    public static void main(String... args) {

        if(args.length == 0){
            usage(MISSING_ARGS);
            return;
        }

        CommandLineArgHandler handler = new CommandLineArgHandler();

        try {
            handler.parse(args);
        }catch(Exception e){
            error(e.getMessage());
            return;
        }

        boolean readme = handler.Readme;
        if(readme){
            doReadMe();
            return;
        } //having issues

        String hostName = handler.HostName;
        int port = Integer.parseInt(handler.PortString);
        boolean print = handler.Print;
        boolean search = handler.Search;

        if(!handler.isReachableHost(hostName, Integer.parseInt(handler.PortString))) {
            System.out.println("\nHost " + handler.HostName + " is not reachable on port " + handler.PortString);
            return;
        }

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

                if(print){
                    System.out.println(newFlight);
                }

            }catch(Exception e){
                error("While contacting server: " + e.getMessage());
            }

        }
        else{
            System.out.println("\nSEARCHING!\n");
            try {
                Airline airline;
                if(sourceAirport == null || destinationAirport == null) { //this is what causes all flights to print

                    try {
                        airline = client.getAirline(airlineName);
                    }catch(IOException e){
                        System.out.println(e.getMessage());
                        return;
                    }

                    PrintWriter writer = new PrintWriter(System.out);
                    PrettyPrinter prettyPrinter = new PrettyPrinter(writer);
                    prettyPrinter.dump(airline);

                }else{

                    try {
                        airline = client.getFlightsBetween(airlineName, sourceAirport, destinationAirport);
                    }catch(Exception e){
                        System.out.println(e.getMessage());
                        return;
                    }

                    PrintWriter writer = new PrintWriter(System.out);
                    PrettyPrinter prettyPrinter = new PrettyPrinter(writer);
                    prettyPrinter.dump(airline);
                }

            }
            catch(IOException e){
                System.out.println(e.getMessage());
            }
            catch(Exception e){
                error("While contacting server: " + e.getMessage());
            }
        }
    }

    /**
     * handle readme stuff
     */
    public static boolean doReadMe(){
        try  //issue getting readme
        {
            InputStream readMe = Project5.class.getResourceAsStream("readme.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(readMe));
            String line;
            while((line=reader.readLine())!=null){
                System.out.println(line);
            }
        }
        catch(Exception e)
        {
            System.out.println("COULD NOT GET README");
            return false;
        }
        return true;
    }
    /**
     * prints error
     * @param message message to print
     */
    @VisibleForTesting
    public static void error( String message )
    {
        PrintStream err = System.err;
        err.println("** " + message);
    }

    /**
     * Prints usage information for this program and exits
     * @param message An error message to print
     */
    public static void usage( String message )
    {
        PrintStream err = System.err;
        err.println("** " + message);
        err.println();
        err.println("usage: \n");
        err.println("To add flight: java Project5 -host [hostname] -port [port] <args>");
        err.println("To display all of an airlines flights: java Project5 -host [hostname] -port [port] ");
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