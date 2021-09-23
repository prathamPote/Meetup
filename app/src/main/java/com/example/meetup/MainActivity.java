package com.example.meetup;

import androidx.appcompat.app.AppCompatActivity;
import android.util.Pair;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    Button Login, Register;
    ImageView Logo, Gradient;
    float ytranslate= 1600;
    int timeout = 1000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Login = findViewById(R.id.LoginBtn);
        Register = findViewById(R.id.registerBtn);
        Logo =findViewById(R.id.logo);
        Gradient=findViewById(R.id.loginGradient);


        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Gradient.animate().translationY(-3000).alpha(1).setDuration(1000).setStartDelay(100).start();
                Intent intent  = new Intent(MainActivity.this,LoginLayout.class);
                Pair<View,String>[] pairs= new Pair[2];
                pairs[0]= new Pair(Logo,"LogoTransition");
                pairs[1]= new Pair(Login,"LoginBtnTransition");
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this,pairs);
                startActivity(intent,options.toBundle());
            }
        });
        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Gradient.animate().translationY(-3000).alpha(1).setDuration(1000).setStartDelay(100).start();
            }
        });


    }

}