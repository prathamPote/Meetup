package com.example.meetup;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class LoginLayout extends AppCompatActivity {
    private final int RC_SIGN_IN = 100;
    EditText Email;
    EditText Password;
    Button LoginBtn;
    ImageView GoogleBtn,Logo;
    String email;
    String password;
    TextView forgotpassword;
    LottieAnimationView loadinganimation;
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
                            loadinganimation.setVisibility(View.GONE);
                            FirebaseUser user = auth.getCurrentUser();
                            NavigateToHome();

                        } else {
                            Toast.makeText(LoginLayout.this, "Authentication Failed", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(LoginLayout.this, "Authentication Failed", Toast.LENGTH_SHORT).show();
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_layout);
        Email = findViewById(R.id.editTextTextEmailAddress);
        Logo = findViewById(R.id.Logo2);
        Password = findViewById(R.id.editTextTextPassword);
        LoginBtn = findViewById(R.id.LoginBtn2);
        GoogleBtn = findViewById(R.id.GoogleImageview);
        forgotpassword= findViewById(R.id.textView);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("192895743829-p2i2dat02rngbpbcp795c10g7v0nj1g1.apps.googleusercontent.com")
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        auth = FirebaseAuth.getInstance();
        loadinganimation = findViewById(R.id.LottieLoading);
        forgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  = new Intent(LoginLayout.this,ForgotPassword.class);
                Pair<View,String>[] pairs= new Pair[1];
                pairs[0]= new Pair(Logo,"LogoTransition");
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(LoginLayout.this,pairs);
                startActivity(intent,options.toBundle());
            }
        });
        LoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ValidateText()) {
                    loadinganimation.setVisibility(View.VISIBLE);
                    auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                if (auth.getCurrentUser().isEmailVerified()) {
                                    loadinganimation.setVisibility(View.GONE);
                                    NavigateToHome();
                                    Toast.makeText(LoginLayout.this, "Login Successful", Toast.LENGTH_LONG).show();
                                } else {
                                    loadinganimation.setVisibility(View.GONE);
                                    Toast.makeText(LoginLayout.this, "Please verify your email", Toast.LENGTH_LONG).show();
                                }

                            } else {
                                Password.setError("Invalid Username/password");
                                Password.requestFocus();
                            }
                        }
                    });

                }

            }
        });

        GoogleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadinganimation.setVisibility(View.VISIBLE);
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });

    }

    private void NavigateToHome() {
        Intent intent = new Intent(LoginLayout.this, HomeScreen.class);
        startActivity(intent);
    }

    Boolean ValidateText() {
        email = Email.getText().toString().trim();
        password = Password.getText().toString().trim();
        if (email.isEmpty()) {
            Email.setError("Email Cannot be Empty");
            Email.requestFocus();
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Email.setError("Invalid Email ID");
            Email.requestFocus();
            return false;
        } else if (password.isEmpty()) {
            Password.setError("Empty Field");
            Password.requestFocus();
            return false;
        } else if (password.length() < 6) {
            Password.setError("Password cannot be less than 6 characters");
            Password.requestFocus();
            return false;
        } else {
            return true;
        }
    }

}