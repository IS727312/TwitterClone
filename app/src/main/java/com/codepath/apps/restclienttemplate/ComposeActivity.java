package com.codepath.apps.restclienttemplate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.apps.restclienttemplate.models.Tweet;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;

import org.json.JSONException;
import org.parceler.Parcels;

import okhttp3.Headers;

public class ComposeActivity extends AppCompatActivity {
    public static final int MAX_TWEET_LENGTH = 140;
    public static final String TAG = "ComposeActivity";
    EditText etCompose;
    TextView tvCounter;
    Button bTweet;
    TwitterClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);

        client = TwitterApp.getRestClient(this);
        etCompose = findViewById(R.id.etCompose);
        bTweet = findViewById(R.id.bTweet);
        tvCounter = findViewById(R.id.tvCounter);

        etCompose.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String counter = s.length() + "/140";
                tvCounter.setText(counter);
                Log.d(TAG,tvCounter.getText().toString());
            }
            @Override
            public void afterTextChanged(Editable s) { }
        });

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
                        Intent intent = new Intent();
                        intent.putExtra("tweet", Parcels.wrap(tweet));
                        //Set result code and bundle data for response
                        setResult(RESULT_OK, intent);
                        //Close the activity
                        finish();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                    Log.e(TAG, "onFailure to publish tweet: " + statusCode, throwable);
                }
            });

            //Make an API call to Twitter to publish the Tweet

        });

    }
}