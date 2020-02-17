package com.udacity.gradle.builditbigger;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.example.myandroidlib.JokeDisplayActivity;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import java.util.concurrent.ExecutionException;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private InterstitialAd mInterstitialAd;
    private ProgressBar mProgressBar;
    private  AlertDialog mDialog;

    public MainActivityFragment() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_main, container, false);


        tellJoke(root);

        return root;
    }

    private void setupProgressBar(View root){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(false); // if you want user to wait for some process to finish,
        builder.setView(R.layout.progressdialog);
        mDialog = builder.create();
        mDialog.show();
      //  mProgressBar = (ProgressBar) root.findViewById(R.id.progressbar);
      //  mProgressBar.setVisibility(View.VISIBLE);
    }


    private void tellJoke(final View root) {
        ((Button) root.findViewById(R.id.joker)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    performAction(root);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void performAction(View root) throws ExecutionException, InterruptedException {
      //  mProgressBar = root.findViewById(R.id.progressbar);
      //  mProgressBar.setVisibility(View.VISIBLE);
        setupProgressBar(root);
        String result = getJoke();
        loadIntersitialAds(root,result);
    }

    public String getJoke() throws ExecutionException, InterruptedException {
        return new EndpointsAsyncTask(getActivity()).execute().get();
    }

    private void loadIntersitialAds(View root,final String result){

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
