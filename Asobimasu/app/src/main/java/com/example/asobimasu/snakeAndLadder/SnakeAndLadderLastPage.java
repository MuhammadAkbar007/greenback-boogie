package com.example.asobimasu.snakeAndLadder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.asobimasu.R;

public class SnakeAndLadderLastPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snake_and_ladder_last_page);
    }

    public void onClickReplay(View view){
        Intent replay = new Intent(SnakeAndLadderLastPage.this,SnakeAndLadderBoard.class);
        startActivity(replay);
    }

}
