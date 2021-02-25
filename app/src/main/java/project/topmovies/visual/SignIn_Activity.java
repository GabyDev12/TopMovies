package project.topmovies.visual;


import project.topmovies.*;
import project.topmovies.logic.User;
import static project.topmovies.logic.statusApp.*;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;

import static project.topmovies.logic.statusApp.loggedIn;


public class SignIn_Activity extends AppCompatActivity {

    // VARIABLES //

    EditText
            editText_Email,
            editText_Password;

    Button button_SignIn;

    ProgressBar progressBar_SignIn;


    private FirebaseAuth mAuth;


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

                                                    actualUser = new User(
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
                                        loggedIn = true;


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

    }

}