package com.example.snake_android;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    boolean exit;

    Button sound;
    Button level;
    Button howtoplay;
    Button about;

    public static boolean PLAYSOUND = true;
    public static int LEVEL = 1;
    public static boolean GAMEOVER = false;

    AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sound = findViewById(R.id.sound);
        level = findViewById(R.id.level);
        howtoplay = findViewById(R.id.howtoplay);
        about = findViewById(R.id.about);

        exit = false;

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        sound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MainActivity.PLAYSOUND) {
                    MainActivity.PLAYSOUND = false;
                    sound.setText("Sound: Off");
                } else {
                    MainActivity.PLAYSOUND = true;
                    sound.setText("Sound: On");
                }
            }
        });

        level.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MainActivity.LEVEL != 3) {
                    MainActivity.LEVEL++;
                } else {
                    MainActivity.LEVEL = 1;
                }

                switch (MainActivity.LEVEL) {
                    case 1:
                        level.setText("Level: Easy");
                        break;
                    case 2:
                        level.setText("Level: Medium");
                        break;
                    case 3:
                        level.setText("Level: Hard");
                        break;
                }
            }
        });

        howtoplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setHowtoplay();
            }
        });

        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAbout();
            }
        });


    }

    @Override
    public void onBackPressed() {
        if (exit) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(false);
            builder.setMessage("Exit?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create()
                    .show();
        } else {
            exit = true;
            Toast.makeText(MainActivity.this, "Press BACK again to exit.",
                    Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exit = false;
                }
            }, 2000);
        }
    }

    public void startGame(View view) {
        GAMEOVER = false;
        Intent intent = new Intent(MainActivity.this, StartGame.class);
        startActivity(intent);

        finish();
    }

    private void setHowtoplay() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater layoutInflater = this.getLayoutInflater();

        alertDialogBuilder.setCancelable(false);

        alertDialogBuilder.setView(layoutInflater.inflate(R.layout.how_to_play, null))
                .setPositiveButton("Back", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create()
                .show();
    }

    private void setAbout() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater layoutInflater = this.getLayoutInflater();

        alertDialogBuilder.setCancelable(false);

        alertDialogBuilder.setView(layoutInflater.inflate(R.layout.about, null))
                .setPositiveButton("Back", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create()
                .show();
    }
}
