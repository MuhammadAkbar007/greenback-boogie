/*Written by Achyut Bhandiwad for CS6326.001, assignment 6,starting November 14, 2019
 * Net ID: aab180004
 * This class is the abstract class to which represents a shape.
 */

package com.example.pop_the_balloons_game;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.RectF;

import com.example.pop_the_balloons_game.utils.GameColors;
import com.example.pop_the_balloons_game.utils.PixelHelper;

import java.util.Random;

public abstract class Shape {

    int size;
    int color;
    float x , y;
    int velocity;
    RectF rectF;
    public static final int MAX_VELOCITY = 20; //max velocity a balloon can have
    public static final int MIN_VELOCITY = 2;//min velocity a balloon can have


    /*
    * Generate a random shape and initialzie the attributes
    * */
    public Shape(Context context, int screenHeight, int screenWidth){
        Random rand = new Random();
        int randSize = rand.nextInt((64 - 32) + 1) + 32;
        this.color = GameColors.getRandomColor().color;
        this.size = PixelHelper.dip2px(context, randSize);
        this.y = screenHeight + size;
        this.x = rand.nextInt(screenWidth - this.size);
        this.velocity = rand.nextInt((10 - 5) + 1) + 5;
        rectF = new RectF(x ,y-size ,x+size,y);
    }


    abstract void drawBalloon(Canvas canvas);

    abstract float getTop();

    abstract boolean getShape();


    /*
    * increment the velocity and check if it does not cross the threshold
    * */
    public void incrementVelocity(int v){
        if(velocity + v <= MAX_VELOCITY){
            velocity += v;
        }
    }

    void drawBallooForMain(Canvas canvas){

    }

    /*
     * decrement the velocity and check if it does not cross the threshold
     * */

    public void decrementVelocity(int v){
        if(velocity - v >= MIN_VELOCITY){
            velocity -= v;
        }
    }

}
