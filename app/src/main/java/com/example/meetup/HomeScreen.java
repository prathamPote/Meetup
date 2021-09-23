package com.example.meetup;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class HomeScreen extends AppCompatActivity {

    TextView textView;
    Button logout;
    FirebaseAuth auth;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finishAffinity();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        textView = findViewById(R.id.textView2);
        logout = findViewById(R.id.Logoutbutton);
        auth = FirebaseAuth.getInstance();
        textView.setText("Hello "+auth.getCurrentUser().getEmail());
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth.signOut();
                startActivity(new Intent(HomeScreen.this,MainActivity.class));
            }
        });
    }
}