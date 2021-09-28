package com.example.meetup;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.ActionCodeSettings;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class Register extends AppCompatActivity {
    private final int RC_SIGN_IN = 101;
    EditText NameEditTxt, EmailEditTxt, PasswordEditTxt, ConfirmPasswordEditTxt;
    Button RegisterBtn;
    ImageView LogoRegisterView, Google;
    LottieAnimationView loading;
    private FirebaseAuth auth;
    private GoogleSignInClient mGoogleSignInClient;


    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            loading.setVisibility(View.GONE);
                            FirebaseUser user = auth.getCurrentUser();
                            User user1 = new User(user.getDisplayName(), user.getEmail());
                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid())
                                    .setValue(user1).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {

                                        Toast.makeText(Register.this, "user registered", Toast.LENGTH_SHORT).show();
                                        updateUI(user);
                                    }
                                    else
                                        Toast.makeText(Register.this, "Registration Error", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                        else {
                            Toast.makeText(Register.this, "Authentication Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                Toast.makeText(Register.this, "Authentication Failed", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void updateUI(FirebaseUser user) {
        if (!user.getUid().isEmpty()) {
            startActivity(new Intent(Register.this,SelectInterests.class));

        }
        else
        {
            Toast.makeText(Register.this, "User Registration Failed", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        NameEditTxt = findViewById(R.id.NameEditTxt);
        EmailEditTxt = findViewById(R.id.EmailEditTxt);
        PasswordEditTxt = findViewById(R.id.PasswordEditTxt);
        ConfirmPasswordEditTxt = findViewById(R.id.ConfirmPasswordEditTxt);
        RegisterBtn = findViewById(R.id.registerBtn2);
        LogoRegisterView = findViewById(R.id.LogoRegisterView);
        Google = findViewById(R.id.GoogleImageview2);
        loading = findViewById(R.id.loadinganimation);
        // Create a request for Google Sign in

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("192895743829-p2i2dat02rngbpbcp795c10g7v0nj1g1.apps.googleusercontent.com")
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        auth = FirebaseAuth.getInstance();

        RegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Name = NameEditTxt.getText().toString().trim();
                String Email = EmailEditTxt.getText().toString().trim();
                String Password = PasswordEditTxt.getText().toString().trim();
                String ConfirmPassword = ConfirmPasswordEditTxt.getText().toString().trim();
                if (ValidateData(Name, Email, Password, ConfirmPassword)) {
                    RegisterUser(Name, Password, Email);
                    loading.setVisibility(View.VISIBLE);
                }
            }
        });


        Google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    loading.setVisibility(View.VISIBLE);
                    Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                    startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });
    }

    private void RegisterUser(String name, String password, String email) {
        try {
            auth = FirebaseAuth.getInstance();
            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task1) {
                    if (task1.isSuccessful()) {
                        User user = new User(name, email);
                        FirebaseDatabase.getInstance().getReference("Users")
                                .child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid())
                                .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    //email verification
                                        auth.getCurrentUser().sendEmailVerification();
                                        Toast.makeText(Register.this, "An email has been sent to you please check", Toast.LENGTH_SHORT).show();
                                        loading.setVisibility(View.GONE);
                                        startActivity(new Intent(Register.this,LoginLayout.class));

                                } else
                                    Toast.makeText(Register.this, "Registration Error", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    else {
                        Toast.makeText(Register.this, "User Registration Failed", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean ValidateData(String name, String email, String password, String confirmPassword) {
        if (name.isEmpty()) {
            NameEditTxt.setError("Name cannot be empty");
            NameEditTxt.requestFocus();
            return false;
        } else if (email.isEmpty()) {
            EmailEditTxt.setError("Email cannot be empty");
            EmailEditTxt.requestFocus();
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            EmailEditTxt.setError("Invalid EmailID");
            EmailEditTxt.requestFocus();
            return false;
        } else if (password.isEmpty()) {
            PasswordEditTxt.setError("Password cannot be empty");
            PasswordEditTxt.requestFocus();
            return false;
        } else if (password.length() < 6) {
            PasswordEditTxt.setError("Password cannot be less than 6 characters");
            PasswordEditTxt.requestFocus();
            return false;
        } else if (confirmPassword.isEmpty()) {
            ConfirmPasswordEditTxt.setError("Password cannot be empty");
            ConfirmPasswordEditTxt.requestFocus();
            return false;
        } else if (confirmPassword.compareTo(password) != 0) {
            ConfirmPasswordEditTxt.setError("Passwords do not Match");
            ConfirmPasswordEditTxt.requestFocus();
            return false;
        } else
            return true;
    }
}