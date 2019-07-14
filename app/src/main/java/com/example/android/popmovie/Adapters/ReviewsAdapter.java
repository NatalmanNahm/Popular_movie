package com.example.android.popmovie.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.popmovie.R;
import com.example.android.popmovie.Reviews;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ReviewMovieHolder> {

    private ArrayList<Reviews> mReviews;
    private Context mContext;


    /**
     * Constructor
     * @param context
     * @param reviews
     */
    public ReviewsAdapter(Context context, ArrayList<Reviews> reviews){
        mContext = context;
        mReviews = reviews;
    }

    @NonNull
    @Override
    public ReviewsAdapter.ReviewMovieHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        int layoutForListItem = R.layout.reviews_list;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shourldAttachToParentimmediately = false;

        View view = inflater.inflate(layoutForListItem, viewGroup, shourldAttachToParentimmediately);

        return new ReviewMovieHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewsAdapter.ReviewMovieHolder reviewMovieHolder, int i) {
        reviewMovieHolder.bindReview(mReviews.get(i));
    }

    @Override
    public int getItemCount() {
        if (null == mReviews) return 0;
        return mReviews.size();
    }

    public void setReviewsData(ArrayList<Reviews> reviews){
        mReviews = reviews;
        notifyDataSetChanged();
    }

    public class ReviewMovieHolder extends RecyclerView.ViewHolder{

        @Bind(R.id.author) TextView mAuthor;
        @Bind(R.id.content_review) TextView mContent;
        private Context mContext;

        public ReviewMovieHolder(@NonNull View view) {
            super(view);
            mContext = view.getContext();
            ButterKnife.bind(this, view);
        }
        //use to bind the review data to its appropriate view
        public void bindReview(Reviews reviews){
            mAuthor.setText(reviews.getAuthor());
            mContent.setText(reviews.getContent());
        }

    }
}
