package project.topmovies.visual;


import project.topmovies.*;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class SignIn_Activity extends AppCompatActivity {

    // VARIABLES //

    EditText
            editText_Email,
            editText_Password;

    Button button_SignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin_layout);

        // Access to views
        editText_Email = findViewById(R.id.editText_SI_Email);
        editText_Password = findViewById(R.id.editText_SI_Password);

        button_SignIn = findViewById(R.id.button_SI_SignIn);


        // Action Sign In
        button_SignIn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {



            }

        });

    }

}