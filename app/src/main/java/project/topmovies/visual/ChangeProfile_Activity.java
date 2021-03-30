package project.topmovies.visual;


import project.topmovies.*;
import project.topmovies.logic.statusApp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class ChangeProfile_Activity extends AppCompatActivity {

    // VARIABLE //

    ImageView imageView_cpUserImage;

    EditText
            editText_cName,
            editText_cLastName,
            editText_cEmail;

    Button button_UpdateProfile;

    ProgressBar progressBar_ChangeProfile;


    private String currentUserName;
    private String currentUserLastName;

    private boolean successfulUpdate = true;


    private FirebaseAuth mAuth;

    private DatabaseReference userRef;


    // ACTIVITY ACTIONS //

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.cprofile_layout);


        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();


        // Get reference of user data in Firebase
        userRef = FirebaseDatabase.getInstance().getReference("users").child(mAuth.getCurrentUser().getUid());


        // Access to views
        imageView_cpUserImage = findViewById(R.id.imageView_cpUserImage);

        editText_cName = findViewById(R.id.editText_cName);
        editText_cLastName = findViewById(R.id.editText_cLastName);

        editText_cEmail = findViewById(R.id.editText_cEmail);

        button_UpdateProfile = findViewById(R.id.button_UpdateProfile);

        progressBar_ChangeProfile = findViewById(R.id.progressBar_ChangeProfile);


        // Load user data
        userRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                // Save current user data in local variables
                currentUserName = snapshot.child("name").getValue(String.class);
                currentUserLastName = snapshot.child("lastName").getValue(String.class);

                // Set current user data on the activity
                editText_cName.setText(currentUserName);
                editText_cLastName.setText(currentUserLastName);

                editText_cEmail.setText(mAuth.getCurrentUser().getEmail());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(ChangeProfile_Activity.this, "There was a problem loading the user data. Sorry", Toast.LENGTH_LONG).show();

            }

        });


        // Action change profile image
        imageView_cpUserImage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Toast.makeText(ChangeProfile_Activity.this, "Patata", Toast.LENGTH_LONG).show();

            }

        });


        // Action Update Profile
        button_UpdateProfile.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String name = editText_cName.getText().toString();
                String lastName = editText_cLastName.getText().toString();

                String email = editText_cEmail.getText().toString();


                // Validation of the fields

                if (name.isEmpty()) {

                    editText_cName.setError("Required");
                    editText_cName.requestFocus();
                    return;

                }

                else if (lastName.isEmpty()) {

                    editText_cLastName.setError("Required");
                    editText_cLastName.requestFocus();
                    return;

                }

                else if (email.isEmpty()) {

                    editText_cEmail.setError("Required");
                    editText_cEmail.requestFocus();
                    return;

                }

                else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {

                    editText_cEmail.setError("Format invalid");
                    editText_cEmail.requestFocus();
                    return;

                }


                // After validation, the update of the user data start

                else {

                    progressBar_ChangeProfile.setVisibility(View.VISIBLE);


                    // If the Name is different from the actual
                    if (!currentUserName.equals(name)) {

                        // Update the user information stored in the DB
                        userRef.child("name").setValue(name).addOnCompleteListener(new OnCompleteListener<Void>() {

                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                if (task.isSuccessful()) {

                                    // Successfully updated!

                                }

                                else {

                                    successfulUpdate = false;

                                    Toast.makeText(ChangeProfile_Activity.this, "There was a problem updating the name in the DB. Sorry", Toast.LENGTH_LONG).show();

                                }

                            }

                        });

                    }


                    // If the Name is different from the actual
                    if (!currentUserLastName.equals(lastName)) {

                        // Update the user information stored in the DB
                        userRef.child("lastName").setValue(lastName).addOnCompleteListener(new OnCompleteListener<Void>() {

                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                if (task.isSuccessful()) {

                                    // Successfully updated!

                                }

                                else {

                                    successfulUpdate = false;

                                    Toast.makeText(ChangeProfile_Activity.this, "There was a problem updating the last name in the DB. Sorry", Toast.LENGTH_LONG).show();

                                }

                            }

                        });

                    }


                    // If the Email is different from the actual
                    if (!mAuth.getCurrentUser().getEmail().equals(email)) {

                        // Update the email for login
                        mAuth.getCurrentUser().updateEmail(email)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {

                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        if (task.isSuccessful()) {

                                            // Update the user information stored in the DB
                                            userRef.child("email").setValue(email).addOnCompleteListener(new OnCompleteListener<Void>() {

                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {

                                                    if (task.isSuccessful()) {

                                                        // Successfully updated!

                                                    }

                                                    else {

                                                        successfulUpdate = false;

                                                        Toast.makeText(ChangeProfile_Activity.this, "There was a problem updating the email in the DB. Sorry", Toast.LENGTH_LONG).show();

                                                    }

                                                }

                                            });

                                        }

                                        else {

                                            successfulUpdate = false;

                                            Toast.makeText(ChangeProfile_Activity.this, "There was a problem updating the email. Sorry", Toast.LENGTH_LONG).show();

                                        }

                                    }

                                });

                    }

                }

                // If there are no problems, return to settings in HomeScreen_Activity
                if (successfulUpdate) {

                    Toast.makeText(ChangeProfile_Activity.this, "Updated successfully!", Toast.LENGTH_LONG).show();

                    statusApp.getInstance().settings = true;

                    finish();

                }

            }

        });

    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();

        statusApp.getInstance().settings = true;

    }

}