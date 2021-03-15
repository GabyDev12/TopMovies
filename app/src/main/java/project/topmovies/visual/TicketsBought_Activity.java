package project.topmovies.visual;


import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;
import pl.droidsonroids.gif.GifImageView;
import project.topmovies.*;
import project.topmovies.logic.MovieSeen;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.zxing.WriterException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;


public class TicketsBought_Activity extends AppCompatActivity {

    // VARIABLES //

    private static final int PERMISSION_REQUEST_CODE = 200;


    private String selectedMovieTitle;

    private String dateSelected;

    private String timeSelected;

    private int amountOfTickets;

    private double finalPrice;


    ProgressBar progressBar_TicketsBought;

    GifImageView gifImageView_Enjoy;

    Button button_DownloadTicket;

    Button button_Finish;


    Bitmap Bitmap_QRCode, ScaledBitmap_QRCode;


    private FirebaseAuth mAuth;

    private DatabaseReference mDatabase;


    // ACTIVITY ACTIONS //

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.ticketsbought_layout);


        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();


        // Access to Firebase Database users reference
        mDatabase = FirebaseDatabase.getInstance().getReference("users");


        // Load the movie object, selected time an amount of seats
        selectedMovieTitle = (String) getIntent().getSerializableExtra("MOVIE");
        dateSelected = (String) getIntent().getSerializableExtra("DATE");
        timeSelected = (String) getIntent().getSerializableExtra("TIME");
        amountOfTickets = (Integer) getIntent().getSerializableExtra("TICKETS");
        finalPrice = (Double) getIntent().getSerializableExtra("PRICE");


        // Access to views
        progressBar_TicketsBought = findViewById(R.id.progressBar_TicketsBought);

        gifImageView_Enjoy = findViewById(R.id.gifImageView_Enjoy);

        button_DownloadTicket = findViewById(R.id.button_DownloadTicket);

        button_Finish = findViewById(R.id.button_Finish);


        // Save movie seen in the user profile
        MovieSeen currentMovieSeen = new MovieSeen(dateSelected, timeSelected, String.valueOf(amountOfTickets), String.valueOf(finalPrice));


        // Hide progress bar and show the other items
        progressBar_TicketsBought.setVisibility(View.GONE);

        gifImageView_Enjoy.setVisibility(View.VISIBLE);

        button_DownloadTicket.setVisibility(View.VISIBLE);

        button_Finish.setVisibility(View.VISIBLE);

        /** mDatabase.child(mAuth.getCurrentUser().getUid())
                .child("watchedMovies")
                .child(selectedMovieTitle)
                .setValue(currentMovieSeen).addOnCompleteListener(new OnCompleteListener<Void>() {

            @Override
            public void onComplete(@NonNull Task<Void> task) {      // Check if the user information was stored in the DB

                if (task.isSuccessful()) {

                    // Hide progress bar and show the other items
                    progressBar_TicketsBought.setVisibility(View.GONE);

                    gifImageView_Enjoy.setVisibility(View.VISIBLE);

                    button_DownloadTicket.setVisibility(View.VISIBLE);

                    button_Finish.setVisibility(View.VISIBLE);


                    // Return to HomeScreen_Activity

                    Toast.makeText(TicketsBought_Activity.this, "Watched movie data saved successfully in your profile!", Toast.LENGTH_LONG).show();

                }

                else {

                    Toast.makeText(TicketsBought_Activity.this, "There was a problem saving the watched movie data. Sorry", Toast.LENGTH_LONG).show();

                }

            }

        });*/


        // Generate QR Code

        QRGEncoder qrgEncoder = new QRGEncoder("QR Code", null, QRGContents.Type.TEXT, 200);

        try {

            Bitmap_QRCode = qrgEncoder.encodeAsBitmap();

        } catch (WriterException e) {

            Toast.makeText(TicketsBought_Activity.this, "There was a problem generating the QR code", Toast.LENGTH_SHORT).show();

        }

        // Resize the QR code
        ScaledBitmap_QRCode = Bitmap.createScaledBitmap(Bitmap_QRCode, 100, 100, false);


        // Action Download Ticket
        button_DownloadTicket.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // Request permission if it is not granted yet

                if (!checkPermission()) {

                    requestPermission();

                }


                // GENERATE PDF //


                // Create a new document
                PdfDocument document = new PdfDocument();

                // Create a page description
                PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(210, 100, 1).create();

                // Start a page
                PdfDocument.Page page = document.startPage(pageInfo);


                // Set content on the page
                Canvas canvas = page.getCanvas();

                // Variables for QR image and text
                Paint paint = new Paint();
                Paint text = new Paint();

                // Configuration of the text
                text.setTextSize(5);
                text.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));

                // Load the data
                canvas.drawBitmap(ScaledBitmap_QRCode, 5, 0, paint);

                canvas.drawText("Movie:  " + selectedMovieTitle, 110, 33, text);
                canvas.drawText("Date:  " + dateSelected, 110, 43, text);
                canvas.drawText("Time:  " + timeSelected, 110, 53, text);
                canvas.drawText("Tickets:  " + amountOfTickets, 110, 63, text);
                canvas.drawText("Price:  " + finalPrice + " €", 110, 73, text);


                // finish the page
                document.finishPage(page);


                // Create a File for storage the PDF in the phone
                String fileName = "Ticket_" + selectedMovieTitle.replace(" ", "-") + "_" + dateSelected + ".pdf";

                File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "/" + fileName);


                // Save the document in the phone
                FileOutputStream outputStream = null;

                try {

                    file.createNewFile();

                    outputStream = new FileOutputStream(file, false);

                    document.writeTo(outputStream);

                    outputStream.flush();
                    outputStream.close();

                    Toast.makeText(TicketsBought_Activity.this, "The ticket was successfully saved!", Toast.LENGTH_SHORT).show();

                } catch (IOException e) {

                    e.printStackTrace();

                }

                // Close the document
                document.close();


                // Open the PDF in a new screen
                Intent intentPDFViewer = new Intent(TicketsBought_Activity.this, PDFViewer_Activity.class);

                // Pass file object
                intentPDFViewer.putExtra("PDF", file);

                startActivity(intentPDFViewer);

            }

        });


        // Action Finish
        button_Finish.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent backHome = new Intent(TicketsBought_Activity.this, HomeScreen_Activity.class);

                backHome.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                startActivity(backHome);

            }

        });

    }

    @Override
    public void onBackPressed() {

        // Do nothing when it is pressed
        // The user must click the Finish button

    }


    // PERMISSIONS //

    private boolean checkPermission() {

        // Checking of permissions
        int permission1 = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
        int permission2 = ContextCompat.checkSelfPermission(getApplicationContext(), READ_EXTERNAL_STORAGE);

        return permission1 == PackageManager.PERMISSION_GRANTED && permission2 == PackageManager.PERMISSION_GRANTED;

    }

    private void requestPermission() {

        // Requesting permissions if not provided
        ActivityCompat.requestPermissions(this, new String[]{WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == PERMISSION_REQUEST_CODE) {

            if (grantResults.length > 0) {

                // After requesting permissions we are showing
                // users a toast message of permission granted
                boolean writeStorage = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                boolean readStorage = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                if (writeStorage && readStorage) {

                    Toast.makeText(TicketsBought_Activity.this, "Permission Granted", Toast.LENGTH_SHORT).show();

                }

                else {

                    Toast.makeText(TicketsBought_Activity.this, "Permission Denied", Toast.LENGTH_SHORT).show();

                }

            }

        }

    }

}