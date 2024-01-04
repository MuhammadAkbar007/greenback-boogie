package com.example.androidgamesnake;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

  private SnakePanelView mSnakePanelView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    mSnakePanelView = findViewById(R.id.snake_view);

    findViewById(R.id.left_btn).setOnClickListener(this);
    findViewById(R.id.right_btn).setOnClickListener(this);
    findViewById(R.id.top_btn).setOnClickListener(this);
    findViewById(R.id.bottom_btn).setOnClickListener(this);
    findViewById(R.id.start_btn).setOnClickListener(this);
  }

  @Override
  public void onClick(View v) {
    int id = v.getId();
    if (id == R.id.left_btn) {
      mSnakePanelView.setSnakeDirection(GameType.LEFT);
    } else if (id == R.id.right_btn) {
      mSnakePanelView.setSnakeDirection(GameType.RIGHT);
    } else if (id == R.id.top_btn) {
      mSnakePanelView.setSnakeDirection(GameType.TOP);
    } else if (id == R.id.bottom_btn) {
      mSnakePanelView.setSnakeDirection(GameType.BOTTOM);
    } else if (id == R.id.start_btn) {
      mSnakePanelView.reStartGame();
    }
  }
}
