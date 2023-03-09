package edu.pdx.cs410J.tmestas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Airline");
    }

    public void onReadMe(View view){
        Intent readme = new Intent(this, ReadMeActivity.class);
        startActivity(readme);
    }

    public void onAirline(View view){
        Intent customer = new Intent(this, AirlineManagerActivity.class);
        startActivity(customer);
    }

}