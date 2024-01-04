package com.example.snake_android.snakeActivity;

import static com.example.snake_android.snakeActivity.MenuActivity.levelMP;
import static com.example.snake_android.snakeActivity.MenuActivity.snakeColorMP;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import com.example.snake_android.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


public class GameOverActivity extends AppCompatActivity {

    Button repeat;
    Button menu;
    TextView points;
    EditText nameText;
    static int timerDelay = 350;

    public String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/SnakeFiles";

    File dir;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_game_over);

        Bundle bundle = getIntent().getExtras();

        final int pkt = bundle.getInt("punkty");

        repeat = (Button) findViewById(R.id.GameOverRepeatButton);
        menu = (Button) findViewById(R.id.GameOverMenuButton);
        points = (TextView) findViewById(R.id.GameOverPoints);
        nameText = (EditText) findViewById(R.id.GameOverEnterName);

        dir = new File(path);

        dir.mkdirs();

        final File file = new File(path + "/HighScores.txt");

        points.setText(pkt + " PKT");

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameText.getText().toString();
                if (name.trim().isEmpty()) {
                    name = "Anonim";
                }
                try {
                    writeToFile(file, name, pkt);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                finish();
            }
        });


        repeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameText.getText().toString();
                if (name.trim().isEmpty()) {
                    name = "Anonim";
                }
                try {
                    writeToFile(file, name, pkt);
                } catch (IOException e) {
                    e.printStackTrace();
                }


                switch (levelMP) {
                    case 1:
                        timerDelay = 350;
                        break;
                    case 2:
                        timerDelay = 225;
                        break;
                    case 3:
                        timerDelay = 100;
                        break;
                }

                finish();
                Intent intent = new Intent(getApplicationContext(), SnakeActivity.class);
                intent.putExtra("TIMERDELAY", timerDelay);
                intent.putExtra("SNAKECOLOR", snakeColorMP);
                intent.putExtra("LEVEL", levelMP);
                startActivity(intent);
            }
        });
    }

    public void writeToFile(File file, String name, int points) throws IOException {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file, true);
            fos.write((name + " " + points + "\n").getBytes());
        } finally {
            if (fos != null) {
                fos.close();
            }
        }
    }


}
