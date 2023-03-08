package edu.pdx.cs410J.tmestas;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class Customer extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_main);
        Objects.requireNonNull(getSupportActionBar()).setTitle("CUSTOMER");
    }
}
