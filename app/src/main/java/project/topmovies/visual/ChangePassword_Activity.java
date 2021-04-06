package project.topmovies.visual;


import project.topmovies.*;
import project.topmovies.logic.statusApp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class ChangePassword_Activity extends AppCompatActivity {

    // VARIABLE //

    EditText
            editText_cCurrentPassword,
            editText_cNewPassword,
            editText_cConfirmNewPassword;

    Button button_ChangePassword;

    ProgressBar progressBar_ChangePassword;


    private FirebaseAuth mAuth;


    // ACTIVITY ACTIONS //

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.cpassword_layout);


        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();


        // Access to views
        editText_cCurrentPassword = findViewById(R.id.editText_cCurrentPassword);

        editText_cNewPassword = findViewById(R.id.editText_cNewPassword);
        editText_cConfirmNewPassword = findViewById(R.id.editText_cConfirmNewPassword);

        button_ChangePassword = findViewById(R.id.button_ChangePassword);

        progressBar_ChangePassword = findViewById(R.id.progressBar_ChangePassword);


        // Action Change Password
        button_ChangePassword.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String currentPassword = editText_cCurrentPassword.getText().toString();

                String newPassword = editText_cNewPassword.getText().toString();
                String confirmNewPassword = editText_cConfirmNewPassword.getText().toString();


                // Validation of the fields

                if (currentPassword.isEmpty()) {

                    editText_cCurrentPassword.setError(getString(R.string.etRequired));
                    editText_cCurrentPassword.requestFocus();
                    return;

                }

                else if (newPassword.isEmpty()) {

                    editText_cNewPassword.setError(getString(R.string.etRequired));
                    editText_cNewPassword.requestFocus();
                    return;

                }

                else if (newPassword.length() < 6) {

                    editText_cNewPassword.setError(getString(R.string.etMinPassword));
                    editText_cNewPassword.requestFocus();
                    return;

                }

                else if (!newPassword.equals(confirmNewPassword)) {

                    Toast.makeText(ChangePassword_Activity.this, R.string.problemMismatchPassword, Toast.LENGTH_LONG).show();

                }


                // After validation, the process of change the password start

                else {

                    progressBar_ChangePassword.setVisibility(View.VISIBLE);


                    // Get credentials of the actual user
                    AuthCredential credential = EmailAuthProvider.getCredential(mAuth.getCurrentUser().getEmail(), currentPassword);


                    // Reauthenticate the actual user
                    mAuth.getCurrentUser().reauthenticate(credential)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {

                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if (task.isSuccessful()) {

                                        // Update the password
                                        mAuth.getCurrentUser().updatePassword(newPassword)
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {

                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {

                                                        if (task.isSuccessful()) {

                                                            Toast.makeText(ChangePassword_Activity.this, R.string.changedPassword, Toast.LENGTH_LONG).show();


                                                            // Firebase sign out
                                                            mAuth.signOut();

                                                            statusApp.getInstance().loggedIn = false;

                                                            
                                                            finish();

                                                        }

                                                        else {

                                                            progressBar_ChangePassword.setVisibility(View.GONE);

                                                            Toast.makeText(ChangePassword_Activity.this, R.string.problemUpdatingPassword, Toast.LENGTH_LONG).show();

                                                        }

                                                    }

                                                });
                                    }

                                    else {

                                        progressBar_ChangePassword.setVisibility(View.GONE);

                                        Toast.makeText(ChangePassword_Activity.this, R.string.wrongPassword, Toast.LENGTH_LONG).show();

                                    }

                                }

                            });

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