package com.example.snake.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.snake.R;


public class MainMenuActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
    }

    public void onButtonClick(View view) {
        int id = view.getId();
        if (id == R.id.iv_about_app) {
            startActivity(new Intent(this, AboutAppActivity.class));
        } else if (id == R.id.iv_settigns) {
            startActivity(new Intent(this, SettingsActivity.class));
        } else if (id == R.id.btn_play) {
            startActivity(new Intent(this, GameActivity.class));
        } else if (id == R.id.btn_high_scores) {
        }
    }
}
