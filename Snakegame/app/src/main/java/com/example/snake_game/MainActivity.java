package com.example.snake_game;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements OnClickListener {

    /*
     * Ye Yint Lwin (Physics)
     * 19/Feb/2018
     */

    private final int POSITION_UP = 0;
    private final int POSITION_DOWN = 1;
    private final int POSITION_RIGHT = 2;
    private final int POSITION_LEFT = 3;
    int count = 0;
    private int positions = POSITION_RIGHT;
    private ImageView[][] imagesView = new ImageView[8][8];
    private boolean isNewPath = false;
    private ImageView[] body = new ImageView[1];
    private int row = 0;
    private int col = 0;
    private ImageView food;
    private boolean foodAvailable = true;
    private boolean tr = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inits();

        ((ImageButton) findViewById(R.id.mainImageButtonUp)).setOnClickListener(this);
        ((ImageButton) findViewById(R.id.mainImageButtonDown)).setOnClickListener(this);
        ((ImageButton) findViewById(R.id.mainImageButtonLeft)).setOnClickListener(this);
        ((ImageButton) findViewById(R.id.mainImageButtonRight)).setOnClickListener(this);
        ((ImageButton) findViewById(R.id.mainImageButtonCentre)).setOnClickListener(this);

        imagesView[0][0].setImageResource(R.drawable.image_6);
        thread();
        setFood();
    }

    private void inits() {
        int k = 0;
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++)
                imagesView[i][j] = (ImageView) findViewById(LED.id[k++]);
    }

    private void makeSnake(int row, int col) {
        if (body.length >= 63) win();
        else {
            for (ImageView mybody : body) if (mybody == imagesView[row][col]) susite();
            foodAvailable = false;
            isNewPath = food == imagesView[row][col];
            if (isNewPath) {
                ImageView[] temp = new ImageView[body.length + 1];
                for (int i = 0; i < body.length; i++) temp[i] = body[i];
                temp[body.length] = imagesView[row][col];
                body = new ImageView[body.length + 1];
                body = temp;
                isNewPath = false;
                foodAvailable = true;
            } else {
                ImageView[] temp = new ImageView[body.length];
                for (int i = 0; i < body.length - 1; i++) temp[i] = body[i + 1];
                temp[body.length - 1] = imagesView[row][col];
                body = temp;
            }
            clear();
            setFood();
            for (ImageView part : body) part.setImageResource(R.drawable.image_4);
            imagesView[row][col].setImageResource(R.drawable.image_6);
        }
    }

    private void setFood() {
        if (foodAvailable) food = imagesView[(int) (Math.random() * 8)][(int) (Math.random() * 8)];
        for (ImageView mpath : body) if (food == mpath) setFood();
        food.setImageResource(R.drawable.image_5);
    }

    private void clear() {
        for (ImageView imageView[] : imagesView)
            for (ImageView a : imageView)
                a.setImageResource(R.drawable.image_3);
    }

    private void goTo(int pos) {
        switch (pos) {
            case POSITION_UP:
                if (row > 0) makeSnake(row = row - 1, col);
                else over();
                break;
            case POSITION_DOWN:
                if (row < 7) makeSnake(row = row + 1, col);
                else over();
                break;
            case POSITION_RIGHT:
                if (col < 7) makeSnake(row, col = col + 1);
                else over();
                break;
            case POSITION_LEFT:
                if (col > 0) makeSnake(row, col = col - 1);
                else over();
                break;
        }
    }

    private void restart() {
        tr = false;
        positions = POSITION_RIGHT;
        body = new ImageView[1];
        isNewPath = false;
        foodAvailable = true;
        tr = true;
        row = 0;
        col = 0;
        imagesView[0][0].setImageResource(R.drawable.image_6);
        //thread();
        setFood();

    }

    private void win() {
        Toast.makeText(this, "You are cleaver!", Toast.LENGTH_SHORT).show();
        tr = false;
        clear();
        count = 0;
        restart();

    }

    private void over() {
        Toast.makeText(this, "It is wall!", Toast.LENGTH_SHORT).show();
        restart();
    }

    private void susite() {
        Toast.makeText(this, "Eating your self!", Toast.LENGTH_SHORT).show();
        restart();
    }

    private void thread() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (tr) {
                    try {
                        Thread.sleep(150);
                    } catch (InterruptedException e) {
                    }
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            count++;
                            switch (count) {
                                case 8:
                                    positions = POSITION_DOWN;
                                    break;
                                case 9:
                                    positions = POSITION_LEFT;
                                    break;
                                case 15:
                                    positions = POSITION_DOWN;
                                    break;
                                case 16:
                                    positions = POSITION_RIGHT;
                                    break;

                                case 22:
                                    positions = POSITION_DOWN;
                                    break;
                                case 23:
                                    positions = POSITION_LEFT;
                                    break;
                                case 29:
                                    positions = POSITION_DOWN;
                                    break;
                                case 30:
                                    positions = POSITION_RIGHT;
                                    break;

                                case 36:
                                    positions = POSITION_DOWN;
                                    break;
                                case 37:
                                    positions = POSITION_LEFT;
                                    break;
                                case 43:
                                    positions = POSITION_DOWN;
                                    break;
                                case 44:
                                    positions = POSITION_RIGHT;
                                    break;

                                case 50:
                                    positions = POSITION_DOWN;
                                    break;
                                case 51:
                                    positions = POSITION_LEFT;
                                    break;
                                case 58:
                                    positions = POSITION_UP;
                                    break;
                                case 65:
                                    count = 1;
                                    positions = POSITION_RIGHT;
                                    break;
                            }
                            goTo(positions);
                        }
                    });
                }
            }
        }).start();
    }

    @Override
    public void onClick(View p1) {
        //tr=false;
        switch (p1.getId()) {

            case R.id.mainImageButtonUp:
                positions = POSITION_UP;
                //goTo(POSITION_UP);
                break;
            case R.id.mainImageButtonDown:
                positions = POSITION_DOWN;
                //goTo(POSITION_DOWN);
                break;
            case R.id.mainImageButtonLeft:
                positions = POSITION_LEFT;
                //goTo(POSITION_LEFT);
                break;
            case R.id.mainImageButtonRight:
                positions = POSITION_RIGHT;
                //goTo(POSITION_RIGHT);
                break;
            case R.id.mainImageButtonCentre:
                //isNewPath = true;
                break;
        }
        //goTo(positions);
    }
}