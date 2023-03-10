package edu.pdx.cs410J.tmestas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import edu.pdx.cs410J.AirportNames;


public class AddFlightActivity extends AppCompatActivity {
    private EditText AirlineName;
    private EditText FlightNumber;
    private EditText Source;
    private EditText DepartureDate;
    private EditText DepartureTime;
    private EditText Destination;
    private EditText ArrivalDate;
    private EditText ArrivalTime;
    private File File;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addflight_main);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Add Flight");

        this.File = getFilesDir();
        this.AirlineName = findViewById(R.id.editTextAirlineName);
        this.FlightNumber = findViewById(R.id.editTextFlightNum);
        this.Source = findViewById(R.id.editTextSourceAirport);
        this.DepartureDate = findViewById(R.id.editTextDepartDate);
        this.DepartureTime = findViewById(R.id.editTextDepartTime);
        this.Destination = findViewById(R.id.editTextArrivalAirport);
        this.ArrivalDate = findViewById(R.id.editTextArrivalDate);
        this.ArrivalTime = findViewById(R.id.editTextArrivalTime);
    }

    public void onSubmit(View view){
        Intent current = new Intent();

        File newFile;
        String path;

        String airlineName = this.AirlineName.getText().toString();
        String flightNumAsString = this.FlightNumber.getText().toString();
        String sourceAirport = this.Source.getText().toString();
        String departureDate = this.DepartureDate.getText().toString();
        String departureTime = this.DepartureTime.getText().toString();
        String destinationAirport = this.Destination.getText().toString();
        String arrivalDate = this.ArrivalDate.getText().toString();
        String arrivalTime = this.ArrivalTime.getText().toString();

        String [] toValidate = new String []{airlineName, flightNumAsString, sourceAirport,
                departureDate, departureTime, destinationAirport, arrivalDate, arrivalTime};

        if(!validate(toValidate)){
            return;
        }

        //check errors here
        Integer flightNumber = Integer.parseInt(this.FlightNumber.getText().toString());
        String departureDateTime = departureDate + " " + departureTime;
        String arrivalDateTime = arrivalDate + " " + arrivalTime;

        String fileName = airlineName + ".txt";
        newFile = new File(this.File, fileName);
        boolean created = false;

        if(!newFile.exists()){

            Airline newAirline = new Airline(airlineName);
            Flight newFlight = new Flight(flightNumber, sourceAirport, departureDateTime, destinationAirport, arrivalDateTime);
            newAirline.addFlight(newFlight);

            try {
                created = newFile.createNewFile();
            }catch(Exception e){
                System.out.println("Error creating file"); //temporary
            }

            if(created) { //if file was successfully created
                try {
                    FileWriter fw = new FileWriter(newFile.getPath());
                    PrintWriter pw = new PrintWriter(fw);
                    TextDumper dumper = new TextDumper(pw);
                    dumper.dump(newAirline); //dump airline to created file (might break)
                } catch (IOException e) {
                    System.out.println("Error dumping to the file"); //temporary
                    finish();
                }
            }else{ //if file was not successfully created
                finish();
            }

        }else{ //if the file does exist
            FileReader fr;
            TextParser parser;
            Airline newAirline;
            Flight newFlight = new Flight(flightNumber, sourceAirport, departureDateTime, destinationAirport, arrivalDateTime);

            try {
                fr = new FileReader(newFile.getPath());
                parser = new TextParser(fr);
                newAirline = parser.parse();
                newAirline.addFlight(newFlight);    //add newly created flight

                FileWriter fw = new FileWriter(newFile.getPath());
                PrintWriter pw = new PrintWriter(fw);
                TextDumper dumper = new TextDumper(pw);
                dumper.dump(newAirline);            //write back to the file we read from

                /*
                PrintWriter pw2 = new PrintWriter(System.out); //for testing only
                PrettyPrinter printer = new PrettyPrinter(pw2);
                printer.dump(newAirline);
                */

            }catch(FileNotFoundException e){
                System.out.println("File not found");
            }catch(Exception e){
                System.out.println(e.getMessage()); //error from parser
            }

        }

        this.AirlineName.setText("");
        this.FlightNumber.setText("");
        this.Source.setText("");
        this.DepartureDate.setText("");
        this.DepartureTime.setText("");
        this.Destination.setText("");
        this.ArrivalDate.setText("");
        this.ArrivalTime.setText("");

        //add toast thing
    }

    public void onCancel(View view){
        finish();
    }

    private boolean validate(String[] toValidate){

        boolean valid = true;

        if(toValidate[0].isEmpty()){
            this.AirlineName.setError("Airline name required");
            valid = false;
        }else if(!checkAirlineNameCharacters(toValidate[0])){
            this.AirlineName.setError("Invalid characters found");
            valid = false;
        }else{
            this.AirlineName.setError(null);
        }

        if(toValidate[1].isEmpty()){
            this.FlightNumber.setError("Flight number required");
            valid = false;
        }else{
            this.FlightNumber.setError(null);
        }

        if(toValidate[2].isEmpty()){
            this.Source.setError("Source airport required");
            valid = false;
        }else if(!isValidAirportCode(toValidate[2])){
            this.Source.setError("Source airport invalid format");
            valid = false;
        }else if(!doesAirportCodeExist(toValidate[2])){
            this.Source.setError("Source is not stored");
            valid = false;
        }else{
            this.Source.setError(null);
        }

        if(toValidate[3].isEmpty()){
            this.DepartureDate.setError("Departure date required");
            valid = false;
        }else if(!isValidDate(toValidate[3])){
            this.DepartureDate.setError("Departure date invalid format");
            valid = false;
        }else{
            this.DepartureDate.setError(null);
        }

        if(toValidate[4].isEmpty()){
            this.DepartureTime.setError("Departure time required");
            valid = false;
        }else if(!isValidTime(toValidate[4])){
            this.DepartureTime.setError("Departure time invalid format");
            valid = false;
        }else{
            this.DepartureTime.setError(null);
        }

        if(toValidate[5].isEmpty()){
            this.Destination.setError("Destination airport required");
            valid = false;
        }else if(!isValidAirportCode(toValidate[5])){
            this.Destination.setError("Destination airport invalid format");
            valid = false;
        }else if(!doesAirportCodeExist(toValidate[5])){
            this.Destination.setError("Destination is not stored");
            valid = false;
        }else{
            this.Destination.setError(null);
        }

        if(toValidate[6].isEmpty()){
            this.ArrivalDate.setError("Arrival date required");
            valid = false;
        }else if(!isValidDate(toValidate[6])){
            this.ArrivalDate.setError("Arrival date invalid format");
            valid = false;
        }else{
            this.ArrivalDate.setError(null);
        }

        if(toValidate[7].isEmpty()){
            this.ArrivalTime.setError("Arrival time required");
            valid = false;
        }else if(!isValidTime(toValidate[7])){
            this.ArrivalTime.setError("Arrival time invalid format");
            valid = false;
        }else if(!isDepartureTimeNotAfterArrivalTime(toValidate[3] + " " + toValidate[4],
                toValidate[6] + " " + toValidate[7])){
            this.ArrivalDate.setError("Arrival date/time after departure date/time");
            this.ArrivalTime.setError("Arrival date/time after departure date/time");
            valid = false;
        }
        else{
            this.ArrivalTime.setError(null);
        }

        return valid;
    }

    public boolean checkAirlineNameCharacters(String airlineName){
        String[] invalidChars = new String[]{"<", ">", ":", "\"", "|", "?", "*", "\\\\", "//"};
        for (String i : invalidChars) {if(airlineName.contains(i)) {return false;}}
        return true;
    }

    public boolean isValidDate(String Date){
        try {
            SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
            format.setLenient(false);
            format.parse(Date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    public boolean isValidTime(String Time){

        try {
            SimpleDateFormat format = new SimpleDateFormat("hh:mm a");
            format.setLenient(false);
            Date time = format.parse(Time);
            //if(time == undefined)
            return true;
        } catch (ParseException e) {
            return false;
        }

    }

    public boolean isValidAirportCode(String airportCode){
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

    public boolean doesAirportCodeExist(String airportCode){
        if(AirportNames.getName(airportCode) != null) {return true;}
        else{return false;}
    }

    public static boolean isDepartureTimeNotAfterArrivalTime(String depart, String arrive){
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

}
