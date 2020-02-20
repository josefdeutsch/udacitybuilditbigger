package com.udacity.gradle.builditbigger;

import androidx.annotation.Nullable;
import androidx.annotation.UiThread;

public interface IEndpointAsyncTask {

    @UiThread
    void onRetrieveJokeStart();

    @UiThread
    void onRetrieveJokeFinish(@Nullable String result);

}
