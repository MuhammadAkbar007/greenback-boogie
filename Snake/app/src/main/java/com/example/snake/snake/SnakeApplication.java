package com.example.snake.snake;

import android.app.Application;

import com.example.snake.models.Game;


/**
 * Created by andre on 18/01/2017.
 */

public class SnakeApplication extends Application {
    private Game snakeGame;

    public SnakeApplication() {
        this.snakeGame = null;
    }

    public Game getSnakeGame() {
        return snakeGame;
    }

    public void setSnakeGame(Game snakeGame) {
        this.snakeGame = snakeGame;
    }
}
