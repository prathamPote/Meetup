package com.example.meetup;

import androidx.appcompat.app.AppCompatActivity;

import android.media.ExifInterface;
import android.os.Handler;
import android.util.Pair;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onStart() {
        super.onStart();
        if(auth.getCurrentUser()!=null)
        {
            startActivity(new Intent(MainActivity.this,HomeScreen.class));
        }
    }

    Button Login, Register;
    ImageView Logo, Gradient;
    float ytranslate= 1600;
    int timeout = 1000;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Login = findViewById(R.id.LoginBtn);
        Register = findViewById(R.id.registerBtn);
        Logo =findViewById(R.id.logo);
        Gradient=findViewById(R.id.loginGradient);
        Gradient.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(100).start();
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Gradient.animate().translationY(-3000).alpha(1).setDuration(300).setStartDelay(0).start();
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
                Intent intent  = new Intent(MainActivity.this,Register.class);
                Pair<View,String>[] pairs= new Pair[2];
                pairs[0]= new Pair(Logo,"LogoTransition");
                pairs[1]= new Pair(Register,"RegisterBtnTransition");
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this,pairs);
                startActivity(intent,options.toBundle());
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        Gradient.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(100).start();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finishAffinity();
    }
}