package com.example.meetup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;


public class ForgotPassword extends AppCompatActivity {
    //
    Button submit;
    EditText emailid;
    ImageView logo;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        submit = findViewById(R.id.submit_forgotpassword);
        emailid = findViewById(R.id.emailid_forgotpassword);
        logo = findViewById(R.id.logo_forgotpassword);

        auth = FirebaseAuth.getInstance();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ValidateText())
                {
                        auth.sendPasswordResetEmail(emailid.getText().toString().trim()).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                if(task.isSuccessful()){
                                    Toast.makeText(ForgotPassword.this, "Check your email to reset your password", Toast.LENGTH_LONG).show();
                                }
                                else{
                                    Toast.makeText(ForgotPassword.this, "Account Does not Exist", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                }
            }
        });





    }//


    Boolean ValidateText() {
        String email = emailid.getText().toString().trim();
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
           emailid.setError("Invalid Email ID");
            emailid.requestFocus();
            return false;
        }
        else if (email.isEmpty())
        {
          emailid.setError("Email Cannot be Empty");
          emailid.requestFocus();
            return false;

        }
        else return true;


    }
}