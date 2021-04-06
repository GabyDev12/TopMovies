package project.topmovies.visual;


import project.topmovies.*;
import project.topmovies.logic.DatePickerFragment;
import project.topmovies.logic.Movie;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class Booking_Activity extends AppCompatActivity implements View.OnClickListener {

    // VARIABLES //

    private String selectedMovieTitle;

    Button button_DateSelection;
    TextView textView_MovieDateSelected;
    private String dateSelected;


    Spinner spinner_MovieTimes;
    private String timeSelected;


    ViewGroup layout;

    String seats = "/_UUAAAU_/"
            + "________/"
            + "U_AAUU_U/"
            + "A_AAAU_A/"
            + "U_UAAA_A/"
            + "A_UUAA_U/"
            + "________/"
            + "A_AAAA_A/";

    List<TextView> seatViewList = new ArrayList<>();
    int seatSize = 85;
    int seatGaping = 10;

    int STATUS_AVAILABLE = 1;
    int STATUS_BOOKED = 2;
    String selectedIds = "";

    private List<String> seatsSelected;


    Button button_Checkout;


    // ACTIVITY ACTIONS //

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.booking_layout);


        // Load movie object
        selectedMovieTitle = (String) getIntent().getSerializableExtra("MOVIE");


        // Configuration for Toolbar
        ActionBar toolBar = getSupportActionBar();

        toolBar.setTitle(R.string.titleBooking);


        // Access to views
        button_DateSelection = findViewById(R.id.button_DateSelection);
        textView_MovieDateSelected = findViewById(R.id.textView_MovieDateSelected);

        spinner_MovieTimes = findViewById(R.id.spinner_MovieTimes);

        button_Checkout = findViewById(R.id.button_Checkout);


        // Action button DateSelection
        button_DateSelection.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                        // +1 because January is zero
                        final String selectedDate = year + "-" + (month + 1) + "-" + day;

                        textView_MovieDateSelected.setText(selectedDate);

                        dateSelected = selectedDate;

                    }

                });

                newFragment.show(getSupportFragmentManager(), "datePicker");

            }

        });


        // Spinner Movie Times
        spinner_MovieTimes.setOnItemSelectedListener((new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {

                ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);
                ((TextView) parent.getChildAt(0)).setTextSize(16);

                switch (parent.getItemAtPosition(position).toString()) {

                    case "17:15": timeSelected = "17:15"; break;
                    case "17:45": timeSelected = "17:45"; break;
                    case "18:00": timeSelected = "18:00"; break;
                    case "19:35": timeSelected = "19:35"; break;
                    case "20:15": timeSelected = "20:15"; break;
                    case "20:45": timeSelected = "20:45"; break;

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) { }

        }));


        // Seat Selection

        seatsSelected = new ArrayList<>();

        layout = findViewById(R.id.layoutSeat);

        LinearLayout layoutSeat = new LinearLayout(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutSeat.setOrientation(LinearLayout.VERTICAL);
        layoutSeat.setLayoutParams(params);
        layoutSeat.setPadding(seatGaping, seatGaping, seatGaping, seatGaping);
        layout.addView(layoutSeat);

        LinearLayout layout = null;

        // Count for the seats number
        int count = 0;

        // Create the seats like the pattern
        for (int index = 0; index < seats.length(); index++) {

            if (seats.charAt(index) == '/') {               // Edges of each row

                layout = new LinearLayout(this);
                layout.setOrientation(LinearLayout.HORIZONTAL);
                layoutSeat.addView(layout);

            } else if (seats.charAt(index) == 'U') {        // Already booked seats

                count++;

                TextView view = new TextView(this);

                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(seatSize, seatSize);
                layoutParams.setMargins(seatGaping, seatGaping, seatGaping, seatGaping);
                view.setLayoutParams(layoutParams);

                view.setPadding(0, 0, 0, 2 * seatGaping);
                view.setId(count);
                view.setGravity(Gravity.CENTER);

                view.setBackgroundResource(R.drawable.ic_seats_booked);
                view.setTag(STATUS_BOOKED);

                view.setText(count + "");
                view.setTextColor(Color.WHITE);
                view.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 9);

                layout.addView(view);
                seatViewList.add(view);

                view.setOnClickListener(this);

            } else if (seats.charAt(index) == 'A') {        // Available seats

                count++;

                TextView view = new TextView(this);

                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(seatSize, seatSize);
                layoutParams.setMargins(seatGaping, seatGaping, seatGaping, seatGaping);
                view.setLayoutParams(layoutParams);

                view.setPadding(0, 0, 0, 2 * seatGaping);
                view.setId(count);
                view.setGravity(Gravity.CENTER);

                view.setBackgroundResource(R.drawable.ic_seats_book);
                view.setTag(STATUS_AVAILABLE);

                view.setText(count + "");
                view.setTextColor(Color.BLACK);
                view.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 9);

                layout.addView(view);
                seatViewList.add(view);

                view.setOnClickListener(this);

            } else if (seats.charAt(index) == '_') {        // Empty space

                TextView view = new TextView(this);

                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(seatSize, seatSize);
                view.setLayoutParams(layoutParams);

                view.setBackgroundColor(Color.TRANSPARENT);

                layout.addView(view);

            }

        }

        // Action proceed to Checkout
        button_Checkout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (textView_MovieDateSelected.getText().equals("xxxx-xx-xx")) {

                    Toast.makeText(getApplicationContext(), getString(R.string.selectDate), Toast.LENGTH_SHORT).show();

                }

                else if (spinner_MovieTimes.getSelectedItemPosition() == 0) {

                    Toast.makeText(getApplicationContext(), getString(R.string.selectTime), Toast.LENGTH_SHORT).show();

                }

                else if (seatsSelected.size() < 1) {

                    Toast.makeText(getApplicationContext(), getString(R.string.selectSeats), Toast.LENGTH_SHORT).show();

                }

                else {

                    Intent intentCheckout = new Intent(Booking_Activity.this, Checkout_Activity.class);

                    // Pass movie object, selected time an amount of seats to the new activity
                    intentCheckout.putExtra("MOVIE", selectedMovieTitle);
                    intentCheckout.putExtra("DATE", dateSelected);
                    intentCheckout.putExtra("TIME", timeSelected);
                    intentCheckout.putExtra("SEATS", (Serializable) seatsSelected);

                    startActivity(intentCheckout);

                }

            }

        });

    }


    // Action when the seats are clicked
    @Override
    public void onClick(View view) {

        if ((int) view.getTag() == STATUS_AVAILABLE) {

            if (selectedIds.contains(view.getId() + ",")) {     // Available seat selected is deselect

                selectedIds = selectedIds.replace(+view.getId() + ",", "");
                view.setBackgroundResource(R.drawable.ic_seats_book);

                seatsSelected.remove(String.valueOf(view.getId()));

            }

            else {      // Available seat deselected is select

                selectedIds = selectedIds + view.getId() + ",";
                view.setBackgroundResource(R.drawable.ic_seats_selected);

                seatsSelected.add(String.valueOf(view.getId()));

            }

        }

        else if ((int) view.getTag() == STATUS_BOOKED) {

            Toast.makeText(this,  getString(R.string.seatBooked1) + " " + view.getId() + " " + getString(R.string.seatBooked2), Toast.LENGTH_SHORT).show();

        }

    }

}