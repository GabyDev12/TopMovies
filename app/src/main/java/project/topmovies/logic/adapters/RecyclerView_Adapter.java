package project.topmovies.logic.adapters;


import project.topmovies.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class RecyclerView_Adapter extends RecyclerView.Adapter<RecyclerView_Adapter.ViewHolder> {

    private List<String> mData;

    private LayoutInflater mInflater;

    private Context context;

    public RecyclerView_Adapter(List<String> itemList, Context context) {

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

        holder.bindData(mData.get(position));
        holder.movie_cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
            }
        });

    }

    public void setItems(List<String> items) {

        mData = items;

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textView;
        CardView movie_cardView;

        ViewHolder(View itemView) {

            super(itemView);
            movie_cardView = itemView.findViewById(R.id.movie_card);

        }

        void bindData(final String item) {


        }

    }

}
