package edu.pdx.cs410J.tmestas;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class AirlineManager extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.airlinemanager_main);
        Objects.requireNonNull(getSupportActionBar()).setTitle("AIRLINE MANAGER");
    }
}
