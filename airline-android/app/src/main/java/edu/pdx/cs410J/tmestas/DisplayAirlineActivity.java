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

public class DisplayAirlineActivity extends AppCompatActivity {

    File File;
    EditText AirlineName;
    TextView AirlineContents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.displayairline_main);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Display Airline");

        this.File = getFilesDir();
        this.AirlineName = findViewById(R.id.editTextAirline);
        this.AirlineContents = findViewById(R.id.airlineContent);
    }

    public void onSubmitDisplay(View view){

        String airlineName = this.AirlineName.getText().toString();
        String fileName = airlineName + ".txt";
        File newFile = new File(this.File, fileName);
        String content;

        if(newFile.exists()) {
            try {
                FileReader fr = new FileReader(newFile.getPath());
                TextParser parser = new TextParser(fr);
                Airline newAirline = parser.parse();
                StringWriter sr = new StringWriter();
                PrintWriter pw = new PrintWriter(sr);
                PrettyPrinter printer = new PrettyPrinter(pw);
                printer.dump(newAirline);
                content = sr.toString();
                this.AirlineContents.setText(content);
                this.AirlineContents.setMovementMethod(new ScrollingMovementMethod());
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }else{
            this.AirlineContents.setText(airlineName + " is not a stored airline");
        }
    }

    public void onCancelDisplay(View view){
        finish();
    }
}
