package com.example.meetup;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.android.material.chip.ChipGroup;

public class SelectInterests extends AppCompatActivity {
    ChipGroup sports;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_interests);
        sports = findViewById(R.id.SportsChipGroup);

    }
}