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
        Gradient = findViewById(R.id.gradient);
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Gradient.setTranslationY(300);
                Logo.setTranslationY(300);
                Gradient.setAlpha(ytranslate);
                Logo.setAlpha(ytranslate);
                Gradient.animate().translationY(0).alpha(1).setDuration(2000).setStartDelay(100).start();
                Logo.animate().translationY(0).alpha(1).setDuration(2000).setStartDelay(100).start();
            }
        });

    }
}