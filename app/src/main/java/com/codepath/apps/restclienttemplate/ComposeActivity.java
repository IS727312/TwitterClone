package com.codepath.apps.restclienttemplate;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.codepath.apps.restclienttemplate.models.Tweet;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;

import org.json.JSONException;

import okhttp3.Headers;

public class ComposeActivity extends AppCompatActivity {
    public static final int MAX_TWEET_LENGTH = 140;
    public static final String TAG = "ComposeActivity";
    EditText etCompose;
    Button bTweet;
    TwitterClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);

        client = TwitterApp.getRestClient(this);
        etCompose = findViewById(R.id.etCompose);
        bTweet = findViewById(R.id.bTweet);

        //Set click listener on button
        bTweet.setOnClickListener(v -> {
            // Code here executes on main thread after user presses button
            String tweetContent = etCompose.getText().toString();
            if(tweetContent.isEmpty()){
                Toast.makeText(ComposeActivity.this, "Tweet is empty!", Toast.LENGTH_SHORT).show();
                return;
            }
            if(tweetContent.length() > MAX_TWEET_LENGTH){
                Toast.makeText(ComposeActivity.this, " Tweet is too long", Toast.LENGTH_SHORT).show();
                return;
            }

            client.publishTweet(tweetContent, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Headers headers, JSON json) {
                    Log.i(TAG, "onSuccess to publish tweet");
                    try {
                        Tweet tweet = Tweet.fromJson(json.jsonObject);
                        Log.i(TAG, "Published tweet: " + tweet);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                    Log.e(TAG, "onFailure to publish tweet", throwable);
                }
            });

            //Make an API call to Twitter to publish the Tweet

        });

    }
}