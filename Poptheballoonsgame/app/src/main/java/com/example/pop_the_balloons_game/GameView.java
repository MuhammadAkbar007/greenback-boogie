/*Written by Achyut Bhandiwad for CS6326.001, assignment 6,starting November 14, 2019
 * Net ID: aab180004
 * This the main Game View which handles all the drawing.
 * This view contains all the logic for the game.
 */

package com.example.pop_the_balloons_game;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.pop_the_balloons_game.highscores.HighScoreActivity;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class GameView extends View {

    private static final long GAME_START_TIME = 60000;
    Handler h;
    int frameRate;
    int state = 0;
    int score, poppedBalloonCount, missedBalloonCount;
    CountDownTimer timer;
    long currentTime;
    List<Shape> balloons; //list of active balloons

    boolean currentShape; //true = SQUARE , false = CIRCLE
    int currentColor;

    int canvasHeight, canvasWidth;

    int numberOfBalloons; //number of balloons for current game

    TextView scoreText, timerText;

    LinearLayout resultView;
    Button viewScoresButton;
    TextView result;

    public GameView(Context context) {
        super(context);
        init(context);
    }


    /*
    * Initialize the handler and balloon list
    *
    * */
    public void init(Context context){
        h = new Handler();
        frameRate = 1;
        balloons = new LinkedList<Shape>();
    }

    public GameView(Context context, AttributeSet attrs){
        super(context,attrs);
        init(context);
    }

    /*
    * Set current games shape
    * */
    public void setCurrentGame(boolean shape, int color){
        this.currentShape = shape;
        this.currentColor = color;
    }

    public void setTextViews(TextView score, TextView timer){
        scoreText = score;
        timerText = timer;
    }

    public void setResultView(LinearLayout resultView, TextView result, Button viewScores){
        this.resultView = resultView;
        this.result = result;
        this.viewScoresButton = viewScores;
    }


    /*
    * On touch event which handles balloon popping
    * */
    @Override
    public boolean onTouchEvent(MotionEvent event){
        if(state == 1) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                Shape touchedBalloon = null;
                //Check if a balloon is touched and add it to the list
                for (Shape balloon : balloons) {
                    if (balloon.x < event.getX() && event.getX() < balloon.x + balloon.size && balloon.y > event.getY() && event.getY() > balloon.y - balloon.size) {
                        touchedBalloon = balloon;

                    }
                }

                // check if touched balloon is the correct balloon
                if (touchedBalloon != null) {
                    //if(touchedBalloon.color == currentColor && ((currentShape && touchedBalloon instanceof Square) || (!currentShape && touchedBalloon instanceof Circle))){
                    if (touchedBalloon.color == currentColor && touchedBalloon.getShape() == currentShape) {
                        score++; //increment score if correct balloon
                        poppedBalloonCount++;
                    } else {
                        score--; //decrement score if wrong balloon
                    }


                    //update score
                    scoreText.setText(String.valueOf(score));

                    //if 10 balloons popped the add 10s to the timer

                    balloons.remove(touchedBalloon);
                    if (timer != null && poppedBalloonCount % 10 == 0 && poppedBalloonCount != 0) {
                        timer.cancel();
                        startTimer(currentTime + 10000);
                        timerText.setText("+10s");
                    }

                }


            }
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvasHeight = canvas.getHeight();
        canvasWidth = canvas.getWidth();

        //if it is the start if game then initialize the balloons
        if(state == 0){

            Random rand = new Random();
            numberOfBalloons = rand.nextInt((12 - 6) + 1) + 6;
            //generate random number of balloons
            for(int i=0 ; i< numberOfBalloons ; i++){
                if(rand.nextInt(2) == 1){
                    balloons.add(new Square(getContext(),canvasHeight,canvasWidth));
                }else{
                    balloons.add(new Circle(getContext(),canvasHeight, canvasWidth));
                }

            }
            //start the timer and change state
            startTimer(GAME_START_TIME);
            state++;

        }

        //Check if a balloon reached the top of the screen and add it to list

        List<Shape> disappeardBalloons = new LinkedList<>();

        for(Shape balloon : balloons){
            if(balloon.y <= 0 || balloon.y > canvasHeight + balloon.size){
                if(balloon.color == currentColor && balloon.getShape() == currentShape){
                    missedBalloonCount++;
                }
                disappeardBalloons.add(balloon);
            }
        }

        //remove disappeared balloons from list
        for(Shape balloon : disappeardBalloons){
            balloons.remove(balloon);
        }

        // check the number of balloons disappeared or popped
        int diff = numberOfBalloons - balloons.size();

        //create new random balloons when they are popped or disappear
        for(int i=0; i < diff ; i++){
            Random rand = new Random();
            if(rand.nextInt(2) == 1){
                balloons.add(new Square(getContext(),canvasHeight,canvasWidth));
            }else{
                balloons.add(new Circle(getContext(),canvasHeight, canvasWidth));
            }
        }

        //Check if balloons overlap
        for(Shape balloon : balloons){
            if( state != 0) {
                for (Shape innerBalloon : balloons) {
                    //check if balloons intersect
                    if (balloon.rectF.intersect(innerBalloon.rectF)) {
                        //use the concept of energy transfer in real life and transfer the velocity of one balloon to another
                        if (balloon.y < innerBalloon.y) {
                            balloon.incrementVelocity(2);
                            innerBalloon.decrementVelocity(2);
                        }else{
                            balloon.decrementVelocity(2);
                            innerBalloon.incrementVelocity(2);
                        }
                    }
                }
            }
            balloon.drawBalloon(canvas);
        }


        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                invalidate();
            }
        },10);

    }


    /*
    * Start the timer and set ontick and onfinish
    * */
    private void startTimer(long time){
        timer = new CountDownTimer(time, 1000){
            public void onTick(long millisUntilDone){
                timerText.setText( String.valueOf(millisUntilDone / 1000) + "s");
                currentTime=millisUntilDone;
            }

            public void onFinish() {
                //timerText.setText("DONE!");

                    state = 2;
                    resultView.setVisibility(VISIBLE);
                    result.setText("You scored " + score + "! and missed " + missedBalloonCount + " balloons.");
                    viewScoresButton.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent i = new Intent(getContext(), HighScoreActivity.class);
                            i.putExtra("score", score);
                            getContext().startActivity(i);
                        }
                    });


            }
        }.start();
    }

    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }
}
