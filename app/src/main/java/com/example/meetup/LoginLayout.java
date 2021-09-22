package com.example.meetup;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class LoginLayout extends AppCompatActivity {
    EditText Email;
    EditText Password;
    Button LoginBtn;
    ImageView GoogleBtn;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_layout);
        Email = findViewById(R.id.editTextTextEmailAddress);
        Password = findViewById(R.id.editTextTextPassword);
        LoginBtn = findViewById(R.id.LoginBtn2);
        GoogleBtn = findViewById(R.id.GoogleImageview);

        LoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ValidateText())
                {
                    Toast.makeText(LoginLayout.this, "Login Authentication required", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

     Boolean ValidateText() {
         String email = Email.getText().toString().trim();
         String password = Password.getText().toString().trim();
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            Email.setError("Invalid Email ID");
            Email.requestFocus();
            return false;
        }
        else if (email.isEmpty())
        {
            Email.setError("Email Cannot be Empty");
            Email.requestFocus();
            return false;
        }
          else if(password.isEmpty())
        {
            Password.setError("Empty Field");
            Password.requestFocus();
            return false;
        }
        else if (password.length()<6)
        {
            Password.setError("Password cannot be less than 6 characters");
            Password.requestFocus();
            return false;
        }
        else {
            return true;
        }
    }
}