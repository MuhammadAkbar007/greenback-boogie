/*Written by Achyut Bhandiwad for CS6326.001, assignment 6,starting November 14, 2019
 * Net ID: aab180004
 * This class is the Activity which has the GameView and the timer and score views.
 * This class also listens to the sensor and moves the balloons accordingly
 */

package com.example.pop_the_balloons_game;

import android.content.Intent;
import android.content.res.Configuration;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.TextViewCompat;

public class GameActivity extends AppCompatActivity implements SensorEventListener {
    private ViewGroup mContentView;
    GameView gameView;
    int mScreenWidth, mScreenHeight;
    TextView scoreText, timerText, timerTop, scoreTop;

    int currentColor;
    boolean currentShape;

    private SensorManager mSensorMgr;
    private Sensor accelerometerSensor;
    static final float ALPHA = 0.25f; // if ALPHA = 1 OR 0, no filter applies.
    float previousX;

    LinearLayout resultView;
    Button viewScoresButton;
    TextView result;

    private long lastUpdateTimestamp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        mContentView = findViewById(R.id.activity_game);

        Intent intent = getIntent();
        currentShape = intent.getBooleanExtra("shape", true);
        currentColor = intent.getIntExtra("color", 0);

        gameView = findViewById(R.id.gameView);
        scoreText = findViewById(R.id.score);
        timerText = findViewById(R.id.timer);
        timerTop = findViewById(R.id.timerTop);
        scoreTop = findViewById(R.id.scoreTop);
        resultView = findViewById(R.id.resultView);
        result = findViewById(R.id.result);
        viewScoresButton = findViewById(R.id.viewScores);

        gameView.setCurrentGame(currentShape, currentColor);

        gameView.setTextViews(scoreText, timerText);
        gameView.setResultView(resultView, result, viewScoresButton);

        result = findViewById(R.id.result);
        viewScoresButton = findViewById(R.id.viewScores);


        //Setting border
        gameView.setBackground(getResources().getDrawable(R.drawable.border));


        //Initializing the sensor manager
        mSensorMgr = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometerSensor = mSensorMgr.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        ViewTreeObserver viewTreeObserver = mContentView.getViewTreeObserver();
        if (viewTreeObserver.isAlive()) {
            viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    mContentView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    mScreenHeight = mContentView.getHeight();
                    mScreenWidth = mContentView.getWidth();
                }
            });
        }
    }


    /*
     * Register the sensor listener
     * */
    @Override
    protected void onResume() {
        super.onResume();
        mSensorMgr.registerListener(this, accelerometerSensor, SensorManager.SENSOR_DELAY_GAME);
    }


    /*
     * Un-Register the sensor listener
     * */
    @Override
    protected void onPause() {
        super.onPause();
        mSensorMgr.unregisterListener(this);
    }


    /*
     * Update the balls X pos on change of the x-axis of accelerometer
     * */
    @Override
    public void onSensorChanged(SensorEvent event) {

        int orientation = getResources().getConfiguration().orientation;

        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {


            float currentX = lowPass(event.values[0], previousX);
            float dX;

            //if in landscape mode then get the y axis
            if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                previousX = 0;
                currentX = lowPass(event.values[1], previousX);
            }

            long currentTimestamp = System.currentTimeMillis();

//            if((currentTimestamp - lastUpdateTimestamp) > 100) {
            lastUpdateTimestamp = currentTimestamp;
            dX = (previousX - currentX) * 10;
            for (Shape balloon : gameView.balloons) {
                balloon.x += dX; //add delta to the balloons x position
            }
            previousX = currentX;
//            }


        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }


    /*
     * Lowpass filter to smooth the sensor change
     * */
    protected float lowPass(float input, float output) {
        output = output + ALPHA * (input - output);
        return output;
    }


    /*
     * Change the text size of score and timer depending on the orientaiton
     * */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);


        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
//            TextViewCompat.setTextAppearance(timerText, R.style.TextAppearance_AppCompat_Body1);
//            TextViewCompat.setTextAppearance(scoreText, R.style.TextAppearance_AppCompat_Body1);
            timerTop.setTextSize(8);
            scoreTop.setTextSize(8);
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {

//            TextViewCompat.setTextAppearance(timerText, R.style.TextAppearance_AppCompat_Display2);
//            TextViewCompat.setTextAppearance(scoreText, R.style.TextAppearance_AppCompat_Display2);
//            TextViewCompat.setTextAppearance(timerTop, R.style.TextAppearance_AppCompat_Small);
//            TextViewCompat.setTextAppearance(scoreTop, R.style.TextAppearance_AppCompat_Small);
        }
    }
}
