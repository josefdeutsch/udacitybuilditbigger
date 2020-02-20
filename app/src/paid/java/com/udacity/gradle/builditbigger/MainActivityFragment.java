package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myandroidlib.JokeDisplayActivity;

public class MainActivityFragment extends Fragment {

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_main, container, false);
        tellJoke(root);
        return root;
    }

    private void tellJoke(final View root) {
        root.findViewById(R.id.joker).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new EndpointsAsyncTask(new IEndpointAsyncTask() {
                    @Override
                    public void onRetrieveJokeStart() {

                    }

                    @Override
                    public void onRetrieveJokeFinish(@Nullable String result) {
                       startActivity(result);
                    }
                }).execute();
            }
        });
    }

    private void startActivity(@Nullable String result) {
        Intent myIntent = new Intent(getContext(), JokeDisplayActivity.class);
        myIntent.putExtra(JokeDisplayActivity.INTENT_JOKE, result);
        startActivity(myIntent);
    }
}
