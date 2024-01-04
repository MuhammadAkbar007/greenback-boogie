package com.example.snake_android;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.ArrayList;
import java.util.List;

public class Snake {

    private final String TAG = getClass().getName().toString();
    private final int STARTSIZE = 5;
    private final int STARTX = GameView.dWidth / 2;
    private final int STARTY = GameView.dHeight / 2;
    private final int CELLSIZE = 30;

    private boolean elongate;
    private boolean isMoving;
    private int dx = 1;
    private int dy = 0;

    private Context context;
    private Rect r;
    private Paint fillPaint;

    List<Cell> snakePoints;

    public Snake(Context context) {
        this.context = context;
        init();
    }

    void init() {
        isMoving = true;
        elongate = false;
        snakePoints = new ArrayList<>();
        for (int i = 0; i < STARTSIZE; i++) {
            snakePoints.add(new Cell(STARTX - (i * CELLSIZE), STARTY));
        }
        fillPaint = new Paint();
        fillPaint.setStyle(Paint.Style.FILL);
        fillPaint.setColor(Color.WHITE);
    }

    void drawRectangle(Canvas canvas, int x, int y, int w, int h) {
        int left = x;
        int top = y;
        int right = x + w;
        int bottom = y + h;
        canvas.drawRect(new Rect(left, top, right, bottom), fillPaint);
    }

    void draw(Canvas canvas) {
        fillPaint.setColor(Color.WHITE);
        for (Cell c : snakePoints) {
            int x = c.getX();
            int y = c.getY();
            drawRectangle(canvas, x, y, CELLSIZE, CELLSIZE);
        }
        fillPaint.setColor(Color.MAGENTA);
        for (int i = 0; i < 2; i++) {
            int x = snakePoints.get(i).getX();
            int y = snakePoints.get(i).getY();
            drawRectangle(canvas, x, y, CELLSIZE, CELLSIZE);
        }
    }

    void update() {
        if (isMoving) {
            Cell first = snakePoints.get(0);
            Cell last = snakePoints.get(snakePoints.size() - 1);
            Cell newCell = new Cell(first.getX() + dx * CELLSIZE,
                    first.getY() + dy * CELLSIZE);

            for (int i = snakePoints.size()-1; i > 0; i--) {
                snakePoints.set(i, snakePoints.get(i - 1));
            }

            snakePoints.set(0, newCell);

            if (elongate) {
                snakePoints.add(last);
                setElongate(false);
            }

            moveThroughWalls();
            snakeCollision();
        }
    }

    private void moveThroughWalls() {
        int headX = snakePoints.get(0).getX();
        int headY = snakePoints.get(0).getY();

        if (headX > GameView.dWidth - CELLSIZE && dx == 1) {
            snakePoints.get(0).setX(0);
        }

        if (headX < 0 && dx == -1) {
            snakePoints.get(0).setX(GameView.dWidth);
        }

        if (headY > GameView.dHeight - CELLSIZE && dy == 1) {
            snakePoints.get(0).setY(0);
        }

        if (headY < 0 && dy == -1) {
            snakePoints.get(0).setY(GameView.dHeight);
        }
    }

    private void snakeCollision() {
        int headX = snakePoints.get(0).getX() + CELLSIZE/2;
        int headY = snakePoints.get(0).getY() + CELLSIZE/2;

        for (int i = 2; i < snakePoints.size(); i++) {
            if (headX > snakePoints.get(i).getX() && headX < snakePoints.get(i).getX() + CELLSIZE) {
                if (headY > this.snakePoints.get(i).getY() && headY < this.snakePoints.get(i).getY()
                + CELLSIZE) {
                    setMoving(false);
                    showGameOver();
                }
            }
        }
    }

    void showGameOver() {
       Intent intent = new Intent(context, GameOver.class);
       StartGame.startGameActivity.startActivity(intent);
       StartGame.startGameActivity.finish();
    }

    void reset() {
        snakePoints.clear();
    }

    public boolean isElongate() {
        return elongate;
    }

    public void setElongate(boolean elongate) {
        this.elongate = elongate;
    }

    public boolean isMoving() {
        return isMoving;
    }

    public void setMoving(boolean moving) {
        isMoving = moving;
    }

    public int getDx() {
        return dx;
    }

    public void setDx(int dx) {
        this.dx = dx;
    }

    public int getDy() {
        return dy;
    }

    public void setDy(int dy) {
        this.dy = dy;
    }

    public int getCELLSIZE() {
        return CELLSIZE;
    }
}
