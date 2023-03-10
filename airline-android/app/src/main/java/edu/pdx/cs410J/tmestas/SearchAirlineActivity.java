package edu.pdx.cs410J.tmestas;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Objects;

import edu.pdx.cs410J.AirportNames;

public class SearchAirlineActivity extends AppCompatActivity {
    java.io.File File;
    EditText AirlineName;
    EditText SourceAirport;
    EditText DestinationAirport;
    TextView AirlineContents;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searchairline_main);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Search Airline");

        this.File = getFilesDir();
        this.AirlineName = findViewById(R.id.editTextSearchAirlineName);
        this.SourceAirport = findViewById(R.id.editTextSearchSource);
        this.DestinationAirport = findViewById(R.id.editTextSearchDestination);
        this.AirlineContents = findViewById(R.id.searchedAirlineContents);
    }

    public void onSubmitSearch(View view){

        String airlineName = this.AirlineName.getText().toString();
        String sourceAirport = this.SourceAirport.getText().toString();
        String destinationAirport = this.DestinationAirport.getText().toString();

        String [] toValidate = new String[]{airlineName, sourceAirport, destinationAirport};

        if(!validate(toValidate)){return;}

        String fileName = airlineName + ".txt";
        File newFile = new File(this.File, fileName);
        String content;

        if(newFile.exists()) {
            try {
                FileReader fr = new FileReader(newFile.getPath());
                TextParser parser = new TextParser(fr);
                Airline newAirline = parser.parse();
                Airline newAirline2 = new Airline(airlineName);

                for(Flight f: newAirline.getFlights()){
                    if(f.getSource().equals(sourceAirport) &&
                            f.getDestination().equals(destinationAirport)){
                        newAirline2.addFlight(f);
                    }
                }

                if(newAirline2.getFlights().size() > 0) {
                    StringWriter sr = new StringWriter();
                    PrintWriter pw = new PrintWriter(sr);
                    PrettyPrinter printer = new PrettyPrinter(pw);
                    printer.dump(newAirline2);
                    content = sr.toString();
                    this.AirlineContents.setText(content);
                    this.AirlineContents.setMovementMethod(new ScrollingMovementMethod());
                }else{
                    this.AirlineContents.setText(airlineName + " does not contain any flights between "
                    + sourceAirport + " and " + destinationAirport);
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }else{
            this.AirlineContents.setText(airlineName + " is not a stored airline");
        }
    }

    public void onCancelSearch(View view){
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
            this.SourceAirport.setError("Source airport required");
            valid = false;
        }else if(!isValidAirportCode(toValidate[1])){
            this.SourceAirport.setError("Source airport invalid format");
            valid = false;
        }else if(!doesAirportCodeExist(toValidate[1])){
            this.SourceAirport.setError("Source is not stored");
            valid = false;
        }else{
            this.SourceAirport.setError(null);
        }

        if(toValidate[2].isEmpty()){
            this.DestinationAirport.setError("Destination airport required");
            valid = false;
        }else if(!isValidAirportCode(toValidate[2])){
            this.DestinationAirport.setError("Destination airport invalid format");
            valid = false;
        }else if(!doesAirportCodeExist(toValidate[2])){
            this.DestinationAirport.setError("Destination is not stored");
            valid = false;
        }else{
            this.DestinationAirport.setError(null);
        }

        return valid;
    }

    public boolean checkAirlineNameCharacters(String airlineName){
        String[] invalidChars = new String[]{"<", ">", ":", "\"", "|", "?", "*", "\\\\", "//"};
        for (String i : invalidChars) {if(airlineName.contains(i)) {return false;}}
        return true;
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
}
