package com.techiasolutions.popularmovies;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class MoviesAdaptor extends RecyclerView.Adapter<MoviesAdaptor.ViewHolder> {

    private final List<MoviesResult> mData;
    private ItemClickListener mClickListener;

    // data is passed into the constructor
    MoviesAdaptor(List<MoviesResult> data) {
        this.mData = data;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_row, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        List<MoviesResult> movie = (List<MoviesResult>) mData;
        String photo = holder.itemView.getContext().getString(R.string.photo_url) + movie.get(position).getPosterPath();
        Picasso.get().load(photo)
                .resize(1000, 1000)
                .error(R.drawable.ic_launcher_background)
                .into(holder.myImageView);
        holder.setItem(movie.get(position));
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final ImageView myImageView;
        private MoviesResult mItem;

        ViewHolder(View itemView) {
            super(itemView);
            myImageView = itemView.findViewById(R.id.ivMoviePoster);
            itemView.setOnClickListener(this);
        }

        public void setItem(MoviesResult item) {
            mItem = item;
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(view.getContext(), MovieDetailActivity.class);
            intent.putExtra("moveDetailData", mItem);
            view.getContext().startActivity(intent);
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    List<MoviesResult> getItem(int id) {
        return (List<MoviesResult>) mData.get(id);
    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}