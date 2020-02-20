package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.myandroidlib.JokeDisplayActivity;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;


public class MainActivityFragment extends Fragment {

    private InterstitialAd mInterstitialAd;
    private AlertDialog mDialog;

    public MainActivityFragment() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_main, container, false);
        tellJoke(root);
        return root;
    }

    private void setupProgressBar(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(false);
        builder.setView(R.layout.progressdialog);
        mDialog = builder.create();
        mDialog.show();
    }


    private void tellJoke(final View root) {
       root.findViewById(R.id.joker).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new EndpointsAsyncTask(new IEndpointAsyncTask() {
                    @Override
                    public void onRetrieveJokeStart() {
                        setupProgressBar();
                    }

                    @Override
                    public void onRetrieveJokeFinish(@Nullable String result) {
                        loadIntersitialAds(result);
                    }
                }).execute();
            }
        });

    }

    private void loadIntersitialAds(@Nullable final String result){
        mInterstitialAd = new InterstitialAd(getActivity());
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                if (mDialog != null) {
                    mDialog.hide();
                }
                mInterstitialAd.show();
            }
            @Override
            public void onAdFailedToLoad(int errorCode) {
                if (mDialog != null) {
                    mDialog.hide();
                }
                startActivity(result);
            }
            @Override
            public void onAdClosed() {
                startActivity(result);
            }
        });
    }
    private void startActivity(String result) {
        Intent myIntent = new Intent(getContext(), JokeDisplayActivity.class);
        myIntent.putExtra(JokeDisplayActivity.INTENT_JOKE, result);
        startActivity(myIntent);
    }

}
