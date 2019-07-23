package com.packtpub.mathgame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        VisibilityManager.hideSystemUI(this);

        Button buttonPlay = findViewById(R.id.buttonPlay);
        Button buttonHighScores = findViewById(R.id.buttonHighScores);
        Button buttonQuit = findViewById(R.id.buttonQuit);

        buttonPlay.setOnClickListener(this);
        buttonHighScores.setOnClickListener(this);
        buttonQuit.setOnClickListener(this);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            VisibilityManager.hideSystemUI(this);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonPlay:
                Intent intent = new Intent(this, GameActivity.class);
                startActivity(intent);
                break;
            case R.id.buttonHighScores:
                Toast.makeText(this, "Not implemented!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.buttonQuit:
                Toast.makeText(this, "Not implemented!", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
