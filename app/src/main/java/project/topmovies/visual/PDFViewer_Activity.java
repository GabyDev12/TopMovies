package project.topmovies.visual;


import project.topmovies.*;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.github.barteksc.pdfviewer.PDFView;

import java.io.File;


public class PDFViewer_Activity extends AppCompatActivity {

    // VARIABLES //

    PDFView pdfView;

    private File PDFFile;


    // ACTIVITY ACTIONS //

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.pdfviewer_layout);


        // Load the file object
        PDFFile = (File) getIntent().getSerializableExtra("PDF");


        // Access to views
        pdfView = findViewById(R.id.pdfView);


        // Load the PDF
        pdfView.fromFile(PDFFile).load();

    }

}