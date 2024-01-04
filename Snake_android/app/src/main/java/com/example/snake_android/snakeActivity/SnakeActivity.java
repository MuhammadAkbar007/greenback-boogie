package com.example.snake_android.snakeActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.snake_android.snakeLogic.SnakeLogic;

public class SnakeActivity extends AppCompatActivity {

    static Handler handler = new Handler();
    Runnable r;
    int timerDelay = 350;
    static SnakeLogic logic;
    static int licznik = 0;
    View testView;
    static int snakeColor = Color.BLACK;
    static int level = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        testView = new myview(this);


        logic = new SnakeLogic(getApplicationContext());
        logic.initSnake();
        testView.setOnTouchListener(logic.handleTouch);


        Bundle bundle = new Bundle();

        bundle = getIntent().getExtras();

        timerDelay = bundle.getInt("TIMERDELAY");
        snakeColor = bundle.getInt("SNAKECOLOR");
        level = bundle.getInt("LEVEL");

        logic.setLevel(level);


        r = new Runnable() {
            public void run() {
                if (!logic.isGameOver()) {
                    logic.updatePosition();
                    handler.postDelayed(this, timerDelay);
                    setContentView(testView);

                    testView.invalidate();

                } else {

                    Thread.currentThread().interrupt();
                    finish();
                    Intent intent = new Intent(getApplicationContext(), GameOverActivity.class);

                    intent.putExtra("punkty", logic.getPoints());
                    logic.getSnake().length = 0;
                    logic.getSnake().getPosition().clear();

                    logic.setGameOver(false);
                    startActivity(intent);
                }
            }
        };


        handler.postDelayed(r, timerDelay);

    }


}

class myview extends View {
    private GraphicsTools tools = new GraphicsTools();
    private static int WIDTH = 0;
    private static int HEIGHT = 0;

    private int backgroundColor = Color.WHITE;
    private int fruitColor = Color.RED;
    private int linesColor = Color.BLACK;
    Context context;
    Paint paint = new Paint();


    public myview(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        repaintBackground(canvas);


        WIDTH = canvas.getWidth() / 12;
        HEIGHT = canvas.getHeight() / 20;


        tools.drawLines(canvas, linesColor, paint);
        //Log.v("snaketest", "draw lines");
        drawSnake(canvas);

        /*paint.setColor(fruitColor);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRect(3 * WIDTH + 2, 2 * HEIGHT + 2, 3 * WIDTH + WIDTH - 1, 2 * HEIGHT + HEIGHT - 1, paint);*/

        if (SnakeActivity.logic.isAte()) {
            SnakeActivity.logic.generateFruit();
            SnakeActivity.logic.setAte(false);
        }

        drawFruit(canvas);

        /*if (logic.isGameOver()) {

            GameOverActivity gameOverActivity = new GameOverActivity();
            gameOverActivity.gameOver(canvas,context);
        }*/
    }

    public void repaintBackground(Canvas canvas) {
        paint.setColor(backgroundColor);
        canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), paint);
    }

    public void drawSnake(Canvas canvas) {
        int r;
        int c;
        paint.setColor(SnakeActivity.snakeColor);
        paint.setStyle(Paint.Style.FILL);

        for (int i = 0; i < SnakeActivity.logic.getSnake().length; i++) {
            //Log.v("snaketest", "drawing snake");
            r = SnakeActivity.logic.getSnake().getPosition().get(i).getR();
            c = SnakeActivity.logic.getSnake().getPosition().get(i).getC();
            canvas.drawRect(c * WIDTH + 2, r * HEIGHT + 2, c * WIDTH + WIDTH - 1, r * HEIGHT + HEIGHT - 1, paint);
        }
    }

    private void drawFruit(Canvas canvas) {
        int r = SnakeActivity.logic.getFruitPosition().getR();
        int c = SnakeActivity.logic.getFruitPosition().getC();
        paint.setColor(fruitColor);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRect(c * WIDTH + 2, r * HEIGHT + 2, c * WIDTH + WIDTH - 1, r * HEIGHT + HEIGHT - 1, paint);
        //Log.v("snaketest", "drawing fruit");
    }


}


