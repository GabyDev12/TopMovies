package project.topmovies.logic.adapters;


import project.topmovies.R;
import project.topmovies.logic.Movie;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.squareup.picasso.Picasso;

import java.util.List;


public class RecyclerView_Adapter extends RecyclerView.Adapter<RecyclerView_Adapter.ViewHolder> {

    private List<Movie> mData;

    private LayoutInflater mInflater;

    private Context context;


    public RecyclerView_Adapter(List<Movie> itemList, Context context) {

        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.mData = itemList;

    }

    @Override
    public int getItemCount() {

        return mData.size();

    }

    @Override
    public RecyclerView_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = mInflater.inflate(R.layout.movie_cardview, null);

        return new RecyclerView_Adapter.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(final RecyclerView_Adapter.ViewHolder holder, final int position) {

        // Get the current movie
        Movie currentMovie = mData.get(position);


        // Load the poster of the movie
        Picasso.with(context)
                .load(currentMovie.getPoster())
                .fit()
                .centerCrop()
                .into(holder.imageView_Movie);


        // Action when a movie is clicked
        holder.imageView_Movie.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {



            }

        });

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView_Movie;

        ViewHolder(View itemView) {

            super(itemView);

            imageView_Movie = itemView.findViewById(R.id.imageView_Movie);

        }

    }

}
