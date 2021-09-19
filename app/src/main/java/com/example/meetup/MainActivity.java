package com.example.meetup;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    Button Login, Register;
    ImageView Logo, Gradient;
    float ytranslate= 1600;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Login = findViewById(R.id.LoginBtn);
        Register = findViewById(R.id.registerBtn);
        Logo =findViewById(R.id.logo);
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Logo.animate().translationY(-1000).alpha(1).setDuration(1000).setStartDelay(100).start();
                Login.animate().translationY(-2000).alpha(1).setDuration(1500).setStartDelay(100).start();
            }
        });

    }
}