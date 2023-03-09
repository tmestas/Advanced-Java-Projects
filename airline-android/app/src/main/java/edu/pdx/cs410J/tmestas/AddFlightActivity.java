package edu.pdx.cs410J.tmestas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.util.Objects;


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
        Integer flightNumber = Integer.parseInt(this.FlightNumber.getText().toString());
        String sourceAirport = this.Source.getText().toString();
        String departureDate = this.DepartureDate.getText().toString();
        String departureTime = this.DepartureTime.getText().toString();
        String destinationAirport = this.Destination.getText().toString();
        String arrivalDate = this.ArrivalDate.getText().toString();
        String arrivalTime = this.ArrivalTime.getText().toString();

        //check errors here

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

        finish();
    }

    public void onCancel(View view){
        finish();
    }

}
