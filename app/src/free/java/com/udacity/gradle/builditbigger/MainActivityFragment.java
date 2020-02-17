package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.example.myandroidlib.JokeDisplayActivity;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import java.util.concurrent.ExecutionException;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private InterstitialAd mInterstitialAd;
    private ProgressBar mProgressBar;

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
        String result = getJoke();
        loadIntersitialAds(root,result);
    }

    public String getJoke() throws ExecutionException, InterruptedException {
        return new EndpointsAsyncTask(getActivity()).execute().get();
    }

    private void loadIntersitialAds(View root,final String result){
        mProgressBar = root.findViewById(R.id.progressbar);
        mInterstitialAd = new InterstitialAd(getActivity());
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                if (mProgressBar != null) {
                    mProgressBar.setVisibility(View.GONE);
                }
                mInterstitialAd.show();
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                if (mProgressBar != null) {
                    mProgressBar.setVisibility(View.GONE);
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
