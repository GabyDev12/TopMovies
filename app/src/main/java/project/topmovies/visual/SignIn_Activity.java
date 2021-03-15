package project.topmovies.visual;


import project.topmovies.*;
import project.topmovies.logic.User;
import project.topmovies.logic.statusApp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;


public class SignIn_Activity extends AppCompatActivity {

    // VARIABLES //

    private static final int RC_SIGN_IN = 9001;


    EditText
            editText_Email,
            editText_Password;

    Button button_SignIn;

    Button button_gSignIn;

    ProgressBar progressBar_SignIn;


    private FirebaseAuth mAuth;

    private GoogleSignInClient mGoogleSignInClient;


    // ACTIVITY ACTIONS //

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin_layout);


        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Verify if user is signed in and then log it out
        if (mAuth.getCurrentUser() != null) {

            FirebaseAuth.getInstance().signOut();

        }


        // Access to views
        editText_Email = findViewById(R.id.editText_SI_Email);
        editText_Password = findViewById(R.id.editText_SI_Password);

        button_SignIn = findViewById(R.id.button_SI_SignIn);
        button_gSignIn = findViewById(R.id.button_SI_gSignIn);

        progressBar_SignIn = findViewById(R.id.progressBar_SignIn);


        // Action Sign In
        button_SignIn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String email = editText_Email.getText().toString();
                String password = editText_Password.getText().toString();


                // Validation of the fields

                if (email.isEmpty()) {

                    editText_Email.setError("Required");
                    editText_Email.requestFocus();
                    return;

                }

                else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {

                    editText_Email.setError("Format invalid");
                    editText_Email.requestFocus();
                    return;

                }

                else if (password.isEmpty()) {

                    editText_Password.setError("Required");
                    editText_Password.requestFocus();
                    return;

                }


                // After validation, the registration of the user start

                else {

                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {        // Check if the sign in is successful

                                    if (task.isSuccessful()) {

                                        progressBar_SignIn.setVisibility(View.VISIBLE);

                                        FirebaseDatabase.getInstance().getReference("users")
                                                .child(mAuth.getCurrentUser().getUid())
                                                .get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {

                                            @Override
                                            public void onComplete(@NonNull Task<DataSnapshot> task) {      // Check if the user information was loaded correctly

                                                if (task.isSuccessful()) {

                                                    statusApp.getInstance().actualUser = new User(
                                                            task.getResult().child("name").getValue(String.class),
                                                            task.getResult().child("lastName").getValue(String.class),
                                                            task.getResult().child("email").getValue(String.class));

                                                }

                                                else {

                                                    Toast.makeText(SignIn_Activity.this, "There was a problem loading the user data. Sorry", Toast.LENGTH_LONG).show();

                                                }

                                            }

                                        });

                                        // Save login
                                        statusApp.getInstance().loggedIn = true;


                                        // Return to HomeScreen_Activity
                                        Toast.makeText(SignIn_Activity.this, "Signed in successfully!", Toast.LENGTH_LONG).show();

                                        finish();

                                    }

                                    else {

                                        Toast.makeText(SignIn_Activity.this, "There was a problem\nPlease, check if the data introduced is correct", Toast.LENGTH_LONG).show();

                                    }

                                }

                            });

                }

            }

        });


        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("209581455260-2qkd36kiu1vi8gqsumbrqrn6o7noi8gf.apps.googleusercontent.com")
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);


        // Action Sign In with Google
        button_gSignIn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                progressBar_SignIn.setVisibility(View.VISIBLE);

                Intent signInIntent = mGoogleSignInClient.getSignInIntent();

                startActivityForResult(signInIntent, RC_SIGN_IN);

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

                // DEBUG LINE --> Log.d(SignIn_Activity.this, "firebaseAuthWithGoogle:" + account.getId(), Toast.LENGTH_LONG).show();

                firebaseAuthWithGoogle(account.getIdToken());

            } catch (ApiException e) {

                // Google Sign In failed, update UI appropriately
                Toast.makeText(SignIn_Activity.this, "Google sign in failed", Toast.LENGTH_LONG).show();

            }

        }

    }

    // Authentication with Google
    private void firebaseAuthWithGoogle(String idToken) {

        progressBar_SignIn.setVisibility(View.VISIBLE);

        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);

        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {        // Check if the sign in with Google is successful

                        if (task.isSuccessful()) {

                            // Save user data
                            statusApp.getInstance().actualUser = new User(mAuth.getCurrentUser().getEmail());


                            // Save Uid of Google user and the email in Firebase Database
                            // If the data exist, it do nothing
                            FirebaseDatabase.getInstance().getReference("users")
                                    .child(mAuth.getCurrentUser().getUid())
                                    .setValue(statusApp.getInstance().actualUser).addOnCompleteListener(new OnCompleteListener<Void>() {

                                @Override
                                public void onComplete(@NonNull Task<Void> task) {      // Check if the Google user information was stored in the DB

                                    if (!task.isSuccessful()) {

                                        Toast.makeText(SignIn_Activity.this, "There was a problem storing the user data. Sorry", Toast.LENGTH_LONG).show();

                                    }

                                }

                            });


                            // Save login
                            statusApp.getInstance().loggedIn = true;
                            statusApp.getInstance().gAuth = true;


                            // Return to HomeScreen_Activity
                            Toast.makeText(SignIn_Activity.this, "Signed in with Google successfully!", Toast.LENGTH_LONG).show();

                            finish();

                        } else {

                            // If sign in fails, display a message to the user.
                            Toast.makeText(SignIn_Activity.this, "There was a problem\nPlease, check if the data introduced is correct", Toast.LENGTH_LONG).show();

                        }

                    }

                });

    }


}