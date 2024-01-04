package com.example.snake_android;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Handler;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;

public class GameView extends View {

    Paint fillPaint;
    Rect r;
    static int dWidth, dHeight;
    Handler handler;
    Runnable runnable;

    Snake snake;
    Food food;

    private float previousX;
    private float previousY;
    private int fps;

    public static SoundPool soundPool;
    public static int up, down, left, right, eat;

    public GameView(Context context) {
        super(context);

        soundPool = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);

        down = soundPool.load(getContext(), R.raw.down, 1);
        up = soundPool.load(getContext(), R.raw.up, 1);
        eat = soundPool.load(getContext(), R.raw.eat, 1);
        left = soundPool.load(getContext(), R.raw.left, 1);
        right = soundPool.load(getContext(), R.raw.right, 1);

        fillPaint = new Paint();
        fillPaint.setStyle(Paint.Style.FILL);
        fillPaint.setColor(Color.BLACK);
        
        Display display = ((Activity) getContext())
                .getWindowManager()
                .getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        dWidth = size.x;
        dHeight = size.y;

        r = new Rect(0, 0, dWidth, dHeight);

        snake = new Snake(context);
        food = new Food(snake);

        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                update();
                invalidate();
            }
        };

        switch (MainActivity.LEVEL) {
            case 1:
                fps = 15;
                break;
            case 2:
                fps = 21;
                break;
            case 3:
                fps = 30;
                break;
        }
    }

    void update() {
        snake.update();
        food.update();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRect(r, fillPaint);

        if (!MainActivity.GAMEOVER) {
            snake.draw(canvas);
            food.draw(canvas);
        }

        handler.postDelayed(runnable, 1000/fps);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getActionMasked();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                previousX = event.getX();
                previousY = event.getY();
                break;
            case MotionEvent.ACTION_UP:
                float finalX = event.getX();
                float finalY = event.getY();

                float deltaX = Math.abs(finalX - previousX);
                float deltaY = Math.abs(finalY - previousY);

                if (deltaX > deltaY) {
                    if (finalX > previousX) {
                        if (snake.getDx() != -1 && snake.isMoving()) {
                            playSound(right, false);
                            snake.setDy(0);
                            snake.setDx(1);
                        }
                    } else if (finalX < previousX) {
                        if (snake.getDx() != 1 && snake.isMoving()) {
                            playSound(left, false);
                            snake.setDy(0);
                            snake.setDx(-1);
                        }
                    }
                }

                if (deltaX < deltaY) {
                    if (finalY > previousY) {
                        if (snake.getDy() != -1 && snake.isMoving()) {
                            playSound(down, false);
                            snake.setDx(0);
                            snake.setDy(1);
                        }
                    } else if (finalY < previousY) {
                        if (snake.getDy() != 1 && snake.isMoving()) {
                            playSound(up, false);
                            snake.setDx(0);
                            snake.setDy(-1);
                        }
                    }
                }

                break;
        }
        return true;
    }

    public static void playSound(int sound, boolean loop) {
        if (MainActivity.PLAYSOUND) {
            if (loop) {
                soundPool.play(sound, 1, 1, 1, 0, 1f);
            } else {
                soundPool.play(sound, 1, 1, 1, 1, 1f);
            }
        }
    }

    public static void clean() {
        soundPool.release();
        soundPool = null;
    }
}
