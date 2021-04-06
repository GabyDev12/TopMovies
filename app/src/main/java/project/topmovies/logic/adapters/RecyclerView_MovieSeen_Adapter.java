package project.topmovies.logic.adapters;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

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


public class RecyclerView_MovieSeen_Adapter extends RecyclerView.Adapter<RecyclerView_MovieSeen_Adapter.ViewHolder> {

    private List<MovieSeen> mData;

    private LayoutInflater mInflater;

    private Context context;


    private Bitmap Bitmap_QRCode;
    private Bitmap ScaledBitmap_QRCode;


    public RecyclerView_MovieSeen_Adapter(List<MovieSeen> itemList, Context context) {

        this.mInflater = LayoutInflater.from(context);
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

            Toast.makeText(context, R.string.problemQRCode, Toast.LENGTH_SHORT).show();

        }

        // Resize the QR code
        ScaledBitmap_QRCode = Bitmap.createScaledBitmap(Bitmap_QRCode, 100, 100, false);


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

                canvas.drawText(context.getString(R.string.pdfMovie) + " " + movieSeenSelected.getMovieTitle(), 110, 33, text);
                canvas.drawText(context.getString(R.string.pdfDate) + " " + movieSeenSelected.getDateWatched(), 110, 43, text);
                canvas.drawText(context.getString(R.string.pdfTime) + " " + movieSeenSelected.getTimeWatched(), 110, 53, text);
                canvas.drawText(context.getString(R.string.pdfTickets) + " " + movieSeenSelected.getTicketsNumber(), 110, 63, text);
                canvas.drawText(context.getString(R.string.pdfPrice) + " " + movieSeenSelected.getFinalPrice(), 110, 73, text);


                // Finish the page
                document.finishPage(page);


                // Create a File object for the PDF
                String fileName = "tempTicket-" + System.currentTimeMillis() + "_" + movieSeenSelected.getMovieTitle().replace(" ", "-") + "_" + movieSeenSelected.getDateWatched() + ".pdf";

                File file = new File(context.getCacheDir(), "/" + fileName);


                // Save the document in cache for visualize it after
                FileOutputStream outputStream = null;

                try {

                    file.createNewFile();

                    outputStream = new FileOutputStream(file, false);

                    document.writeTo(outputStream);

                    outputStream.flush();
                    outputStream.close();

                } catch (IOException e) {

                    e.printStackTrace();

                }


                // Close the document
                document.close();


                // Open the PDF in a new screen
                Intent intentPDFViewer = new Intent(context, PDFViewer_Activity.class);

                // Pass file object
                intentPDFViewer.putExtra("PDF", file);

                context.startActivity(intentPDFViewer);

            }

        });

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textView_msTitle;

        TextView textView_msDate;
        TextView textView_msTime;

        TextView textView_msPrice;
        TextView textView_msTickets;

        ImageButton imageButton_msWatch;


        ViewHolder(View itemView) {

            super(itemView);

            textView_msTitle = itemView.findViewById(R.id.textView_msTitle);

            textView_msDate = itemView.findViewById(R.id.textView_msDate);
            textView_msTime = itemView.findViewById(R.id.textView_msTime);

            textView_msPrice = itemView.findViewById(R.id.textView_msPrice);
            textView_msTickets = itemView.findViewById(R.id.textView_msTickets);

            imageButton_msWatch = itemView.findViewById(R.id.imageButton_msWatch);

        }

    }

}
