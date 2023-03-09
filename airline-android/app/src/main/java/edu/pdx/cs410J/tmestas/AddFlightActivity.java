package edu.pdx.cs410J.tmestas;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class AddFlightActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addflight_main);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Add Flight");
    }

}
