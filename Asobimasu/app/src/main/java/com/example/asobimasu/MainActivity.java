package com.example.asobimasu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.asobimasu.chess.ChessMainActivity;
import com.example.asobimasu.connect4.MainActivityConnect4;
import com.example.asobimasu.gravityBalls.GravityBallsMainActivity;
import com.example.asobimasu.snakeAndLadder.SnakeAndLadderMainActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class MainActivity extends AppCompatActivity {

    FloatingActionButton chess, snake_and_ladder, gravity_balls, connect4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        chess = (FloatingActionButton) findViewById(R.id.button_chess);
        snake_and_ladder = (FloatingActionButton) findViewById(R.id.button_snake_and_ladder);
        gravity_balls = (FloatingActionButton) findViewById(R.id.button_gravity_balls);
        connect4 = (FloatingActionButton) findViewById(R.id.button_connect4);

        chess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent chess = new Intent(MainActivity.this, ChessMainActivity.class);
                MainActivity.this.startActivity(chess);
            }
        });

        snake_and_ladder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent chess = new Intent(MainActivity.this, SnakeAndLadderMainActivity.class);
                MainActivity.this.startActivity(chess);
            }
        });

        gravity_balls.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent chess = new Intent(MainActivity.this, GravityBallsMainActivity.class);
                MainActivity.this.startActivity(chess);
            }
        });

        connect4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent chess = new Intent(MainActivity.this, MainActivityConnect4.class);
                MainActivity.this.startActivity(chess);
            }
        });

    }
}
