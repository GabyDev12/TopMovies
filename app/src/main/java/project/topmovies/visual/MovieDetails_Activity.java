package project.topmovies.visual;


import project.topmovies.*;
import project.topmovies.logic.Movie;
import project.topmovies.logic.statusApp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.time.LocalDate;


public class MovieDetails_Activity extends AppCompatActivity {

    // VARIABLES //

    private Movie currentMovie;


    ImageView imageView_Poster;

    TextView textView_Runtime;
    TextView textView_ReleaseDate;
    TextView textView_Categories;

    Button button_Actors;
    Button button_Director;

    TextView textView_Synopsis;

    Button button_WatchTrailer;
    Button button_WatchMovie;


    // ACTIVITY ACTIONS //

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_layout);


        // Load movie object
        currentMovie = (Movie) getIntent().getSerializableExtra("MOVIE");


        // Configuration for Toolbar
        ActionBar toolBar = getSupportActionBar();

        toolBar.setTitle(currentMovie.getTitle());
        toolBar.setDisplayHomeAsUpEnabled(true);


        // Access to views
        imageView_Poster = findViewById(R.id.imageView_Poster);

        textView_Runtime = findViewById(R.id.textView_Runtime);
        textView_ReleaseDate = findViewById(R.id.textView_ReleaseDate);
        textView_Categories = findViewById(R.id.textView_Categories);

        textView_Synopsis = findViewById(R.id.textView_Synopsis);

        button_Actors = findViewById(R.id.button_Actors);
        button_Director = findViewById(R.id.button_Director);

        button_WatchTrailer = findViewById(R.id.button_WatchTrailer);

        button_WatchMovie = findViewById(R.id.button_WatchMovie);


        // MOVIE DATA //

        // Load Poster
        Picasso.with(MovieDetails_Activity.this)
                .load(currentMovie.getPoster())
                .fit()
                .centerCrop()
                .into(imageView_Poster);

        // Load Runtime
        textView_Runtime.setText(Html.fromHtml("<b>" + getString(R.string.movieRuntime) + "</b>: " + currentMovie.getRuntime()));

        // Load Release date
        textView_ReleaseDate.setText(Html.fromHtml("<b>"  + getString(R.string.movieReleaseDate) + "</b> <br/> " + currentMovie.getReleaseDate()));

        // Load Categories
        textView_Categories.setText(Html.fromHtml("<b>" + getString(R.string.movieCategories) + "</b> <br/>"));

        for (String category : currentMovie.getCategories()) {

            textView_Categories.append((Html.fromHtml(category + "<br/>")));

        }


        // Load Actors
        button_Actors.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // Retrieving actors data
                String actorsMessage = "\n";

                for (String actor : currentMovie.getActors()) {

                    actorsMessage += actor + "\n";

                }

                // Info dialog builder

                AlertDialog.Builder builder_infoActors = new AlertDialog.Builder(MovieDetails_Activity.this);

                builder_infoActors.setTitle(R.string.movieActors);

                builder_infoActors.setMessage(actorsMessage);

                builder_infoActors.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {

                        // Executed code when "OK" button is pressed

                    }
                });

                builder_infoActors.setIcon(android.R.drawable.ic_dialog_info);


                // Info dialog using the builder

                AlertDialog dialog_infoActors = builder_infoActors.create();

                dialog_infoActors.show();

            }

        });

        // Load Directors
        button_Director.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // Retrieving director data
                String directorsMessage = "\n";

                for (String director : currentMovie.getDirector()) {

                    directorsMessage += director + "\n";

                }

                // Info dialog builder

                AlertDialog.Builder builder_infoDirectors = new AlertDialog.Builder(MovieDetails_Activity.this);

                builder_infoDirectors.setTitle(R.string.movieDirectors);

                builder_infoDirectors.setMessage(directorsMessage);

                builder_infoDirectors.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {

                        // Executed code when "OK" button is pressed

                    }
                });

                builder_infoDirectors.setIcon(android.R.drawable.ic_dialog_info);


                // Info dialog using the builder

                AlertDialog dialog_infoDirectors = builder_infoDirectors.create();

                dialog_infoDirectors.show();

            }

        });


        // Load Synopsis
        textView_Synopsis.setText(currentMovie.getSynopsis());


        // Action Watch movie
        button_WatchTrailer.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intentTrailer = new Intent(MovieDetails_Activity.this, MovieTrailer_Activity.class);

                // Pass movie object to the new activity
                intentTrailer.putExtra("TRAILER", currentMovie.getTrailer());

                startActivity(intentTrailer);

            }

        });


        // Action Watch movie
        button_WatchMovie.setOnClickListener(new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {

                // Check if the movie is available in the actual date
                if (!LocalDate.parse(currentMovie.getReleaseDate()).isAfter(LocalDate.now())) {

                    // Check if the user is logged in
                    if (statusApp.getInstance().loggedIn) {

                        Intent intentDateTimeTicket = new Intent(MovieDetails_Activity.this, Booking_Activity.class);

                        // Pass movie object to the new activity
                        intentDateTimeTicket.putExtra("MOVIE", currentMovie.getTitle());

                        startActivity(intentDateTimeTicket);

                    }

                    else {

                        Toast.makeText(MovieDetails_Activity.this, R.string.mustSignIn, Toast.LENGTH_LONG).show();

                    }

                }

                else {

                    Toast.makeText(MovieDetails_Activity.this, R.string.movieComingSoon, Toast.LENGTH_LONG).show();

                }

            }

        });

    }

}