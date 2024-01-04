package com.example.snake_android.snakeActivity;

import static com.example.snake_android.snakeActivity.MenuActivity.levelMP;
import static com.example.snake_android.snakeActivity.MenuActivity.snakeColorMP;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.example.snake_android.R;

public class SettingsActivity extends AppCompatActivity {

    Button button1Level;
    Button button2Level;
    Button button3Level;
    Button blackButton;
    Button greenButton;
    Button redButton;
    Button yellowButton;
    Button blueButton;
    Button saveButton;
    Button menuButton;

    private int level = 1;
    private int snakeColor = Color.BLACK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_settings);

        button1Level = (Button) findViewById(R.id.SettingsLevel1Button);
        button2Level = (Button) findViewById(R.id.SettingsLevel2Button);
        button3Level = (Button) findViewById(R.id.SettingsLevel3Button);

        blackButton = (Button) findViewById(R.id.SettingsBlackButton);
        greenButton = (Button) findViewById(R.id.SettingsGreenButton);
        redButton = (Button) findViewById(R.id.SettingsRedButton);
        yellowButton = (Button) findViewById(R.id.SettingsYellowButton);
        blueButton = (Button) findViewById(R.id.SettingsBlueButton);

        saveButton = (Button) findViewById(R.id.SettingsSaveButton);
        menuButton = (Button) findViewById(R.id.SettingsMenuButton);


        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                levelMP = level;
                snakeColorMP = snakeColor;
            }
        });

        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        button1Level.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                level = 1;
                button1Level.setTextColor(Color.BLACK);
                button2Level.setTextColor(Color.WHITE);
                button3Level.setTextColor(Color.WHITE);
            }
        });

        button2Level.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                level = 2;
                button1Level.setTextColor(Color.WHITE);
                button2Level.setTextColor(Color.BLACK);
                button3Level.setTextColor(Color.WHITE);
            }
        });

        button3Level.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                level = 3;
                button1Level.setTextColor(Color.WHITE);
                button2Level.setTextColor(Color.WHITE);
                button3Level.setTextColor(Color.BLACK);
            }
        });

        blackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snakeColor = Color.BLACK;
            }
        });

        greenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snakeColor = Color.GREEN;
            }
        });

        redButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snakeColor = Color.RED;
            }
        });

        yellowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snakeColor = Color.YELLOW;
            }
        });

        blueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snakeColor = Color.BLUE;
            }
        });


    }
}
