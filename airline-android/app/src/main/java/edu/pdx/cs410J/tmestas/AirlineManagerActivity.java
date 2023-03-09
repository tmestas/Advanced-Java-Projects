package edu.pdx.cs410J.tmestas;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.util.Objects;

public class AirlineManagerActivity extends AppCompatActivity {


    private Button AddAirlineButton;
    private String AirlineName;
    private ArrayAdapter<String> Airlines;
    private File File;
    private ListView AirlineList;
    private TextView EmptyAirlineList;
    private TextView FlightManagerInstructions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.airlinemanager_main);
        Objects.requireNonNull(getSupportActionBar()).setTitle("AIRLINE MANAGER");

        this.File = getFilesDir();
        this.Airlines = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        this.AddAirlineButton = findViewById(R.id.addAirline);
        this.AirlineList = findViewById(R.id.airlineList);
        //this.EmptyAirlineList = findViewById();
        //this.FlightManagerInstructions = findViewById();
        //generateAirlineList()
        this.AirlineList.setAdapter(Airlines);

    }
}
