package com.example.android.popmovie;


import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerViewHolder>{

    private ArrayList<Trailers> mTrailers = new ArrayList<>();
    private Context mContext;

    private final TrailerAdapterOnClickHandler mTrailerClickHandler;

    public interface TrailerAdapterOnClickHandler {
        void onClick(String key);
    }

    public TrailerAdapter(Context context, ArrayList<Trailers> trailers, TrailerAdapterOnClickHandler clickHandler){
        mContext = context;
        mTrailers = trailers;
        mTrailerClickHandler = clickHandler;
    }

    @NonNull
    @Override
    public TrailerAdapter.TrailerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.trailers_list;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmedialtely = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmedialtely);
        return new TrailerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrailerAdapter.TrailerViewHolder trailerViewHolder, int position) {
        trailerViewHolder.bindTrailer(mTrailers.get(position), position);
    }

    @Override
    public int getItemCount() {
        if (null == mTrailers) return 0;
        return mTrailers.size();
    }

    public void setmTrailerData(ArrayList<Trailers> trailers){
        mTrailers = trailers;
        notifyDataSetChanged();
    }

    public class TrailerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @Bind(R.id.name_trailer) TextView mTrailerName;
        @Bind(R.id.trailer_label) TextView mTrailerLabel;
        private Context mContext;


        public TrailerViewHolder(@NonNull View view) {
            super(view);
            mContext = view.getContext();
            ButterKnife.bind(this, view);
            view.setOnClickListener(this);

        }

        public void bindTrailer(Trailers trailers, int position){
            mTrailerName.setText(trailers.getmName());
            mTrailerLabel.setText("Trailer " + (position + 1));
        }

        @Override
        public void onClick(View view){
            int adapterPosition = getAdapterPosition();
            Trailers trailers = mTrailers.get(adapterPosition);

            String key = trailers.getmTrailerKey();

            mTrailerClickHandler.onClick(key);
        }
    }

}
