package edu.pdx.cs410J.tmestas;

import android.content.Intent;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.util.Objects;

public class AirlineManagerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.airlinemanager_main);
        Objects.requireNonNull(getSupportActionBar()).setTitle("AIRLINE MANAGER");
    }
    public void onAddFlight(View view){
        Intent addFlight = new Intent(this, AddFlightActivity.class);
        startActivity(addFlight);
    }

    public void onDisplayAirline(View view){
        Intent displayAirline = new Intent(this, DisplayAirlineActivity.class);
        startActivity(displayAirline);
    }

    public void onSearchAirline(View view){
        Intent searchAirline = new Intent(this, SearchAirlineActivity.class);
        startActivity(searchAirline);
    }
}
