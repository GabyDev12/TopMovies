package project.topmovies.logic.adapters;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import project.topmovies.R;
import project.topmovies.logic.MovieSeen;
import project.topmovies.visual.MovieDetails_Activity;


public class RecyclerView_MovieSeen_Adapter extends RecyclerView.Adapter<RecyclerView_MovieSeen_Adapter.ViewHolder> {

    private List<MovieSeen> mData;

    private LayoutInflater mInflater;

    private Context context;


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


        // Action when the Download button is clicked
        holder.imageButton_msDownload.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {



            }

        });

        // Action when the Watch button is clicked
        holder.imageButton_msWatch.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {



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

}
