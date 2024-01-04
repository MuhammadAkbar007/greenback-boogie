/*Written by Achyut Bhandiwad for CS6326.001, assignment 6,starting November 14, 2019
 * Net ID: aab180004
 * This class is the Circle which extends Shape and has attributes specific to a circle
 */

package com.example.pop_the_balloons_game;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;


public class Circle extends Shape {

    public Circle(Context context, int screenHeight, int screenWidth) {
        super(context,screenHeight,screenWidth);
    }

    /*
    * draw balloon on the canvas. This method also adds velocity to the balloon
    * */
    @Override
    void drawBalloon(Canvas canvas) {
        rectF = new RectF(x ,y-size ,x+size,y);
        Paint paint = new Paint();
        paint.setColor(color);
        canvas.drawCircle(x + size /2,y - size/2,size / 2, paint);
        y -= velocity; //add velocity to the ball
    }

    void drawBallooForMain(Canvas canvas){
        rectF = new RectF(x ,y-size ,x+size,y);
        Paint paint = new Paint();
        paint.setColor(color);
        canvas.drawCircle(x + size /2,y - size/2,size / 2, paint);
    }

    @Override
    public float getTop() {
        return y - size/2;
    }

    @Override
    boolean getShape() {
        return false;
    }

}
