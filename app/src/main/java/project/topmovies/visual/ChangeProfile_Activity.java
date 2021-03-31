package project.topmovies.visual;


import project.topmovies.*;
import project.topmovies.logic.statusApp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;


public class ChangeProfile_Activity extends AppCompatActivity {

    // VARIABLE //

    ImageView imageView_cpUserImage;

    EditText
            editText_cName,
            editText_cLastName,
            editText_cEmail;

    Button button_UpdateProfile;

    ProgressBar progressBar_ChangeProfile;


    Uri imageSelected;

    private String currentUserName;
    private String currentUserLastName;

    private boolean successfulUpdate = true;


    private FirebaseAuth mAuth;

    private DatabaseReference userRef;

    private StorageReference storageRef;


    // ACTIVITY ACTIONS //

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.cprofile_layout);


        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();


        // Get reference of user data in Firebase
        userRef = FirebaseDatabase.getInstance().getReference("users").child(mAuth.getCurrentUser().getUid());


        // Get reference of user in Firebase Storage
        storageRef = FirebaseStorage.getInstance().getReference("users").child(mAuth.getCurrentUser().getUid());


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


        // Load the profile image of the user
        if (mAuth.getCurrentUser().getPhotoUrl() != null) {

            Picasso.with(ChangeProfile_Activity.this)
                    .load(mAuth.getCurrentUser().getPhotoUrl())
                    .fit()
                    .centerCrop()
                    .placeholder(R.drawable.img_loading)
                    .into(imageView_cpUserImage);

        }


        // Action change profile image
        imageView_cpUserImage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // Select a image from the gallery
                Intent intentOpenGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(intentOpenGallery, 1000);

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

                                    progressBar_ChangeProfile.setVisibility(View.GONE);

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

                                    progressBar_ChangeProfile.setVisibility(View.GONE);

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

                                                        progressBar_ChangeProfile.setVisibility(View.GONE);

                                                        Toast.makeText(ChangeProfile_Activity.this, "There was a problem updating the email in the DB. Sorry", Toast.LENGTH_LONG).show();

                                                    }

                                                }

                                            });

                                        }

                                        else {

                                            successfulUpdate = false;

                                            progressBar_ChangeProfile.setVisibility(View.GONE);

                                            Toast.makeText(ChangeProfile_Activity.this, "There was a problem updating the email. Try again later. Sorry", Toast.LENGTH_LONG).show();

                                        }

                                    }

                                });

                    }

                }


                // Upload the image to Firebase
                StorageReference fileRef = storageRef.child("profile-Image.jpg");

                fileRef.putFile(imageSelected)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {

                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                // The image was uploaded to Firebase successfully!


                                // Link the image URL with the user profile image
                                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {

                                    @Override
                                    public void onSuccess(Uri uri) {

                                        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                                .setPhotoUri(uri)
                                                .build();

                                        mAuth.getCurrentUser().updateProfile(profileUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {

                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {

                                                if (task.isSuccessful()) {

                                                    // Profile image updated successfully!

                                                }

                                                else {

                                                    successfulUpdate = false;

                                                    progressBar_ChangeProfile.setVisibility(View.GONE);

                                                    Toast.makeText(ChangeProfile_Activity.this, "There was a problem updating the profile image. Try again later. Sorry", Toast.LENGTH_LONG).show();

                                                }

                                            }

                                        });

                                    }

                                });

                            }

                        })

                        .addOnFailureListener(new OnFailureListener() {

                            @Override
                            public void onFailure(@NonNull Exception e) {

                                successfulUpdate = false;

                                progressBar_ChangeProfile.setVisibility(View.GONE);

                                Toast.makeText(ChangeProfile_Activity.this, "There was a problem uploading the image. Try again later. Sorry", Toast.LENGTH_LONG).show();

                            }

                        });


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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1000) {

            // If a image from the gallery was selected
            if (resultCode == Activity.RESULT_OK) {

                // Get the image an load it
                imageSelected = data.getData();

                Picasso.with(ChangeProfile_Activity.this)
                        .load(imageSelected)
                        .fit()
                        .centerCrop()
                        .into(imageView_cpUserImage);

            }

        }

    }

}