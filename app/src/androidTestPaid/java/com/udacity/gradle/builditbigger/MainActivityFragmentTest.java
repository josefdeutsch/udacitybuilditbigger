package com.udacity.gradle.builditbigger;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.testing.FragmentScenario;
import org.junit.Test;
import java.util.concurrent.ExecutionException;
import static org.junit.Assert.*;

public class MainActivityFragmentTest {

    private Bundle getBundle() {
        Bundle args = new Bundle();
        return args;
    }

    @Test
    public void check_EndpointsAsyncTask_isNotNull_hasNoEmptyValue() {
        Bundle args = getBundle();
        FragmentScenario<MainActivityFragment> fragmentScenario =
                FragmentScenario.launchInContainer(MainActivityFragment.class,args);
        fragmentScenario.onFragment(new FragmentScenario.FragmentAction<MainActivityFragment>() {
            @Override
            public void perform(@NonNull final MainActivityFragment fragment) {
                try {
                    String str = new EndpointsAsyncTask(new IEndpointAsyncTask() {
                        @Override
                        public void onRetrieveJokeStart() {

                        }

                        @Override
                        public void onRetrieveJokeFinish(@Nullable String result) {

                        }
                    }).execute().get();

                    assertNotNull(str);
                    assertTrue(!str.isEmpty());

                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}