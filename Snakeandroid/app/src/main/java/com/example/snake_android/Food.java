package com.example.snake_android;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Food {

    private final String TAG = getClass().getName().toString();
    private final int SIZE = 22;

    private Snake snake;
    private int x;
    private int y;
    public static int score = 0;
    private Paint fillPaint;

    public static int getScore() {
        return score;
    }

    public Food(Snake snake) {
        fillPaint = new Paint();
        fillPaint.setStyle(Paint.Style.FILL);
        fillPaint.setColor(Color.GREEN);
        fillPaint.setTextSize(80);

        this.snake = snake;
        newPosition();
    }

    public void draw(Canvas canvas) {
        if (snake.isMoving()) {
            fillPaint.setColor(Color.WHITE);
            canvas.drawText("" + score, (int) GameView.dWidth - 170,
                    (int)100, fillPaint);
            fillPaint.setColor(Color.GREEN);
            canvas.drawCircle((float) x, (float) y, (float) SIZE, fillPaint);
        }
    }

    public void update() {
        if (snakeCollision(snake)) {
            GameView.playSound(GameView.eat, false);
            newPosition();
            score++;
            snake.setElongate(true);
        }
    }

    public void newPosition() {
        x = (int) (Math.random() * (GameView.dWidth - SIZE*9)) + 3*SIZE;
        y = (int) (Math.random() * (GameView.dHeight - SIZE*7)) + 3*SIZE;
    }

    public boolean snakeCollision(Snake s) {
        int headX = s.snakePoints.get(0).getX() + 2;
        int headY = s.snakePoints.get(0).getY() + 2;

        if (snake.getDx() == 1) {
            if (headX + (snake.getCELLSIZE() - 3) >= x - SIZE &&
                    headX <= x + SIZE) {
                if (headY + (snake.getCELLSIZE() - 3) > y && headY < y + SIZE) {
                    return true;
                } else if (headY + (snake.getCELLSIZE()) > y - SIZE &&
                        headY + (snake.getCELLSIZE() - 3) < y + SIZE) {
                    return true;
                }
            }
        }

        if (snake.getDx() == -1) {
            if (headX + 3 >= x && headX <= x + SIZE) {
                if (headY + (snake.getCELLSIZE() - 3) > y && headY < y + SIZE) {
                    return true;
                } else if (headY + (snake.getCELLSIZE()) > y - SIZE &&
                        headY + (snake.getCELLSIZE() - 3) < y + SIZE) {
                    return true;
                }
            }
        }

        if (snake.getDy() == 1) {
            if (headY + (snake.getCELLSIZE() - 3) >= y - SIZE && headY <= y + SIZE) {
                if (headX + (snake.getCELLSIZE() - 3) > x && headX < x + SIZE) {
                    return true;
                } else if (headX + (snake.getCELLSIZE()) > x - SIZE &&
                        headX + (snake.getCELLSIZE() - 3) < x + SIZE) {
                    return true;
                }
            }
        }

        if (snake.getDy() == -1) {
            if (headY + 3 >= y &&
                    headY <= y + SIZE) {
                if (headX + (snake.getCELLSIZE()- 3) > x && headX < x + SIZE) {
                    return true;
                } else if (headX + (snake.getCELLSIZE()) > x - SIZE &&
                        headX + (snake.getCELLSIZE() - 3) < x + SIZE) {
                    return true;
                }
            }
        }

        return false;
    }

    public static void reet() {
        score = 0;
    }
}
