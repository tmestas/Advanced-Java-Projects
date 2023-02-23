package edu.pdx.cs410J.tmestas;

import com.google.common.annotations.VisibleForTesting;
import edu.pdx.cs410J.AirportNames;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CommandLineArgHandler {

    public String HostName;
    public String PortString;
    public String AirlineName;
    public String FlightNumberAsString;
    public String Source;
    public String DepartureDate;
    public String DepartureTime;
    public String Destination;
    public String ArrivalDate;
    public String ArrivalTime;
    public boolean Search;
    public boolean Print;
    public boolean Readme;

    CommandLineArgHandler(){
        HostName = null;
        PortString = null;
        AirlineName = null;
        FlightNumberAsString = null;
        Source = null;
        DepartureDate = null;
        DepartureTime = null;
        Destination = null;
        ArrivalDate = null;
        ArrivalTime = null;
        Search = false;
        Print = false;
        Readme = false;
    }

    public void parse(String[] args) throws Exception{

        int optionSize;

        try {optionSize = handleOptions(args);}
        catch(Exception e){throw new Exception(e.getMessage());}

        if(optionSize == -1){
            //readme is specified, abort everything and print readme
            this.Readme = true;
            return;
        }

        try{handleArgs(args, optionSize);}
        catch(Exception e){throw new Exception(e.getMessage());}
    }

    private boolean checkForReadMe(String[]args){
        for(String arg: args){
            if(arg.equals("-README")){
                return true;
            }
        }
        return false;
    }

    private int handleOptions(String[] args) throws Exception{
        if (checkForReadMe(args)) {
            return -1;
        }

        String hostName = null;
        String portString = null;
        int i = 0;
        int optionsLength = 0;

        for(i = 0; i < args.length; ++i){
            if(args[i].equals("-host")){
                try {hostName = args[i + 1];optionsLength += 2;}
                catch(IndexOutOfBoundsException e){throw new Exception("\nNo value included with -host");}
            }
            if(args[i].equals("-port")){
                try{portString = args[i + 1];optionsLength += 2;}
                catch(IndexOutOfBoundsException e){throw new Exception("\nNo value included with -port");}
            }
            if(args[i].equals("-search")){this.Search = true; ++optionsLength;}
            if(args[i].equals("-print")){this.Print = true; ++optionsLength;}
            if(args[i].equals("-README")){this.Readme = true; ++optionsLength;}
        }

        if(isInteger(portString)){this.PortString = portString;}
        else{throw new Exception("\nInteger value required for port");}

        if(isReachableHost(hostName, Integer.parseInt(portString))){this.HostName = hostName;}
        else{throw new Exception("\nHost " + hostName + " is not reachable on port " + portString);}

        return optionsLength;
    }


    private void handleArgs(String[] args, int start) throws Exception{
        try {
            this.AirlineName = args[start];

            if (isInteger(args[start + 1])) {this.FlightNumberAsString = args[start + 1];}
            else{throw new Exception ("Flight Number is not an integer");}

            this.Source = args[start + 2];
            this.DepartureDate = args[start + 3];
            this.DepartureTime = args[start + 4] + " " + args[start + 5];
            this.Destination = args[start + 6];
            this.ArrivalDate = args[start + 7];
            this.ArrivalTime = args[start + 8] + " " + args[start + 9];

            //if it makes it to here all arguments are included, so we need to check their validity

            try{
            checkValidInput(this.Source, this.DepartureTime, this.DepartureDate, this.Destination,
                                                                this.ArrivalTime, this.ArrivalDate);
            }catch(Exception e){
                throw new Exception(e.getMessage());
            }
        }catch(IndexOutOfBoundsException e){
            if(this.AirlineName == null){
                throw new Exception("Missing airline name");
            }

            if(!this.Search){
                if(this.FlightNumberAsString == null){
                    throw new Exception("Flight num missing");
                }
                else if(this.Source == null){
                    throw new Exception("Source airport is missing");
                }
                else if(this.DepartureDate == null){
                    throw new Exception("Departure date is missing");
                }
                else if(this.DepartureTime == null){
                    throw new Exception("Departure time is missing");
                }
                else if(this.Destination == null){
                    throw new Exception("Destination airport is missing");
                }
                else if(this.ArrivalTime == null){
                    throw new Exception("Arrival time is missing");
                }
                else if(this.ArrivalDate == null){
                    throw new Exception("Arrival date is missing");
                }
            }
        }
    }

    private boolean isReachableHost(String host, int port){
        try {
            try (Socket soc = new Socket()) {
                soc.connect(new InetSocketAddress(host, port)/*note that there can be a timeout time specified here*/);
            }
            return true;
        } catch (IOException ex) {
            return false;
        }
    }

    private boolean isInteger(String port){
        try {
            Integer.parseInt(port);
            return true;
        }catch(NumberFormatException e){
            return false;
        }
    }

    /**
     * A method to run all input check functions
     * @param departAirport user entered departure airport code
     * @param departTime user entered departure time
     * @param departDate user entered departure date
     * @param arrivalAirport user entered arrival airport code
     * @param arrivalTime user entered arrival time
     * @param arrivalDate user entered arrival date
     * @return a boolean signifying whether the program should continue, or if there was an error
     * parsing the input
     */
    private void checkValidInput(String departAirport, String departTime, String departDate,
                            String arrivalAirport, String arrivalTime, String arrivalDate) throws Exception
    {
        if(!isValidTime(departTime)){
            throw new Exception("\n" + departTime+" is not a valid departure time.\nValid Format: hh:mm\n");
        }

        if(!isValidTime(arrivalTime)){
            throw new Exception("\n" + arrivalTime+" is not a valid arrival time.\nValid Format: hh:mm\n");
        }

        if(!isValidDate(departDate)){
            throw new Exception("\n" + departDate +" is not a valid departure date.\nValid Format: mm/dd/yyyy\n");
        }

        if(!isValidDate(arrivalDate)){
            throw new Exception("\n" +arrivalDate +" is not a valid arrival date.\nValid Format: mm/dd/yyyy\n");
        }

        if(!isDepartureTimeNotAfterArrivalTime(departDate + " " + departTime, arrivalDate + " " + arrivalTime)){
            throw new Exception("\nDeparture time is after arrival time, exiting\n");
        }

        if(!isValidAirportCode(departAirport) || !isValidAirportCode(arrivalAirport)){
            throw new Exception("\nAirport Code must be 3 letters and no other characters\n");
        }
        else if(!doesAirportCodeExist(departAirport)){
            throw new Exception("\n" + departAirport + " is not a known airport, exiting");
        }
        else if(!doesAirportCodeExist(arrivalAirport)) {
            throw new Exception("\n" + arrivalAirport + " is not a known airport, exiting");
        }
    }

    @VisibleForTesting
    static boolean isDepartureTimeNotAfterArrivalTime(String depart, String arrive){
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
        Date DepartureDate = new Date();
        Date ArrivalDate = new Date();

        try {
            DepartureDate = formatter.parse(depart);
        }catch(Exception e){
            System.out.println("ERROR PARSING DEPARTURE DATE");
        }
        try {
            ArrivalDate = formatter.parse(arrive);
        }catch(Exception e){
            System.out.println("ERROR PARSING ARRIVAL DATE");
        }

        if(DepartureDate.before(ArrivalDate)){return true;}
        else{return false;}
    }

    private boolean isValidTime(String Time){
        try {
            SimpleDateFormat format = new SimpleDateFormat("hh:mm a");
            format.setLenient(false);
            format.parse(Time);
            return true;
        } catch (ParseException e) {
            return false;
        }

    }

    /**
     * A method to check if the user entered date is in valid format
     * @param Date a string containing the user input for date
     * @return a boolean signifying if the user entered date is in valid format
     */

    private boolean isValidDate(String Date){
        try {
            SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
            format.setLenient(false);
            format.parse(Date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    private boolean isValidAirportCode(String airportCode){
        boolean hasNonLetter = false;
        char [] array = airportCode.toCharArray();

        for(char c: array){
            if(!Character.isLetter(c)){
                System.out.println("\nNon letter found in airport code");
                hasNonLetter = true;
            }
        }

        if(airportCode.length() > 3){
            System.out.println("\nAirport code too long");
        }
        else if(airportCode.length() < 3){
            System.out.println("\nAirport code too short");
        }

        if(hasNonLetter || airportCode.length() != 3){
            return false;
        }
        else{
            return true;
        }
    }

    private boolean doesAirportCodeExist(String airportCode){ //write unit test
        if(AirportNames.getName(airportCode) != null) {return true;}
        else{return false;}
    }
}
