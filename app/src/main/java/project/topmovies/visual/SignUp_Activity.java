package project.topmovies.visual;


import project.topmovies.*;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;


public class SignUp_Activity extends AppCompatActivity {

    // VARIABLES //

    EditText
            editText_Name,
            editText_LastName,
            editText_Email,
            editText_Password,
            editText_ConfirmPassword;

    Button button_SignUp;


    // ACTIVITY ACTIONS //

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_layout);

        // Access to views
        editText_Name = findViewById(R.id.editText_SU_Name);
        editText_LastName = findViewById(R.id.editText_SU_LastName);

        editText_Email = findViewById(R.id.editText_SU_Email);
        editText_Password = findViewById(R.id.editText_SU_Password);
        editText_ConfirmPassword = findViewById(R.id.editText_SU_ConfirmPassword);

        button_SignUp = findViewById(R.id.button_SU_SignUp);

    }

}