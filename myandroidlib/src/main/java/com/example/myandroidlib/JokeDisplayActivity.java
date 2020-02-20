package com.example.myandroidlib;


import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class JokeDisplayActivity extends AppCompatActivity {

    public final static String INTENT_JOKE = "INTENT_JOKE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke_display);
        TextView textViewJoke = (TextView) findViewById(R.id.textview_joke);
        textViewJoke.setText(getExtra());
    }

    private String getExtra() {
        String extra;
        try {
            extra = getStringExtra();
        } catch (NullPointerException e)  {
            extra = "Server timed out..";
        }
        return extra;
    }

    private String getStringExtra() {
        if(getIntent().getStringExtra(INTENT_JOKE)==null){
           throw new NullPointerException();
        }
        return getIntent().getStringExtra(INTENT_JOKE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
