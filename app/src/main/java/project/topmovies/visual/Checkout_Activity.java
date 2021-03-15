package project.topmovies.visual;


import project.topmovies.*;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;


public class Checkout_Activity extends AppCompatActivity {

    // VARIABLES //

    private String selectedMovieTitle;

    private String dateSelected;

    private String timeSelected;

    private List<String> seatsSelected;

    private static double ticketPrice = 2.50;


    TextView textView_MovieTitle;

    TextView textView_Date;
    TextView textView_Time;

    TextView textView_Tickets;
    TextView textView_Seats;

    TextView textView_FinalPrice;

    Button button_Buy;


    // ACTIVITY ACTIONS //

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.checkout_layout);


        // Load the movie object, selected time an amount of seats
        selectedMovieTitle = (String) getIntent().getSerializableExtra("MOVIE");
        dateSelected = (String) getIntent().getSerializableExtra("DATE");
        timeSelected = (String) getIntent().getSerializableExtra("TIME");
        seatsSelected = (List<String>) getIntent().getSerializableExtra("SEATS");


        // Configuration for Toolbar
        ActionBar toolBar = getSupportActionBar();

        toolBar.setTitle("Checkout");


        // Access to views
        textView_MovieTitle = findViewById(R.id.textView_MovieTitle);

        textView_Date = findViewById(R.id.textView_Date);
        textView_Time = findViewById(R.id.textView_Time);

        textView_Tickets = findViewById(R.id.textView_Tickets);
        textView_Seats = findViewById(R.id.textView_Seats);

        textView_FinalPrice = findViewById(R.id.textView_FinalPrice);

        button_Buy = findViewById(R.id.button_Buy);


        // SHOW DATA //

        textView_MovieTitle.setText(selectedMovieTitle);

        textView_Date.setText(dateSelected);
        textView_Time.setText(timeSelected);

        int amountOfTickets = seatsSelected.size();
        textView_Tickets.setText(String.valueOf(amountOfTickets));

        String seats = "";

        for (int i = 0; i < seatsSelected.size(); i++) {

            if (i == seatsSelected.size() - 1) {

                seats = seats + seatsSelected.get(i);

            }

            else  {

                seats = seats + seatsSelected.get(i) + ", ";

            }

        }

        textView_Seats.setText(seats);

        double finalPrice = Double.valueOf(amountOfTickets) * ticketPrice;
        textView_FinalPrice.setText(String.valueOf(finalPrice) + " €");


        // Action buy tickets
        button_Buy.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intentTicketsBought = new Intent(Checkout_Activity.this, TicketsBought_Activity.class);

                // Pass movie object, selected time an amount of seats to the new activity
                intentTicketsBought.putExtra("MOVIE", selectedMovieTitle);
                intentTicketsBought.putExtra("DATE", dateSelected);
                intentTicketsBought.putExtra("TIME", timeSelected);
                intentTicketsBought.putExtra("TICKETS", amountOfTickets);
                intentTicketsBought.putExtra("PRICE", finalPrice);

                startActivity(intentTicketsBought);

            }

        });

    }

}