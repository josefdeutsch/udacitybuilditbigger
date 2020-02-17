package com.udacity.gradle.builditbigger;

import android.os.Bundle;

import androidx.annotation.NonNull;
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
    public void getJoke() {
        Bundle args = getBundle();
        FragmentScenario<MainActivityFragment> fragmentScenario =
                FragmentScenario.launchInContainer(MainActivityFragment.class,args);
        fragmentScenario.onFragment(new FragmentScenario.FragmentAction<MainActivityFragment>() {
            @Override
            public void perform(@NonNull MainActivityFragment fragment) {
                try {
                    assertNotNull(fragment.getJoke());
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}