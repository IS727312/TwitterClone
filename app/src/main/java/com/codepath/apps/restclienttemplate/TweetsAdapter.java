package com.codepath.apps.restclienttemplate;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.models.Tweet;

import java.util.List;

public class TweetsAdapter extends RecyclerView.Adapter<TweetsAdapter.viewHolder>{
    Context context;
    List<Tweet> tweets;
    //Pass in the context and list of tweets

    public TweetsAdapter(Context context, List<Tweet> tweets) {
        this.context = context;
        this.tweets = tweets;
    }

    //For each row, inflate the layout
    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_tweet, parent, false);
        return new viewHolder(view);
    }

    //Bind values based on the position
    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        //Get data at the position
         Tweet tweet = tweets.get(position);
        //Bind the tweet with the viewHolder
        holder.bind(tweet);
    }

    @Override
    public int getItemCount() {
        return tweets.size();
    }

    //Define a new viewHolder
    public class viewHolder extends RecyclerView.ViewHolder {

        public static final String TAG = "TweetsAdapter";

        ImageView ivProfilePicture;
        ImageView ivTweetImage;
        TextView tvScreenName;
        TextView tvBody;
        TextView tvUsername;
        TextView tvRelativeTimeStamp;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            ivProfilePicture = itemView.findViewById(R.id.ivProfilePicture);
            ivTweetImage = itemView.findViewById(R.id.ivTweetImage);
            tvScreenName = itemView.findViewById(R.id.tvScreenName);
            tvBody = itemView.findViewById(R.id.tvBody);
            tvUsername = itemView.findViewById(R.id.tvUsername);
            tvRelativeTimeStamp = itemView.findViewById(R.id.tvRelativeTimeStamp);
        }

        public void bind(Tweet tweet) {
            String username = "@" + tweet.user.screenName;
            tvBody.setText(tweet.body);
            tvScreenName.setText(username);
            tvUsername.setText(tweet.user.name);
            Glide.with(context).load(tweet.user.profileImageURL).into(ivProfilePicture);
            Glide.with(context).load(tweet.tweetImage).into(ivTweetImage);
            if(tweet.tweetImage != ""){
                ivTweetImage.setVisibility(View.VISIBLE);
            }
            tvRelativeTimeStamp.setText(tweet.relativeTimeStamp);
            Log.e(TAG, tweet.relativeTimeStamp);
        }
    }

    public void clear() {
        tweets.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(List<Tweet> list) {
        tweets.addAll(list);
        notifyDataSetChanged();
    }
}
