package edu.pdx.cs410J.tmestas;

import com.google.common.annotations.VisibleForTesting;

import java.io.*;

/**
 * The main class that parses the command line and communicates with the
 * Airline server using REST.
 */
public class Project5 {
    public static final String MISSING_ARGS = "Missing command line arguments";
    
    public static void main(String... args) {

        if(args.length == 0){
            System.out.println("\nNO COMMAND LINE ARGUMENTS");
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
            printResourceFile("readme.txt");
            return;
        }

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
    public static boolean printResourceFile(String file){
        try
        {
            InputStream readMe = Project5.class.getResourceAsStream(file);
            BufferedReader reader = new BufferedReader(new InputStreamReader(readMe));
            String line;
            while((line=reader.readLine())!=null){
                System.out.println(line);
            }
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
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
        err.println(printResourceFile("usage.txt"));
    }
}