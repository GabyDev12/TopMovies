package project.topmovies.logic.adapters;


import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.zxing.WriterException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;
import project.topmovies.R;
import project.topmovies.logic.MovieSeen;
import project.topmovies.visual.PDFViewer_Activity;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;


public class RecyclerView_MovieSeen_Adapter extends RecyclerView.Adapter<RecyclerView_MovieSeen_Adapter.ViewHolder> {

    private List<MovieSeen> mData;

    private LayoutInflater mInflater;

    private Context context;

    private Fragment fragment;


    private Bitmap Bitmap_QRCode;
    private Bitmap ScaledBitmap_QRCode;


    public RecyclerView_MovieSeen_Adapter(List<MovieSeen> itemList, Context context, Fragment fragment) {

        this.mInflater = LayoutInflater.from(context);
        this.fragment = fragment;
        this.context = context;
        this.mData = itemList;

    }

    @Override
    public int getItemCount() {

        return mData.size();

    }

    @Override
    public RecyclerView_MovieSeen_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = mInflater.inflate(R.layout.movieseen_cardview, null);

        return new RecyclerView_MovieSeen_Adapter.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(final RecyclerView_MovieSeen_Adapter.ViewHolder holder, final int position) {

        // Get the current movie
        MovieSeen movieSeenSelected = mData.get(position);


        // Load the movie seen data
        holder.textView_msTitle.setText(movieSeenSelected.getMovieTitle());

        holder.textView_msDate.setText(movieSeenSelected.getDateWatched());
        holder.textView_msTime.setText(movieSeenSelected.getTimeWatched());

        holder.textView_msPrice.setText(movieSeenSelected.getFinalPrice());
        holder.textView_msTickets.setText(movieSeenSelected.getTicketsNumber());


        // Generate QR Code

        QRGEncoder qrgEncoder = new QRGEncoder("QR Code", null, QRGContents.Type.TEXT, 200);

        try {

            Bitmap_QRCode = qrgEncoder.encodeAsBitmap();

        } catch (WriterException e) {

            Toast.makeText(fragment.getActivity(), "There was a problem generating the QR code", Toast.LENGTH_SHORT).show();

        }

        // Resize the QR code
        ScaledBitmap_QRCode = Bitmap.createScaledBitmap(Bitmap_QRCode, 100, 100, false);


        // Action when the Download button is clicked
        holder.imageButton_msDownload.setOnClickListener(new View.OnClickListener() {

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

                canvas.drawText("Movie:  " + movieSeenSelected.getMovieTitle(), 110, 33, text);
                canvas.drawText("Date:  " + movieSeenSelected.getDateWatched(), 110, 43, text);
                canvas.drawText("Time:  " + movieSeenSelected.getTimeWatched(), 110, 53, text);
                canvas.drawText("Tickets:  " + movieSeenSelected.getTicketsNumber(), 110, 63, text);
                canvas.drawText("Price:  " + movieSeenSelected.getFinalPrice() + " €", 110, 73, text);


                // Finish the page
                document.finishPage(page);


                // Create a File for storage the PDF in the phone
                String fileName = "Ticket_" + movieSeenSelected.getMovieTitle().replace(" ", "-") + "_" + movieSeenSelected.getDateWatched() + ".pdf";

                File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "/" + fileName);


                // Save the document in the phone
                FileOutputStream outputStream = null;

                try {

                    file.createNewFile();

                    outputStream = new FileOutputStream(file, false);

                    document.writeTo(outputStream);

                    outputStream.flush();
                    outputStream.close();

                    Toast.makeText(fragment.getActivity(), "The ticket was successfully saved!", Toast.LENGTH_SHORT).show();

                } catch (IOException e) {

                    e.printStackTrace();

                }


                // Close the document
                document.close();

            }

        });


        // Action when the Watch button is clicked
        holder.imageButton_msWatch.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

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

                canvas.drawText("Movie:  " + movieSeenSelected.getMovieTitle(), 110, 33, text);
                canvas.drawText("Date:  " + movieSeenSelected.getDateWatched(), 110, 43, text);
                canvas.drawText("Time:  " + movieSeenSelected.getTimeWatched(), 110, 53, text);
                canvas.drawText("Tickets:  " + movieSeenSelected.getTicketsNumber(), 110, 63, text);
                canvas.drawText("Price:  " + movieSeenSelected.getFinalPrice() + " €", 110, 73, text);


                // Finish the page
                document.finishPage(page);


                // Create a File object for the PDF
                String fileName = "Ticket_" + movieSeenSelected.getMovieTitle().replace(" ", "-") + "_" + movieSeenSelected.getDateWatched() + ".pdf";

                File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "/" + fileName);


                // Open the PDF in a new screen
                Intent intentPDFViewer = new Intent(fragment.getActivity(), PDFViewer_Activity.class);

                // Pass file object
                intentPDFViewer.putExtra("PDF", file);

                fragment.startActivity(intentPDFViewer);

            }

        });

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textView_msTitle;

        TextView textView_msDate;
        TextView textView_msTime;

        TextView textView_msPrice;
        TextView textView_msTickets;

        ImageButton imageButton_msDownload;
        ImageButton imageButton_msWatch;


        ViewHolder(View itemView) {

            super(itemView);

            textView_msTitle = itemView.findViewById(R.id.textView_msTitle);

            textView_msDate = itemView.findViewById(R.id.textView_msDate);
            textView_msTime = itemView.findViewById(R.id.textView_msTime);

            textView_msPrice = itemView.findViewById(R.id.textView_msPrice);
            textView_msTickets = itemView.findViewById(R.id.textView_msTickets);

            imageButton_msDownload = itemView.findViewById(R.id.imageButton_msDownload);
            imageButton_msWatch = itemView.findViewById(R.id.imageButton_msWatch);

        }

    }



    // PERMISSIONS FOR GENERATE TICKET //

    private boolean checkPermission() {

        // Checking of permissions
        int permission1 = ContextCompat.checkSelfPermission(context, WRITE_EXTERNAL_STORAGE);
        int permission2 = ContextCompat.checkSelfPermission(context, READ_EXTERNAL_STORAGE);

        return permission1 == PackageManager.PERMISSION_GRANTED && permission2 == PackageManager.PERMISSION_GRANTED;

    }

    private void requestPermission() {

        // Requesting permissions if not provided
        ActivityCompat.requestPermissions(fragment.getActivity(), new String[]{WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE}, 200);

    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == 200) {

            if (grantResults.length > 0) {

                // After requesting permissions we are showing
                // users a toast message of permission granted
                boolean writeStorage = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                boolean readStorage = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                if (writeStorage && readStorage) {

                    Toast.makeText(fragment.getActivity(), "Permission Granted", Toast.LENGTH_SHORT).show();

                }

                else {

                    Toast.makeText(fragment.getActivity(), "Permission Denied", Toast.LENGTH_SHORT).show();

                }

            }

        }

    }

}
